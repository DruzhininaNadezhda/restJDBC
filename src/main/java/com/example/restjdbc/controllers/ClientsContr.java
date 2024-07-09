package com.example.restjdbc.controllers;

import com.example.restjdbc.DTO.ClientsDTO;

import com.example.restjdbc.service.interfaces.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientsContr {
    private final ClientsService clientsService;

//    @PostMapping(value = "/help", consumes = "application/json")
//    public void help ( @RequestBody Optional<ReferenceDTO> reference){
//        clientsService.help(reference.get().getClientid());
//      referenceService.help(reference);
//     }
    @PatchMapping()
    public Mono<ClientsDTO> update(@RequestBody ClientsDTO clientsDTO) {
        return clientsService.update(clientsDTO);
    }
}
