package com.tms.employees;

import com.tms.company.CompanyNotFoundException;
import com.tms.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UUID companyId = UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2");

    public List<EmployeeDto> findAllEmployees() {
        return userRepository.findAllActiveUsers(companyId)
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public EmployeeDto findEmployeeById(UUID id) {
        var user = userRepository.findActiveUserById(id, companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        return EmployeeMapper.toDto(user);
    }

    public List<EmployeeDto> findAllManagers() {
        return userRepository.findAllActiveUsersByRole(EmployeeRole.ROLE_MANAGER,companyId)
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    public List<EmployeeDto> findAllDrivers() {
        return userRepository.findAllActiveUsersByRole(EmployeeRole.ROLE_DRIVER,companyId)
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }


    @Transactional
    public EmployeeDto registerEmployee(EmployeeRegisterRequest userRequest) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(CompanyNotFoundException::new);

        var existingUser = userRepository.findByEmail(userRequest.email().toLowerCase(), companyId);
        if (existingUser.isPresent()) throw new EmployeeAlreadyExistsException();
        else {
            var newUser = EmployeeMapper.toEntity(userRequest, company);
            newUser.setEmail(userRequest.email().toLowerCase());
            return EmployeeMapper.toDto(userRepository.save(newUser));
        }
    }

    @Transactional
    public void deleteEmployee(UUID id) {
        var user = userRepository.findActiveUserById(id, companyId)
                .orElseThrow(EmployeeNotFoundException::new);

        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public EmployeeDto updateEmployee(UUID id, EmployeeRegisterRequest userRequest) {

        // Verify if user exists
        var user = userRepository.findById(id)
                .filter(u -> u.getCompany().getId().equals(companyId))
                .orElseThrow(EmployeeNotFoundException::new);

        // Verify if someone else has the same email
        userRepository.findByEmail(userRequest.email().toLowerCase(), companyId)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new EmployeeAlreadyExistsException();
                    }
                });

        // update
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email().toLowerCase());
        user.setPassword(userRequest.password());
        user.setRole(EmployeeRole.valueOf(userRequest.role().name()));
        user.setPhone(userRequest.phone());
        user.setDeleted(false);
        user.setDeletedAt(null);

        return EmployeeMapper.toDto(userRepository.save(user));
    }

}