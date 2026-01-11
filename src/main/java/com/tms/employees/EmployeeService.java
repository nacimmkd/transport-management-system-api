package com.tms.employees;

import com.tms.common.CodeGeneratorUtil;
import com.tms.company.CompanyNotFoundException;
import com.tms.company.CompanyRepository;
import com.tms.employees.driver.*;
import com.tms.notification.EmailNotificationService;
import com.tms.notification.EmailTemplates;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final UUID companyId = UUID.fromString("98798865-0dc2-4cc0-8661-f357b21d5d6b");

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

    public EmployeeDto findEmployeeByEmail(String email) {
        var employee = employeeRepository.findActiveByEmail(email,companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        return EmployeeMapper.toDto(employee);
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
            driverService.registerDriverProfile(newEmployee,employeeRequest.driverProfile());
        }
        // Save
        var password = CodeGeneratorUtil.generatePassword();
        System.out.println(password);
        newEmployee.setPassword(passwordEncoder.encode(password));
        newEmployee.setEmail(employeeRequest.email().toLowerCase());
        var savedUser = employeeRepository.save(newEmployee);

        // Send email with login credentials
        sendLoginCredentials(
                newEmployee.getEmail(),
                newEmployee.getUsername(),
                password);
        return EmployeeMapper.toDto(savedUser);
    }


    @Transactional
    public void deleteEmployee(UUID id) {
        var employee = employeeRepository.findActiveUserById(id, companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        employee.setDeleted(true);
        employee.setDeletedAt(LocalDateTime.now());
        employee.deleteDriverProfile(employee.getDriverProfile());
        employeeRepository.save(employee);
    }

    @Transactional
    public EmployeeDto updateEmployee(UUID id, EmployeeUpdateRequest employeeRequest) {
        // Verify if employee exists only on not deleted ones
        var employee = employeeRepository.findActiveUserById(id, companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        String normalizedEmail = employeeRequest.email().toLowerCase();
        // Verify if someone else has the same email only on not deleted
        employeeRepository.findActiveByEmail(normalizedEmail, companyId)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new EmployeeAlreadyExistsException();
                    }
                });
        // update
        employee.setUsername(employeeRequest.username());
        employee.setEmail(normalizedEmail);
        employee.setPassword(passwordEncoder.encode(employeeRequest.password()));
        employee.setPhone(employeeRequest.phone());

        //update driver profile
        if(employee.getRole().equals(EmployeeRole.ROLE_DRIVER)) {
            driverService.updateDriverProfile(employee, employeeRequest.driverProfile());
        }
        return EmployeeMapper.toDto(employeeRepository.save(employee));
    }

    public void resendCredentialsEmail(UUID id) {
        var employee = employeeRepository.findActiveUserById(id, companyId).orElseThrow(EmployeeNotFoundException::new);
        sendLoginCredentials(employee.getEmail(),
                employee.getUsername(),
                employee.getPassword());
    }

    private void sendLoginCredentials(String to, String name, String password) {
        var template = EmailTemplates.getEmailCredentialTemplate(name,to,password);
        notificationService.sendEmail(to,"TMS - Login Credentials", template);
    }
}