package com.example.flightmanagement.flight.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public record FlightUpdateRequest(
        @NotBlank @Pattern(regexp = "^[A-Z]{2}\\s?[0-9]{3,4}$") String flightNumber,
        @NotBlank String departurePlace,
        @NotNull @DateTimeFormat LocalDateTime departureTime,
        @NotBlank String arrivalPlace,
        @NotNull @DateTimeFormat LocalDateTime arrivalTime,
        @Min(1) Integer numberOfSeats
) {
}
