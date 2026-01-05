package com.tms.employees.driver_profile;

import com.tms.delivery.Delivery;
import com.tms.employees.*;
import com.tms.vehicule.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverProfileService {

    private final EmployeeRepository employeeRepository;
    private final UUID companyId = UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2");


    public List<EmployeeDto> findAllDrivers() {
        return employeeRepository.findAllActiveUsersByRole(EmployeeRole.ROLE_DRIVER,companyId)
                .stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

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


    // get user with there licence category
    public List<EmployeeDto> getAvailableDriversAt(LocalDateTime requestedTime, LicenseCategory licenseCategory) {
        var criteria = EmployeeSearchCriteria.builder()
                .role(EmployeeAllowedRoles.ROLE_DRIVER)
                .licenseCategory(licenseCategory)
                .availableAt(requestedTime).build();
        var specification = EmployeeSpecifications.withCriteria(criteria,companyId);
        return employeeRepository.findAll(specification).stream()
                .map(EmployeeMapper::toDto)
                .toList();
    }

    // employee is driver
    public boolean isDriverValidAndAvailableAt(Employee employee,Vehicle vehicle, LocalDateTime requestedTime) {
        var isExpired = isDriverLicenseExpiredAt(employee.getDriverProfile(), LocalDateTime.now());
        if(isExpired) return false;
        var requiredLicense = getRequiredLicenseCategoryForDelivery(vehicle);
        if(requiredLicense == null) return false;
        var criteria = EmployeeSearchCriteria.builder()
                .availableAt(requestedTime)
                .role(EmployeeAllowedRoles.ROLE_DRIVER)
                .build();
        var spec = EmployeeSpecifications.withCriteria(criteria, companyId);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("id"), employee.getId()));
        return employeeRepository.exists(spec);
    }


    private boolean isDriverLicenseExpiredAt(DriverProfile profile, LocalDateTime dateTimeToTest) {
        if (profile.getLicenseExpiryDate() == null) {
            return false;
        }
        LocalDate date = dateTimeToTest.toLocalDate();
        return date.isAfter(profile.getLicenseExpiryDate());
    }


    public LicenseCategory getRequiredLicenseCategoryForDelivery(Vehicle vehicle) {
        var vehicleType = vehicle.getVehicleType();
        return switch (vehicleType) {
            case TRUCK -> LicenseCategory.C;
            case HEAVY_TRUCK -> LicenseCategory.CE;
            default -> LicenseCategory.B;
        };
    }
}
