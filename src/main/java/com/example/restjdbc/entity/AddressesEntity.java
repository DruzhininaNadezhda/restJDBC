package com.example.restjdbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Table(name = "addresses")
public class AddressesEntity {

    @Id
    @Column("addressid")
    private Long addressid;

    @Column("ip")
    private String ip;

    @Column("mac")
    private String mac;
    @Column( "model")
    private String model;

    @Column( "address")
    private String address;

    @Column("client")
    private Long client;

}
