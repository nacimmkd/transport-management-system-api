package com.tms.client;


public record ClientSearchCriteria(
        String name,
        String phone,
        String email
) {
}
