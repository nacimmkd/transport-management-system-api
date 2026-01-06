package com.tms.employees;

import com.tms.common.ErrorDto;
import com.tms.employees.driver.DriverProfileException;
import com.tms.employees.driver.DriverService;
import com.tms.employees.driver.LicenseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DriverService driverService;


    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable UUID id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("/managers")
    public List<EmployeeDto> getAllManagers() {
        return employeeService.findAllManagers();
    }


    @GetMapping("/drivers")
    public List<EmployeeDto> getAllDrivers(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime availableAt,
            @RequestParam(required = false) LicenseCategory licenseType)
    {
        if(availableAt != null) {
            return driverService.getAvailableDriversAt(availableAt, licenseType);
        }
        return driverService.findAllDrivers();
    }

    @PostMapping("/search")
    public List<EmployeeDto> searchEmployees(
            @RequestBody EmployeeSearchCriteria criteria
    ) {
        return employeeService.searchEmployees(criteria);
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