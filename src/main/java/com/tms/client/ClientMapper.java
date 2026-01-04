package com.tms.client;

import com.tms.company.Company;

public class ClientMapper {

    public static ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail().toLowerCase())
                .phone(client.getPhone())
                .address(client.getAddress())
                .build();
    }

    public static Client toEntity(ClientRequest request, Company company) {
        return Client.builder()
                .name(request.name())
                .email(request.email().toLowerCase())
                .phone(request.phone())
                .address(request.address())
                .company(company)
                .build();
    }
}
