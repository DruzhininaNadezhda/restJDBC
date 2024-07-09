package com.example.restjdbc.service.interfaces;

import com.example.restjdbc.DTO.AddressesDTO;
import com.example.restjdbc.DTO.ClientsDTO;
import com.example.restjdbc.DTO.TypeEnum;
import com.example.restjdbc.entity.AddressesEntity;
import com.example.restjdbc.entity.ClientsEntity;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Validated
public interface ClientsService {
    public Mono<ClientsDTO>  update(@Valid ClientsDTO dto);
    public Optional<ClientsDTO> findClientsDtoById(long clientID);
    public Optional<ClientsDTO> create(ClientsEntity entity);
    public static ClientsDTO clientConvertToDto(ClientsEntity entity){
        return  ClientsDTO.builder()
                .clientName(entity.getClientName())
                .clientid(entity.getClientid())
                .datecreatclient(entity.getDatecreatclient())
                .typeclient(TypeEnum.getType(entity.getTypeclient()))
                //.status(entity.getStatus())
                .addresses(entity.getAddressesEntities().stream().
                        map(n-> addressConvertToDto(n)).collect(Collectors.toSet())
                )
                .build();
    }
    public static AddressesDTO addressConvertToDto(AddressesEntity n){
       return AddressesDTO.builder().address(n.getAddress()).ip(n.getIp()).
                mac(n.getMac()).client(n.getClient()).addressid(n.getAddressid()).model(n.getModel()).build();
    }
    public ClientsEntity convertToEntity(ClientsDTO dto);
    public Flux<ClientsDTO> filerTypeName(String type, String name);
    public void remove(long id);
    //public void help (Long id);
    //public void helpStarted (Long id);

}
