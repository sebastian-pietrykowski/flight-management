package com.example.flightmanagement.flight.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PassengerResource(
        UUID id,
        String name,
        String email,
        String phoneNumber,
        UUID flightId
) {
}
