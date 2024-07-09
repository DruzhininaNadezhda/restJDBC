package com.example.restjdbc.service.impl;

import com.example.restjdbc.DTO.AddressesDTO;
import com.example.restjdbc.DTO.ClientsDTO;
import com.example.restjdbc.DTO.TypeEnum;
import com.example.restjdbc.entity.AddressesEntity;
import com.example.restjdbc.entity.ClientsEntity;

import com.example.restjdbc.repository.ClientsRepo;
import com.example.restjdbc.service.interfaces.ClientsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ClientsServiceImpl implements ClientsService {
    private final ClientsRepo clientRepository;
    public ClientsServiceImpl(ClientsRepo clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Flux<ClientsDTO> findAll(){
        return Flux.fromIterable(clientRepository.findAll())
                .map(ClientsService::clientConvertToDto);
    }
    @Transactional
    @Override
    public Mono<ClientsDTO> update(ClientsDTO dto){
        if(dto!=null){
            return Mono.just(clientRepository.save(convertToEntity(dto)))
                    .map(ClientsService::clientConvertToDto);
        }return Mono.empty();
    }
    @Transactional
    @Override
    public Optional<ClientsDTO> create(ClientsEntity entity) {
        //if(entity!=null){
            return Optional.ofNullable(clientRepository.save(entity)).map(ClientsService::clientConvertToDto);
       // }
       // return Mono.empty();
    }
    @Override
    public Optional<ClientsDTO> findClientsDtoById(long clientID) {
        return Optional.of(clientRepository.findById(clientID).get()).map(ClientsService::clientConvertToDto);
    }
    @Override
    public Flux<ClientsDTO> filerTypeName(String type1, String name1){
        String name = "%"+ name1+"%";
        String type = "%"+ type1+"%";
        return Flux.fromIterable(clientRepository
                        .findAllByClientNameIsLikeIgnoreCaseAndTypeclientIsLikeIgnoreCase(name,type))
                .map(ClientsService::clientConvertToDto);
    }

    @Transactional
    @Override
    public void remove(long id) {
            Optional<ClientsEntity> addressBd = clientRepository.findById(id);
            clientRepository.delete(addressBd.get());
        }

    @Override
    public ClientsEntity convertToEntity(ClientsDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto, ClientsEntity.class);

    }
    //@Override
    //public void help (Long id){
        //clientRepository.help(id);
//}
   // @Override
    //public void helpStarted (Long id){
      //  clientRepository.helpStarted(id);
    }
