package com.tms.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping
    public ResponseEntity<CompanyDto> getCompany() {
        var company = companyService.getCompany();
        return ResponseEntity.ok().body(company);
    }


    @PostMapping("/register")
    public ResponseEntity<CompanyRegistrationResponse> register(
            @RequestBody CompanyRegistrationRequest registrationRequest,
            UriComponentsBuilder uriBuilder
    ) {
        var response = companyService.RegisterAdminWithCompany(registrationRequest);
        var uri = uriBuilder.path("/companies").build().toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> delete(@PathVariable Long companyId) {
        companyService.deleteCompany();
        return ResponseEntity.noContent().build();
    }

}
