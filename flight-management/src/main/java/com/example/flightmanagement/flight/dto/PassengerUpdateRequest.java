package com.example.flightmanagement.flight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PassengerUpdateRequest(
        @NotBlank String name,
        @NotBlank String email,
        @Pattern(regexp = "^\\+\\d{1,3}\\s?\\d{5,15}$")
        String phoneNumber,
        UUID flightId
) {
}
