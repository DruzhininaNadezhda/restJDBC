package com.example.restjdbc.service.impl;

import com.example.restjdbc.DTO.AddressesDTO;
import com.example.restjdbc.DTO.ClientsAddressDTO;
import com.example.restjdbc.DTO.ClientsDTO;
import com.example.restjdbc.entity.AddressesEntity;
import com.example.restjdbc.entity.ClientsEntity;
import com.example.restjdbc.repository.AddressRepo;
import com.example.restjdbc.repository.ClientsRepo;
import com.example.restjdbc.service.interfaces.AddressesService;
import com.example.restjdbc.service.interfaces.ClientsAddressService;
import com.example.restjdbc.service.interfaces.ClientsService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service

public class ClientsAddressServiceImpl implements ClientsAddressService {
    private final ClientsRepo clientRepository;
    private final ClientsService clientsService;
    private final AddressRepo addressRepository;
    private final AddressesService addressesService;



    public ClientsAddressServiceImpl(ClientsRepo clientRepository, ClientsService clientsService, AddressRepo addressRepository, AddressesService addressesService) {
        this.clientRepository = clientRepository;
        this.clientsService = clientsService;
        this.addressRepository = addressRepository;
        this.addressesService = addressesService;
    }


    @Override
    public Stream<ClientsAddressDTO> findAll() {
        List<ClientsAddressDTO> clientsAddress = new LinkedList<>();
        clientRepository.findAll().forEach(entity -> {
                    Set<AddressesEntity> addresses = entity.getAddressesEntities();
                    if (addresses == null|| addresses.isEmpty()) {
                        clientsAddress.add(addressClientConvertToDto(entity,null));
                    }
                    else {
                        addresses.forEach(address ->clientsAddress.add(addressClientConvertToDto(entity,address)));
                        }
                }
        );
        return clientsAddress.stream().sorted(Comparator.comparingLong(ClientsAddressDTO::getClientid));
    }
    @Override
    public Stream<ClientsAddressDTO> findById(Long id) {
        List<ClientsAddressDTO> clientsAddress = new LinkedList<>();
        ClientsEntity result =  clientRepository.findById(id).get();
                    Set<AddressesEntity> addresses = result.getAddressesEntities();
                    if (addresses == null|| addresses.isEmpty()) {
                        clientsAddress.add(addressClientConvertToDto(result,null));
                    }
                    else {
                        addresses.forEach(address ->clientsAddress.add(addressClientConvertToDto(result,address)));
                    }
        return clientsAddress.stream().sorted(Comparator.comparingLong(ClientsAddressDTO::getClientid));
    }
    @Override
    public Flux<ClientsAddressDTO> findFilter(String type, String name) {
        Flux<ClientsAddressDTO> o = filerTypeName(type, name);
        Flux<ClientsAddressDTO> k = filerAddress(name).filter(e -> (type!=null && !type.isEmpty())? e.getTypeclient().equals(type):e.getTypeclient().equals(e.getTypeclient()));
        return Flux.concat(o,k).distinct().sort(Comparator.comparing(ClientsAddressDTO::getClientid));
    }
    @Transactional
     @Override
   public Mono<ClientsAddressDTO> create(ClientsAddressDTO dto){
        ClientsDTO dtoClient = clientsService.create(addressClientConvertToClientEntity(dto)).get();
         AddressesEntity addressesEntity = addressClientConvertToAddressEntity(dto);
         addressesEntity.setClient(dtoClient.getClientid()); //присваение ID клиента адресу
          addressesService.create(addressesEntity);
       return Mono.just(addressClientConvertToDtoFromClientsDTO(clientsService.findClientsDtoById(dtoClient.getClientid()).get()));
    }
    @Transactional
    @Override
    public void remove(long id) {
        Optional<ClientsEntity> clientsBd = clientRepository.findById(id);
        if (clientsBd.get().getAddressesEntities() != null || !clientsBd.get().getAddressesEntities().isEmpty()) {
            for (AddressesEntity addresses : clientsBd.get().getAddressesEntities()) {
                addressesService.remove(addresses.getAddressid());
            }
        }
        clientsService.remove(clientsBd.get().getClientid());
    }
    @Override
    public ClientsAddressDTO addressClientConvertToDto(ClientsEntity entity2, AddressesEntity entity) {
        if (entity == null)
        {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.createTypeMap(ClientsEntity.class, ClientsAddressDTO.class)
            .addMappings(mapper -> mapper.map(ClientsEntity::getTypeclient, ClientsAddressDTO::setTypeclient));
            ClientsAddressDTO dto= modelMapper.map(entity2, ClientsAddressDTO.class);
            return dto;}
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(ClientsEntity.class, ClientsAddressDTO.class)
                .addMappings(mapper -> mapper.map(ClientsEntity::getTypeclient, ClientsAddressDTO::setTypeclient));
        ClientsAddressDTO dto= modelMapper.map(entity2, ClientsAddressDTO.class);
        modelMapper.map(entity,dto);
        return dto;
    }
    @Override
    public ClientsAddressDTO addressClientConvertToDtoFromClientsDTO(ClientsDTO clientsDTO) {
        ModelMapper modelMapper = new ModelMapper();
        ClientsAddressDTO dto = modelMapper.map(clientsDTO, ClientsAddressDTO.class);
       if (clientsDTO.getAddresses()== null) {
           return dto;
        } else {
           for (AddressesDTO addressesDTO:clientsDTO.getAddresses()) {
               dto.setAddress(addressesDTO.getAddress());
               dto.setAddressid(addressesDTO.getAddressid());
               dto.setIp(addressesDTO.getIp());
               dto.setMac(addressesDTO.getMac());
               dto.setModel(addressesDTO.getModel());
               dto.setClientid(addressesDTO.getClient());
               dto.setAddressid(addressesDTO.getAddressid());
           }
           }
       return dto;
    }
//
    @Override
    public ClientsAddressDTO addressClientConvertToDtoFromAddressDTO(AddressesDTO addressesDTO) {
        ModelMapper modelMapper = new ModelMapper();
        ClientsDTO k = clientsService.findClientsDtoById(addressesDTO.getClient()).get();
        ClientsAddressDTO dto = modelMapper.map(clientsService.findClientsDtoById(addressesDTO.getClient()), ClientsAddressDTO.class);
        dto.setAddress(addressesDTO.getAddress());
       dto.setAddressid(addressesDTO.getAddressid());
       dto.setIp(addressesDTO.getIp());
       dto.setMac(addressesDTO.getMac());
       dto.setModel(addressesDTO.getModel());
       dto.setClientid(addressesDTO.getClient());
        return dto;
    }
    @Override
    public ClientsEntity addressClientConvertToClientEntity(ClientsAddressDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto, ClientsEntity.class);
    }
    @Override
    public AddressesEntity addressClientConvertToAddressEntity(ClientsAddressDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(dto, AddressesEntity.class);
    }
    @Override
    public long clientUniControl(String name, String type, Stream <ClientsAddressDTO> clients) {
        List <ClientsAddressDTO> clients1 = clients.collect(Collectors.toList());
        for (ClientsAddressDTO client : clients1) {
            if (client.getClientName() != null || client.getTypeclient()!= null) {
                if (client.getClientName().equals(name)&& client.getTypeclient().equals(type)) {
                    return client.getClientid();
                }
            }
        } return 0L;
    }
    public Flux<ClientsAddressDTO> filerTypeName(String type, String name) {
        return clientsService.filerTypeName(type, name)
                .map(this::addressClientConvertToDtoFromClientsDTO);
}

   @Override
    public Flux<ClientsAddressDTO> filerAddress(String address){
       Flux<ClientsAddressDTO> k= addressesService.filerAddress(address)
                .map(this::addressClientConvertToDtoFromAddressDTO);
       return k;
    }
}

