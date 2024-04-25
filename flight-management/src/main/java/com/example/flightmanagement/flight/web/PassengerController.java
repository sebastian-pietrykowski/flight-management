package com.example.flightmanagement.flight.web;

import com.example.flightmanagement.flight.dto.*;
import com.example.flightmanagement.flight.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    ResponseEntity<PassengerResource> createPassenger(@RequestBody @Valid PassengerCreateRequest passengerCreateRequest) {
        final var passenger = passengerService.createPassenger(passengerCreateRequest);
        return ResponseEntity.ok(passenger);
    }

    @GetMapping("/{id}")
    ResponseEntity<PassengerResource> getPassengerById(@PathVariable UUID id) {
        final var passenger = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passenger);
    }

    @GetMapping
    ResponseEntity<Page<PassengerResource>> getPassengers(Pageable pageable) {
        final var passengers = passengerService.getPassengers(pageable);
        return ResponseEntity.ok(passengers);
    }

    @PutMapping("/{id}")
    ResponseEntity<PassengerResource> updatePassenger(
            @PathVariable UUID id,
            @RequestBody @Valid PassengerUpdateRequest passengerUpdateRequest
    ) {
        final var passenger = passengerService.updatePassenger(id, passengerUpdateRequest);
        return ResponseEntity.ok(passenger);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePassenger(@PathVariable UUID id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
