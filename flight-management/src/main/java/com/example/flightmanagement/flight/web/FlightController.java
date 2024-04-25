package com.example.flightmanagement.flight.web;

import com.example.flightmanagement.flight.dto.FlightCreateRequest;
import com.example.flightmanagement.flight.dto.FlightResource;
import com.example.flightmanagement.flight.dto.FlightUpdateRequest;
import com.example.flightmanagement.flight.dto.SearchFlightParams;
import com.example.flightmanagement.flight.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @PostMapping
    ResponseEntity<FlightResource> createFlight(@RequestBody @Valid FlightCreateRequest flightCreateRequest) {
        final var flight = flightService.createFlight(flightCreateRequest);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/{id}")
    ResponseEntity<FlightResource> getFlightById(@PathVariable UUID id) {
        final var flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }

    @GetMapping
    ResponseEntity<Page<FlightResource>> getFlights(Pageable pageable) {
        final var flights = flightService.getFlights(pageable);
        return ResponseEntity.ok(flights);
    }

    @PutMapping("/{id}")
    ResponseEntity<FlightResource> updateFlight(
            @PathVariable UUID id,
            @RequestBody @Valid FlightUpdateRequest flightCreateRequest
    ) {
        final var flight = flightService.updateFlight(id, flightCreateRequest);
        return ResponseEntity.ok(flight);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteFlight(@PathVariable UUID id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("search")
    ResponseEntity<List<FlightResource>> searchFlights(
            @RequestBody @Valid SearchFlightParams searchFlightParams
    ) {
        final var flights = flightService.getFlightsByParams(searchFlightParams);
        return ResponseEntity.ok(flights);
    }
}
