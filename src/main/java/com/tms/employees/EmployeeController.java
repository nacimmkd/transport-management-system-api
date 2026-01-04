package com.tms.employees;

import com.tms.common.ErrorDto;
import com.tms.employees.driverProfile.DriverProfileException;
import com.tms.employees.driverProfile.DriverProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @GetMapping("/managers")
    public ResponseEntity<List<EmployeeDto>> getAllManagers() {
        return ResponseEntity.ok(employeeService.findAllManagers());
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<EmployeeDto>> getAllDrivers() {
        return ResponseEntity.ok(employeeService.findAllDrivers());
    }


    @PostMapping
    public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeRegisterRequest userRequest, UriComponentsBuilder uriBuilder) {
        var createdUser = employeeService.registerEmployee(userRequest);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(createdUser.id()).toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable UUID id,
            @RequestBody EmployeeUpdateRequest userRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, userRequest));
    }

    @PutMapping("/drivers/{id}")
    public ResponseEntity<EmployeeDto> updateDriverProfile(
            @PathVariable UUID id,
            @RequestBody EmployeeUpdateRequest userRequest) {
        return ResponseEntity.ok(employeeService.updateDriverProfile(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/resend-credentials")
    public ResponseEntity<Void> resendCredentialsEmail(@PathVariable("id") UUID employeeId) {
        employeeService.resendCredentialsEmail(employeeId);
        return ResponseEntity.accepted().build();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleUserAlreadyExistsException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(DriverProfileException.class)
    public ResponseEntity<ErrorDto> handleDriverProfileMissingException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }
}