package com.example.flightmanagement.flight.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record FlightResource(
        UUID id,
        String flightNumber,
        String departurePlace,
        LocalDateTime departureTime,
        String arrivalPlace,
        LocalDateTime arrivalTime,
        Integer numberOfSeats,
        Integer availableSeats,
        List<UUID> passengerIds
) {
}
