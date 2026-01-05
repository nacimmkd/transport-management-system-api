package com.tms.employees.driver_profile;

import com.tms.employees.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverProfileService {

    private final EmployeeRepository employeeRepository;
    private final UUID companyId = UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2");


    public void registerDriverProfile(Employee employee, DriverProfileRequest profileRequest) {
        if (profileRequest == null) {
            throw new DriverProfileException("Le profil de chauffeur est obligatoire pour le rôle ROLE_DRIVER");
        }
        var profileEntity = DriverProfileMapper.toEntity(profileRequest);
        profileEntity.setLicenseNumber(profileEntity.getLicenseNumber().toUpperCase());
        employee.addDriverProfile(profileEntity);
    }

    // ONLY BY ADMIN
    @Transactional
    public EmployeeDto updateDriverProfile(UUID employeeId, DriverProfileRequest request) {
        var employee = employeeRepository.findActiveUserById(employeeId, companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        // Verify if it is a Driver
        if (employee.getRole() != EmployeeRole.ROLE_DRIVER) {
            throw new DriverProfileException("Cet employé n'est pas un chauffeur.");
        }
        // Update
        var profile = employee.getDriverProfile();
        profile.setLicenseNumber(request.licenseNumber().toUpperCase());
        profile.setLicenseCategory(request.licenseCategory());
        profile.setLicenseExpiryDate(request.licenseExpiryDate());
        // Save
        employee.addDriverProfile(profile);
        return EmployeeMapper.toDto(employeeRepository.save(employee));
    }


    public void validateDriver(UUID employeeId) {
        // GET active driver
        var employee = employeeRepository.findActiveUserById(employeeId, companyId)
                .orElseThrow(EmployeeNotFoundException::new);
        if (employee.getRole() != EmployeeRole.ROLE_DRIVER) {
            throw new DriverProfileException("L'employé spécifié n'est pas un chauffeur.");
        }
        var profile = employee.getDriverProfile();
        if (profile == null) {
            throw new DriverProfileException("Le chauffeur n'a pas de profil de permis enregistré.");
        }
        // validate driver license
        if(isDriverLicenseExpiredAt(profile,LocalDateTime.now())) {
            throw new DriverProfileException("Le permis est expiré");
        }
    }

    // TO BE COMPLETED
    public boolean isDriverAvailableAt(UUID employeeId, LocalDateTime requestedTime) {
        return false;
    }


    private boolean isDriverLicenseExpiredAt(DriverProfile profile, LocalDateTime dateTimeToTest) {
        if (profile.getLicenseExpiryDate() == null) {
            throw new DriverProfileException("La date d'expiration du permis est manquante.");
        }
        LocalDate date = dateTimeToTest.toLocalDate();
        return date.isAfter(profile.getLicenseExpiryDate());
    }
}
