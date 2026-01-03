package com.tms.user;

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
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UUID companyId = UUID.fromString("aed2f7aa-5eca-4df1-8881-87a5754350c2");

    public List<UserDto> findAllUsers() {
        return userRepository.findAllActiveUsers(companyId)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public UserDto findUserById(UUID id) {
        var user = userRepository.findActiveUserById(id, companyId)
                .orElseThrow(UserNotFoundException::new);
        return UserMapper.toDto(user);
    }

    public List<UserDto> findAllManagers() {
        return userRepository.findAllActiveUsersByRole(UserRole.ROLE_MANAGER,companyId)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public List<UserDto> findAllDrivers() {
        return userRepository.findAllActiveUsersByRole(UserRole.ROLE_DRIVER,companyId)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }


    @Transactional
    public UserDto registerUser(UserRequest userRequest) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(CompanyNotFoundException::new);

        var existingUser = userRepository.findByEmail(userRequest.email().toLowerCase(), companyId);
        if (existingUser.isPresent()) throw new UserAlreadyExistsException();
        else {
            var newUser = UserMapper.toEntity(userRequest, company);
            newUser.setEmail(userRequest.email().toLowerCase());
            return UserMapper.toDto(userRepository.save(newUser));
        }

    }

    @Transactional
    public void deleteUser(UUID id) {
        var user = userRepository.findActiveUserById(id, companyId)
                .orElseThrow(UserNotFoundException::new);

        user.setActive(false);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public UserDto updateUser(UUID id, UserRequest userRequest) {

        // Verify if user exists
        var user = userRepository.findById(id)
                .filter(u -> u.getCompany().getId().equals(companyId))
                .orElseThrow(UserNotFoundException::new);

        // Verify if someone else has the same email
        userRepository.findByEmail(userRequest.email().toLowerCase(), companyId)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new UserAlreadyExistsException();
                    }
                });

        // update
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email().toLowerCase());
        user.setPassword(userRequest.password());
        user.setRole(UserRole.valueOf(userRequest.role().name()));
        user.setPhone(userRequest.phone());
        user.setActive(true);
        user.setDeletedAt(null);

        return UserMapper.toDto(userRepository.save(user));
    }

}