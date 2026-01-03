package com.tms.vehicule;

import com.tms.client.ClientRequest;
import com.tms.common.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public List<VehicleDto> findAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/{id}")
    public VehicleDto findById(@PathVariable  UUID id) {
        return vehicleService.findById(id);
    }

    @PostMapping
    public ResponseEntity<VehicleDto> registerVehicle(
            @RequestBody VehicleRequest vehicleRequest,
            UriComponentsBuilder uriBuilder) {

        var vehicleDto = vehicleService.registerVehicle(vehicleRequest);
        var uri = uriBuilder.path("/vehicles/{id}").buildAndExpand(vehicleDto.id()).toUri();
        return ResponseEntity.created(uri).body(vehicleDto);
    }

    @PostMapping("/search")
    public List<VehicleDto> searchVehicles(
            @RequestBody VehicleSearchCriteria criteria
    ) {
         return vehicleService.searchVehicle(criteria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable UUID id, @RequestBody VehicleRequest vehicleRequest ){
        var updated = vehicleService.updateVehicle(id, vehicleRequest);
        return ResponseEntity.ok(updated);
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<VehicleDto> updateVehicleStatus(
            @PathVariable UUID id,
            @RequestBody UpdateStatusRequest request
    ) {
        var updated = vehicleService.updateVehicleStatus(id, request);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ErrorDto> handleVehicleNotFoundException(VehicleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(VehicleExistsException.class)
    public ResponseEntity<ErrorDto> handleVehicleExistsException(VehicleExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(e.getMessage()));
    }
}
