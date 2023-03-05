package com.example.SHA256Database;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class HashModelAssembler implements RepresentationModelAssembler<Hash, EntityModel<Hash>> {
  
  @Override
  public EntityModel<Hash> toModel(Hash hash) {
    // First properly encode the digest into base64.
    Base64.Encoder encoder = Base64.getEncoder();
    
    String b64Digest = encoder.encodeToString(hash.getDigest());
    String urlDigest = URLEncoder.encode(b64Digest, StandardCharsets.UTF_8);
    // Double encode slashes to prevent HTTP 400
    String encodedDigest = urlDigest.replaceAll("%2F", "%252F");

    return EntityModel.of(hash, linkTo(methodOn(MainController.class).decodeHash(encodedDigest)).withSelfRel(),
                                linkTo(methodOn(MainController.class).getHashPage(0, 20)).withRel("hashes"));

    //Link.of(String.format("/hash/%s", encodedDigest)));
  }
}
