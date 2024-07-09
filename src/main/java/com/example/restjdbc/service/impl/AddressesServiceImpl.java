package com.example.restjdbc.service.impl;

import com.example.restjdbc.DTO.AddressesDTO;
import com.example.restjdbc.entity.AddressesEntity;
import com.example.restjdbc.entity.ClientsEntity;
import com.example.restjdbc.repository.AddressRepo;
import com.example.restjdbc.repository.ClientsRepo;
import com.example.restjdbc.service.interfaces.AddressesService;

import com.example.restjdbc.service.interfaces.ClientsService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
@Service
@Validated
public class AddressesServiceImpl implements AddressesService {
    private final AddressRepo addressRepository;


    public AddressesServiceImpl(AddressRepo addressRepository) {
        this.addressRepository = addressRepository;
    }
    @Override
    public Mono<AddressesDTO> findAddressesDtoById(long addressId) {
        return Mono.justOrEmpty(addressRepository.findById(addressId)).map(h-> addressConvertToDto(h));
    }
//    @Override
//    public Flux<AddressesDTO> findAddressesDtoBClientId(Long id) {
//       return Flux.fromIterable(addressRepository.findAllByClientIsLike(id)).map(h-> addressConvertToDto(h));
//    }

    @Override
    public Flux<AddressesDTO> findAll() {                                 //настроить нормально маппер!!!!!!!!!!!!!
        return Flux.fromIterable(addressRepository.findAll()).map(entity -> {
                            Long client = entity.getClient();
                           return AddressesDTO.builder()
                                    .client(client != null ? client : 0)
                                    .addressid(entity.getAddressid())
                                    .ip(entity.getIp())
                                    .mac(entity.getMac())
                                   .model(entity.getModel())
                                  .address(entity.getAddress())
                                   .build();
                        }
                );
    }
    @Transactional
    @Override
    public Mono<AddressesDTO> create(AddressesEntity entity) {
        if(entity!=null){

                return Mono.just(addressRepository.save(entity)).map(h-> addressConvertToDto(h));
        }
        return Mono.empty();
    }
    @Transactional
    //@Modifying //блокирует доступ других пользователей
    //@Query("UPDATE address SET ip=:ip WHERE id=:id")
    @Override
    public Mono<AddressesDTO> update(AddressesDTO dto) {
        if(dto!=null){
            return Mono.just(addressRepository.save(addressConvertToEntity(dto))).map(h-> addressConvertToDto(h));
        }
        return Mono.empty();
    }
    @Transactional
    @Override
    public void remove(long id) {
        Optional<AddressesEntity> addressBd = addressRepository.findById(id);
        addressRepository.delete(addressBd.get());
    }
    public AddressesDTO addressConvertToDto(AddressesEntity entity){ // совсем не работает маппер, срочно разобраться с получением лонга из объекта клиента
        return AddressesDTO.builder()
                .client(entity.getClient())
                .address(entity.getAddress())
                .ip(entity.getIp())
                .mac(entity.getMac())
                .model(entity.getModel())
                .addressid(entity.getAddressid())
                .build();
    }

    public AddressesEntity addressConvertToEntity(AddressesDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return Objects.isNull(dto)? null: modelMapper.map(dto, AddressesEntity.class);
    }
    @Override
    public Flux<AddressesDTO> filerAddress(String address){
        String k="%"+address+"%";
        return Flux.fromIterable(addressRepository.findAllByAddressIsLike(k)).map(this::addressConvertToDto);
    }
}