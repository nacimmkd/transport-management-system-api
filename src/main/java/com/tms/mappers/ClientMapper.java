package com.tms.mappers;

import com.tms.dtos.ClientDto;
import com.tms.dtos.ClientRequest;
import com.tms.model.Client;
import com.tms.model.Company;

public class ClientMapper {

    public static ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .build();
    }

    public static Client toEntity(ClientRequest request, Company company) {
        return Client.builder()
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .isActive(true)
                .company(company)
                .build();
    }
}
