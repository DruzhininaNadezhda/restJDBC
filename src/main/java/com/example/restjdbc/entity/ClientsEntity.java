package com.example.restjdbc.entity;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "clients")
public class ClientsEntity{

    @Id
    @Column("clientid")
    private Long clientid;
    @Column("client_name")
    private String clientName;
    @Column("typeclient")
    private String typeclient;
    @Column("datecreatclient")
    private Date datecreatclient;
    @MappedCollection(idColumn = "client", keyColumn = "client")
    private Set<AddressesEntity> addressesEntities = new LinkedHashSet<>();
}
