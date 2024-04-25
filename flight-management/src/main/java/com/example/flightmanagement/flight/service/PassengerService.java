package com.example.flightmanagement.flight.service;

import com.example.flightmanagement.flight.dto.PassengerCreateRequest;
import com.example.flightmanagement.flight.dto.PassengerResource;
import com.example.flightmanagement.flight.dto.PassengerUpdateRequest;
import com.example.flightmanagement.flight.repository.FlightRepository;
import com.example.flightmanagement.flight.repository.PassengerRepository;
import com.example.flightmanagement.flight.service.mapper.PassengerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final FlightRepository flightRepository;
    private final FlightService flightService;

    @Transactional
    public PassengerResource createPassenger(PassengerCreateRequest passengerCreateRequest) {
        validateFlightExists(passengerCreateRequest.flightId());
        final var passengerToSave = passengerMapper.mapPassengerCreateRequestToPassenger(passengerCreateRequest);

        final var flight = flightRepository.findById(passengerCreateRequest.flightId()).orElseThrow();
        flightService.addPassengerToFlight(flight, passengerToSave);
        final var savedPassenger = passengerRepository.save(passengerToSave);

        return passengerMapper.mapPassengerToPassengerResource(savedPassenger);
    }

    @Transactional(readOnly = true)
    public PassengerResource getPassengerById(UUID id) {
        validatePassengerExists(id);
        final var passenger = passengerRepository.findById(id).orElseThrow();
        return passengerMapper.mapPassengerToPassengerResource(passenger);
    }

    @Transactional(readOnly = true)
    public Page<PassengerResource> getPassengers(Pageable pageable) {
        return passengerRepository.findAll(pageable)
                .map(passengerMapper::mapPassengerToPassengerResource);
    }

    @Transactional
    public PassengerResource updatePassenger(UUID id, PassengerUpdateRequest passengerUpdateRequest) {
        validatePassengerExists(id);
        validateFlightExists(passengerUpdateRequest.flightId());

        final var passengerToUpdate = passengerMapper.mapPassengerUpdateRequestToPassenger(passengerUpdateRequest);
        passengerToUpdate.setId(id);
        final var oldPassenger = passengerRepository.findById(id).orElseThrow();
        passengerToUpdate.setFlight(oldPassenger.getFlight());

        if (passengerUpdateRequest.flightId() != oldPassenger.getFlight().getId()) {
            final var oldFlight = oldPassenger.getFlight();
            flightService.removePassengerFromFlight(oldFlight, oldPassenger);
            final var newFlight = flightRepository.findById(passengerUpdateRequest.flightId()).orElseThrow();
            passengerToUpdate.setFlight(null);
            flightService.addPassengerToFlight(newFlight, passengerToUpdate);
        }
        final var updatedPassenger = passengerRepository.save(passengerToUpdate);

        return passengerMapper.mapPassengerToPassengerResource(updatedPassenger);
    }

    @Transactional
    public void deletePassenger(UUID id) {
        validatePassengerExists(id);
        final var passenger = passengerRepository.findById(id).orElseThrow();
        final var flight = passenger.getFlight();

        flightService.removePassengerFromFlight(flight, passenger);
        passengerRepository.deleteById(id);
    }

    private void validateFlightExists(UUID id) {
        if (!flightRepository.existsById(id)) {
            throw new NoSuchElementException("Flight with id " + id + " not found");
        }
    }

    private void validatePassengerExists(UUID id) {
        if (!passengerRepository.existsById(id)) {
            throw new NoSuchElementException("Passenger with id " + id + " not found");
        }
    }
}
