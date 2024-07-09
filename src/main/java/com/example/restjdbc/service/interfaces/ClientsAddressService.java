package com.example.restjdbc.service.interfaces;

import com.example.restjdbc.DTO.AddressesDTO;
import com.example.restjdbc.DTO.ClientsAddressDTO;
import com.example.restjdbc.DTO.ClientsDTO;
import com.example.restjdbc.entity.AddressesEntity;
import com.example.restjdbc.entity.ClientsEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;
@Validated
public interface ClientsAddressService {
    public Stream<ClientsAddressDTO> findAll();
    public void remove(long id);
    public Mono<ClientsAddressDTO> create(@Valid ClientsAddressDTO dto);
    public ClientsAddressDTO addressClientConvertToDtoFromAddressDTO(AddressesDTO addressesDTO);
    //public Stream<ClientsAddressDTO> findFilter(String type, String name);
    public Flux<ClientsAddressDTO> findFilter(String type, String name);
    public ClientsAddressDTO addressClientConvertToDto(ClientsEntity entity2, AddressesEntity entity);
    public AddressesEntity addressClientConvertToAddressEntity(ClientsAddressDTO dto);
    public ClientsEntity addressClientConvertToClientEntity(ClientsAddressDTO dto);
    public long clientUniControl(String name, String type, Stream <ClientsAddressDTO> clients);
    public Flux<ClientsAddressDTO> filerTypeName(String type,String name);
    public Flux<ClientsAddressDTO> filerAddress(String address);
    public ClientsAddressDTO addressClientConvertToDtoFromClientsDTO(ClientsDTO clientsDTO);
    public Stream<ClientsAddressDTO> findById(Long id);
}
