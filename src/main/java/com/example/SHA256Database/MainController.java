package com.example.SHA256Database;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {

  // Maximum allowed size of hashed values
  private static final int MAX_INPUT_SIZE_BYTES = 1024;

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
}
