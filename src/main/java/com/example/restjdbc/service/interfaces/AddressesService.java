package com.example.restjdbc.service.interfaces;

import com.example.restjdbc.DTO.AddressesDTO;
import com.example.restjdbc.DTO.ClientsAddressDTO;
import com.example.restjdbc.entity.AddressesEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
@Validated
public interface AddressesService {
    public Mono<AddressesDTO> findAddressesDtoById(long addressId);
    public Flux<AddressesDTO> findAll();
    public Mono<AddressesDTO> create(AddressesEntity entity);
    public Mono<AddressesDTO> update(@Valid AddressesDTO dto);
    public void remove(long id);
    public Flux<AddressesDTO> filerAddress(String address);
}
