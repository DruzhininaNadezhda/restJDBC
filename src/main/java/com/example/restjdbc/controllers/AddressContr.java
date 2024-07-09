package com.example.restjdbc.controllers;

import com.example.restjdbc.DTO.AddressesDTO;
import com.example.restjdbc.DTO.ClientsDTO;
import com.example.restjdbc.entity.AddressesEntity;
import com.example.restjdbc.service.interfaces.AddressesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressContr {
    private final AddressesService addressesService;
    @DeleteMapping("/{id}")
    public void removeById(@PathVariable("id") Long id) {
        addressesService.remove(id);
    }
    @PostMapping
    public Mono<AddressesDTO> create (@RequestBody AddressesDTO addresses){
       return addressesService.create(AddressesEntity.builder()
                .address(addresses.getAddress())
                        .client(addresses.getClient())
                        .ip(addresses.getIp())
                        .mac(addresses.getMac())
                        .model(addresses.getModel())
                        .address(addresses.getAddress())
                .build());
    }
    @PatchMapping
    public Mono<AddressesDTO> update (@RequestBody AddressesDTO addresses){
        return addressesService.update(addresses);
    }
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getAddress(@PathVariable Long id){
         return ResponseEntity.ok(addressesService.findAddressesDtoById(id));
    }
}
