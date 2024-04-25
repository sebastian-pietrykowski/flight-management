package com.example.flightmanagement.flight.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PassengerCreateRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @Pattern(regexp = "^\\+\\d{1,3}\\s?\\d{5,15}$")
        String phoneNumber,
        UUID flightId
) {
}
