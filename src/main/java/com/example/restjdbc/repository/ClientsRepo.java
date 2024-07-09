package com.example.restjdbc.repository;

import com.example.restjdbc.entity.ClientsEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientsRepo extends CrudRepository<ClientsEntity, Long> {
    Iterable<ClientsEntity> findAllByClientNameIsLikeIgnoreCaseAndTypeclient(String name, String type);
}
