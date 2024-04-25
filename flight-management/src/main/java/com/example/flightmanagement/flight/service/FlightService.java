package com.example.flightmanagement.flight.service;

import com.example.flightmanagement.flight.domain.Flight;
import com.example.flightmanagement.flight.domain.Passenger;
import com.example.flightmanagement.flight.dto.FlightCreateRequest;
import com.example.flightmanagement.flight.dto.FlightResource;
import com.example.flightmanagement.flight.dto.FlightUpdateRequest;
import com.example.flightmanagement.flight.dto.SearchFlightParams;
import com.example.flightmanagement.flight.repository.FlightRepository;
import com.example.flightmanagement.flight.service.mapper.FlightMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Transactional
    public FlightResource createFlight(FlightCreateRequest flightCreateRequest) {
        final var flightToSave = flightMapper.mapFlightCreateRequestToFlight(flightCreateRequest);
        validateIfArrivalTimeIsAfterDepartureTime(flightToSave);
        final var savedFlight = flightRepository.save(flightToSave);

        return flightMapper.mapFlightToFlightResource(savedFlight);
    }

    @Transactional(readOnly = true)
    public FlightResource getFlightById(UUID id) {
        validateFlightExists(id);
        final var flight = flightRepository.findById(id).orElseThrow();
        return flightMapper.mapFlightToFlightResource(flight);
    }

    @Transactional(readOnly = true)
    public Page<FlightResource> getFlights(Pageable pageable) {
        return flightRepository.findAll(pageable).map(flightMapper::mapFlightToFlightResource);
    }

    @Transactional
    public FlightResource updateFlight(UUID id, FlightUpdateRequest flightUpdateRequest) {
        validateFlightExists(id);

        var flightToUpdate = flightMapper.mapFlightUpdateRequestToFlight(flightUpdateRequest);
        flightToUpdate.setId(id);
        validateIfArrivalTimeIsAfterDepartureTime(flightToUpdate);

        final var oldPassengers = flightRepository.findById(id).orElseThrow().getPassengers();
        validateIfNumberOfSeatsIsNotSmallerThanNumberOfPassengers(
                flightToUpdate, oldPassengers.size() + 1
        );
        flightToUpdate.setPassengers(oldPassengers);

        final var updatedFlight = flightRepository.save(flightToUpdate);

        return flightMapper.mapFlightToFlightResource(updatedFlight);
    }

    @Transactional
    public void deleteFlight(UUID id) {
        validateFlightExists(id);
        flightRepository.deleteById(id);
    }

    public void addPassengerToFlight(Flight flight, Passenger passenger) {
        validateIfNumberOfSeatsIsNotSmallerThanNumberOfPassengers(
                flight, flight.getPassengers().size() + 1
        );
        passenger.setFlight(flight);
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);
    }

    public void removePassengerFromFlight(Flight flight, Passenger passenger) {
        flight.getPassengers().remove(passenger);
        final var availableSeats = flight.getNumberOfSeats() - flight.getPassengers().size();
        flight.setAvailableSeats(availableSeats);
        flightRepository.save(flight);
    }

    @Transactional(readOnly = true)
    public List<FlightResource> getFlightsByParams(SearchFlightParams searchFlightParams) {
        return flightRepository.findAll().stream()
                .filter(flight -> flight.getDeparturePlace().equals(searchFlightParams.departurePlace()))
                .filter(flight -> flight.getArrivalPlace().equals(searchFlightParams.arrivalPlace()))
                .filter(flight -> flight.getDepartureTime().isAfter(searchFlightParams.minDepartureTime()))
                .map(flightMapper::mapFlightToFlightResource)
                .toList();
    }

    private void validateFlightExists(UUID id) {
        if (!flightRepository.existsById(id)) {
            throw new NoSuchElementException("Flight with id " + id + " not found");
        }
    }

    private void validateIfNumberOfSeatsIsNotSmallerThanNumberOfPassengers(
            Flight flight,
            int passengersCount
    ) {
        if (flight.getNumberOfSeats() < passengersCount) {
            throw new IllegalArgumentException("Number of seats is less than the number of passengers");
        }
    }

    private void validateIfArrivalTimeIsAfterDepartureTime(Flight flight) {
        if (flight.getDepartureTime().isAfter(flight.getArrivalTime())) {
            throw new IllegalArgumentException("Departure time is after arrival time");
        }
    }
}
