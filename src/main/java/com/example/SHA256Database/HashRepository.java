package com.example.SHA256Database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.SHA256Database.Hash;

// I guess this gets auto implemented idk
public interface HashRepository extends CrudRepository<Hash, byte[]>, 
                                        PagingAndSortingRepository<Hash, byte[]> {}
