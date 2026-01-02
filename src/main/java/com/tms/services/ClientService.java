package com.tms.services;

import com.tms.dtos.ClientDto;
import com.tms.dtos.ClientRequest;
import com.tms.exceptions.ClientDeletedException;
import com.tms.exceptions.ClientNotFoundException;
import com.tms.mappers.ClientMapper;
import com.tms.model.Client;
import com.tms.model.Company;
import com.tms.repository.ClientRepository;
import com.tms.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;

    public List<ClientDto> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .filter(Client::isActive)
                .map(ClientMapper::toDto)
                .toList();
    }

    public ClientDto findClientById(UUID id) {
        var client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        if(!client.isActive()) throw new ClientDeletedException();
        return ClientMapper.toDto(client);
    }

    public ClientDto registerClient(ClientRequest clientRequest) {
        // IMPORTANT: a modifier par la suite
        var company = companyRepository.findById(UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2")).orElse(null);

        var client = clientRepository.findByEmail(clientRequest.email());
        if(client == null) {
            var newClient = ClientMapper.toEntity(clientRequest, company);
            clientRepository.save(newClient);
            return ClientMapper.toDto(newClient);
        } else {
            return updateClient(client.getId(), clientRequest);
        }
    }

    public void deleteClient(UUID id) {
        var client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        if (!client.isActive()) throw new ClientDeletedException();
        client.setActive(false);
        clientRepository.save(client);
    }

    public ClientDto updateClient(UUID id, ClientRequest clientRequest) {
        var client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        if (!client.isActive()) throw new ClientDeletedException();
        client.setName(clientRequest.name());
        client.setAddress(clientRequest.address());
        client.setPhone(clientRequest.phone());
        client.setEmail(clientRequest.email());
        return ClientMapper.toDto(clientRepository.save(client));
    }

}
