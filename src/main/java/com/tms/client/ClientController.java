package com.tms.client;

import com.tms.common.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<ClientDto> findAll() {
        return clientService.findAllClients();
    }


    @GetMapping("/{id}")
    public ClientDto findById(@PathVariable UUID id) {
        return clientService.findClientById(id);
    }

    @PostMapping("/search")
    public List<ClientDto> searchClients(@RequestBody ClientSearchCriteria search) {
        return clientService.searchClients(search);
    }

    @PostMapping
    public ResponseEntity<ClientDto> registerClient(
            @RequestBody  ClientRequest clientRequest, UriComponentsBuilder uriBuilder) {
        var clientDto = clientService.registerClient(clientRequest);
        var uri = uriBuilder.path("/clients/{id}").buildAndExpand(clientDto.id()).toUri();
        return ResponseEntity.created(uri).body(clientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable UUID id, @RequestBody ClientRequest clientRequest ) {
        var clientDto = clientService.updateClient(id, clientRequest);
        return ResponseEntity.ok(clientDto);
    }


    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorDto> handleClientNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(ClientExistsException.class)
    public ResponseEntity<ErrorDto> handleClientDeletedException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }

}
