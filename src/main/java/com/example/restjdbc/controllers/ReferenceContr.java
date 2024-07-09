package com.example.restjdbc.controllers;
import com.example.restjdbc.service.interfaces.ClientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/help")
@RequiredArgsConstructor
public class ReferenceContr {
    //private final ReferenceService referenceService;
    private final ClientsService clientsService;

//    @GetMapping(produces = "application/json")
//    public ResponseEntity<?> getRefenence(@RequestParam Long id) {
//        Optional<ReferenceDTO> reference = referenceService.findReferenceDtoById(id);
//        return ResponseEntity.ok(reference);
//    }
//    @PostMapping(consumes = "application/json")
//    public void help ( @RequestBody Optional<ReferenceDTO> reference){
//        clientsService.helpStarted(reference.get().getClientid());
//    }
}
