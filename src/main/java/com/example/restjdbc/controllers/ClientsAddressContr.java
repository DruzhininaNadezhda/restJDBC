package com.example.restjdbc.controllers;

import com.example.restjdbc.DTO.AddressesDTO;
import com.example.restjdbc.DTO.ClientsAddressDTO;
import com.example.restjdbc.DTO.ClientsDTO;
import com.example.restjdbc.repository.ClientsRepo;
import com.example.restjdbc.service.interfaces.AddressesService;
import com.example.restjdbc.service.interfaces.ClientsAddressService;
import com.example.restjdbc.service.interfaces.ClientsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/clientsAddress")
public class ClientsAddressContr {
    private final ClientsAddressService clientsAddressService;
    private final AddressesService addressesService;
    private final ClientsService clientsService;
    private final ClientsRepo clientRepository;

    public ClientsAddressContr(ClientsAddressService clientsAddressService, AddressesService addressesService, ClientsService clientsService, ClientsRepo clientRepository) {
        this.clientsAddressService = clientsAddressService;
        this.addressesService = addressesService;
        this.clientsService = clientsService;
        this.clientRepository = clientRepository;
    }

    // private final ReferenceService referenceService; - прочитать
    //++++
    @GetMapping(value = "/client/{id}", produces = "application/json")
    public ResponseEntity<?> getClient(@PathVariable("id") Long id){
       if(clientsAddressService.findById(id)!=null) return ResponseEntity.ok(clientsAddressService.findById(id));
       else return ResponseEntity.ok().build();
   }
   //++++
    @GetMapping( produces = "application/json")
    public ResponseEntity<?> filter(@RequestParam String type, String name) {
        return ResponseEntity.ok(clientsAddressService.findFilter(type, name));
    }
    //+++
    @DeleteMapping("/{id}")
        public void removeById(@PathVariable("id") Long id) {
            clientsAddressService.remove(id);
        }
    //+++
        @PostMapping(consumes = "application/json")
    public ResponseEntity<?> creat(@RequestBody ClientsAddressDTO clientsAddress) {
            return ResponseEntity.ok( clientsAddressService.create(clientsAddress));

    }
}
