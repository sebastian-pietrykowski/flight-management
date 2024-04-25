package com.example.flightmanagement.flight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record SearchFlightParams(
        @NotBlank String departurePlace,
        @NotNull @DateTimeFormat LocalDateTime minDepartureTime,
        @NotBlank String arrivalPlace
) {
}
