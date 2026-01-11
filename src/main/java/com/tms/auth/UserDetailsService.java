package com.tms.auth;

import com.tms.employees.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final UUID companyId = UUID.fromString("98798865-0dc2-4cc0-8661-f357b21d5d6b");

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = employeeRepository.findActiveByEmail(email,companyId).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
