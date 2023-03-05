package com.example.SHA256Database;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {

  // Maximum allowed size of hashed values
  private static final int MAX_INPUT_SIZE_BYTES = 1024;

  // Maximum allowed page size of requests for multiple hashes
  private static final int MAX_PAGE_SIZE = 200;

  @Autowired
  private HashRepository hashRepository;

  private HashModelAssembler assembler;

  public MainController (HashModelAssembler assembler) {
    this.assembler = assembler;
  }

  @PostMapping(path="/hash")
  @ResponseStatus(HttpStatus.CREATED)
  public EntityModel<Hash> addNewHash (@RequestBody Hash newHash) {

    if (newHash.getInput().length > MAX_INPUT_SIZE_BYTES) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input too large", 
                                        new InputTooLargeException(MAX_INPUT_SIZE_BYTES));
    }

    Hash h = new Hash(newHash.getInput());

    // Update count
    Hash existingHash = hashRepository.findById(h.getDigest()).orElse(h);
    h.setCount(existingHash.getCount() + 1);
    
    hashRepository.save(h);
    return assembler.toModel(h);
  }

  @GetMapping(path="/hash/{encodedHash}")
  public @ResponseBody EntityModel<Hash> decodeHash(@PathVariable String encodedHash) {
    String b64Hash = encodedHash.replaceAll("%2F", "/");

    Base64.Decoder decoder = Base64.getDecoder();

    byte[] digest = decoder.decode(b64Hash);

    Hash result = hashRepository.findById(digest)
                                .orElseThrow(() -> new HashNotFoundException(encodedHash));
  
    return assembler.toModel(result);
  }

  @GetMapping(path="/hashes")
  public PagedModel<EntityModel<Hash>> getHashPage(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "20") int size) {
    if (size > MAX_PAGE_SIZE) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                                        String.format("Exceeded max size: %d", MAX_PAGE_SIZE));
    }

    PageRequest request = PageRequest.of(page, size, Sort.by("Count").descending());
    Page<Hash> hashesPage = hashRepository.findAll(request);
    
    if (page > hashesPage.getTotalPages()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found.");
    }

    List<Hash> hashesList = hashesPage.getContent();
    List<EntityModel<Hash>> rtnList = new ArrayList<EntityModel<Hash>>();

    for (Hash hash : hashesList) {
      rtnList.add(assembler.toModel(hash));
    }

    PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(size, page,
                                                                   hashesPage.getTotalElements());

    return PagedModel.of(rtnList, metadata);
  }
}
