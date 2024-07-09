package com.example.restjdbc.repository;

import com.example.restjdbc.entity.AddressesEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepo extends CrudRepository<AddressesEntity, Long> {
    Iterable <AddressesEntity> findAllByAddressIsLike(String address);
}
