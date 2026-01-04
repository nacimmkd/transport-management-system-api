package com.tms.employees;

import com.tms.common.CodeGeneratorUtil;
import com.tms.company.CompanyNotFoundException;
import com.tms.company.CompanyRepository;
import com.tms.employees.driver_profile.*;
import com.tms.notification.EmailNotificationService;
import com.tms.notification.EmailTemplates;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final EmailNotificationService notificationService;
    private final DriverProfileService driverProfileService;
    private final UUID companyId = UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2");

    public List<EmployeeDto> findAllEmployees() {
        return employeeRepository.findAllActiveUsers(companyId)
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public EmployeeDto findEmployeeById(UUID id) {
        var user = employeeRepository.findActiveUserById(id, companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        return EmployeeMapper.toDto(user);
    }

    public List<EmployeeDto> searchEmployees(EmployeeSearchCriteria criteria) {
        var spec = EmployeeSpecifications.withCriteria(criteria, companyId);
        return employeeRepository.findAll(spec).stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public List<EmployeeDto> findAllManagers() {
        return employeeRepository.findAllActiveUsersByRole(EmployeeRole.ROLE_MANAGER,companyId)
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public List<EmployeeDto> findAllDrivers() {
        return employeeRepository.findAllActiveUsersByRole(EmployeeRole.ROLE_DRIVER,companyId)
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }


    @Transactional
    public EmployeeDto registerEmployee(EmployeeRegisterRequest employeeRequest) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(CompanyNotFoundException::new);
        var exists = employeeRepository.existsByEmailAndCompanyId(employeeRequest.email().toLowerCase(),companyId);
        if (exists) throw new EmployeeAlreadyExistsException();
        var newEmployee = EmployeeMapper.toEntity(employeeRequest, company);
        // if role is DRIVER, we save driverProfile
        var role = employeeRequest.role();
        if(role.equals(EmployeeAllowedRoles.ROLE_DRIVER)) {
            driverProfileService.registerDriverProfile(newEmployee,employeeRequest.driverProfile());
        }
        // Save
        var password = CodeGeneratorUtil.generatePassword();
        newEmployee.setPassword(password);
        newEmployee.setEmail(employeeRequest.email().toLowerCase());
        var savedUser = employeeRepository.save(newEmployee);
        // Send email with login credentials
        var name = newEmployee.getUsername();
        var email =  newEmployee.getEmail();
        //sendLoginCredentials(email, name, password);
        return EmployeeMapper.toDto(savedUser);
    }


    @Transactional
    public void deleteEmployee(UUID id) {
        var employee = employeeRepository.findActiveUserById(id, companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        employee.setDeleted(true);
        employee.setDeletedAt(LocalDateTime.now());
        employeeRepository.save(employee);
    }

    @Transactional
    public EmployeeDto updateEmployee(UUID id, EmployeeUpdateRequest userRequest) {
        // Verify if employee exists only on not deleted ones
        var user = employeeRepository.findActiveUserById(id, companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        String normalizedEmail = userRequest.email().toLowerCase();
        // Verify if someone else has the same email only on not deleted
        employeeRepository.findActiveByEmail(normalizedEmail, companyId)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new EmployeeAlreadyExistsException();
                    }
                });
        // update
        user.setUsername(userRequest.username());
        user.setEmail(normalizedEmail);
        user.setPassword(userRequest.password())
        ;user.setPhone(userRequest.phone());
        return EmployeeMapper.toDto(employeeRepository.save(user));
    }

    public void resendCredentialsEmail(UUID id) {
        var employee = employeeRepository.findActiveUserById(id, companyId).orElseThrow(EmployeeNotFoundException::new);
        var name = employee.getUsername();
        var email = employee.getEmail();
        var password = employee.getPassword();
        sendLoginCredentials(email, name, password);
    }

    private void sendLoginCredentials(String to, String name, String password) {
        var template = EmailTemplates.getEmailCredentialTemplate(name,to,password);
        notificationService.sendEmail(to,"TMS - Login Credentials", template);
    }
}