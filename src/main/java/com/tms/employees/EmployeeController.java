package com.tms.employees;

import com.tms.common.ErrorDto;
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

    private final EmployeeService userService;


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findEmployeeById(id));
    }

    @GetMapping("/managers")
    public ResponseEntity<List<EmployeeDto>> getAllManagers() {
        return ResponseEntity.ok(userService.findAllManagers());
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<EmployeeDto>> getAllDrivers() {
        return ResponseEntity.ok(userService.findAllDrivers());
    }


    @PostMapping
    public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeRegisterRequest userRequest, UriComponentsBuilder uriBuilder) {
        var createdUser = userService.registerEmployee(userRequest);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(createdUser.id()).toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateUser(
            @PathVariable UUID id,
            @RequestBody EmployeeRegisterRequest userRequest) {
        return ResponseEntity.ok(userService.updateEmployee(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleUserAlreadyExistsException(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(e.getMessage()));
    }
}