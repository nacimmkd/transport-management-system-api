package com.tms.client;

import com.tms.company.CompanyNotFoundException;
import com.tms.company.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;
    private final UUID companyId = UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2");

    public List<ClientDto> findAllClients() {
        return clientRepository.findAllActiveClients(companyId)
                .stream()
                .map(ClientMapper::toDto)
                .toList();
    }

    public ClientDto findClientById(UUID id) {
        var client = clientRepository.findActiveClientById(id,companyId).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.toDto(client);
    }

    public List<ClientDto> searchClients(ClientSearchCriteria criteria) {
        Specification<Client> spec = ClientSpecifications.withCriteria(criteria,companyId);
        return clientRepository.findAll(spec).stream()
                .map(ClientMapper::toDto)
                .toList();
    }

    @Transactional
    public ClientDto registerClient(ClientRequest clientRequest) {

        var company = companyRepository.findById(companyId)
                .orElseThrow(CompanyNotFoundException::new);

        String email = clientRequest.email().toLowerCase();
        var existingClient = clientRepository.findByEmail(email, companyId);

        if (existingClient.isPresent()) {
            throw new ClientExistsException();
        } else {
            var newClient = ClientMapper.toEntity(clientRequest, company);
            newClient.setEmail(email);
            newClient.setDeleted(false);
            return ClientMapper.toDto(clientRepository.save(newClient));
        }
    }

    @Transactional
    public void deleteClient(UUID id) {
        var client = clientRepository.findActiveClientById(id,companyId).orElseThrow(ClientNotFoundException::new);
        client.setDeleted(true);
        client.setDeletedAt(LocalDateTime.now());
        clientRepository.save(client);
    }

    @Transactional
    public ClientDto updateClient(UUID id, ClientRequest clientRequest) {

        // Verify if client exists
        var client = clientRepository.findById(id)
                .filter(c -> c.getCompany().getId().equals(companyId))
                .orElseThrow(ClientNotFoundException::new);

        // Verify if another client has the same email
        clientRepository.findByEmail(clientRequest.email().toLowerCase(), companyId)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new ClientExistsException();
                    }
                });

        // update
        client.setName(clientRequest.name());
        client.setAddress(clientRequest.address());
        client.setPhone(clientRequest.phone());
        client.setEmail(clientRequest.email());
        client.setDeleted(false);
        client.setDeletedAt(null);
        return ClientMapper.toDto(clientRepository.save(client));
    }

}
