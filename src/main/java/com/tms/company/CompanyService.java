package com.tms.company;

import com.tms.employees.Employee;
import com.tms.employees.EmployeeRepository;
import com.tms.employees.EmployeeRole;
import com.tms.employees.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    private final UUID companyId = UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2");


    public CompanyDto getCompany() {
        var company = companyRepository.findByIdAndDeletedFalse(companyId)
                .orElseThrow(CompanyNotFoundException::new);
        return CompanyMapper.toDto(company);
    }

    @Transactional
    public void deleteCompany(){
        var company = companyRepository.findByIdAndIsDeletedFalse(companyId)
                .orElseThrow(CompanyNotFoundException::new);
        company.deleteCompany();
        companyRepository.save(company);
    }

    @Transactional
    public CompanyRegistrationResponse RegisterAdminWithCompany(CompanyRegistrationRequest registrationRequest) {
        var exists = companyRepository.existsByEmailAndIsDeletedFalse(registrationRequest.company().email());
        if (exists) throw new CompanyAlreadyExistsException();

        var company = registerCompany(registrationRequest.company());

        var owner = Employee.builder()
                .username(registrationRequest.ownerUsername())
                .email(registrationRequest.ownerEmail().toLowerCase())
                .password(passwordEncoder.encode(registrationRequest.ownerPassword()))
                .phone(registrationRequest.ownerPhone())
                .role(EmployeeRole.ROLE_ADMIN)
                .company(company)
                .build();
        employeeRepository.save(owner);

        return CompanyRegistrationResponse.builder()
                .ownerUsername(owner.getUsername())
                .ownerEmail(owner.getEmail())
                .ownerPhone(owner.getPhone())
                .company(CompanyMapper.toDto(company)).build();
    }


    private Company registerCompany(CompanyRegisterRequest registrationRequest) {
        var company = Company.builder()
                .name(registrationRequest.name().toUpperCase())
                .siren(registrationRequest.siren())
                .address(registrationRequest.address())
                .email(registrationRequest.email().toLowerCase())
                .phone(registrationRequest.phone()).build();
        return companyRepository.save(company);
    }
}
