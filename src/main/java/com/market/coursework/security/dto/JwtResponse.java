package com.market.coursework.security.dto;

import lombok.Data;

@Data
public class JwtResponse {
    private Boolean access;
    private String message;

    public JwtResponse() {
        this.access = true;
        this.message = "Authenticated successfully";
    }

    public JwtResponse(String message) {
        this.message = message;
        this.access = false;
    }
}
