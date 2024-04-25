package com.example.flightmanagement.flight.service.mapper;

import com.example.flightmanagement.flight.domain.Flight;
import com.example.flightmanagement.flight.domain.Passenger;
import com.example.flightmanagement.flight.dto.FlightCreateRequest;
import com.example.flightmanagement.flight.dto.FlightResource;
import com.example.flightmanagement.flight.dto.FlightUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FlightMapper {
    @Mapping(source = "numberOfSeats", target = "availableSeats")
    Flight mapFlightCreateRequestToFlight(FlightCreateRequest flightCreateRequest);

    Flight mapFlightUpdateRequestToFlight(FlightUpdateRequest flightUpdateRequest);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "flightNumber", target = "flightNumber")
    @Mapping(source = "departurePlace", target = "departurePlace")
    @Mapping(source = "departureTime", target = "departureTime")
    @Mapping(source = "arrivalPlace", target = "arrivalPlace")
    @Mapping(source = "arrivalTime", target = "arrivalTime")
    @Mapping(source = "numberOfSeats", target = "numberOfSeats")
    @Mapping(source = "availableSeats", target = "availableSeats")
    @Mapping(source = "passengers", target = "passengerIds", qualifiedByName = "passengersToPassengerIds")
    FlightResource mapFlightToFlightResource(Flight flight);

    @Named("passengersToPassengerIds")
    default List<UUID> passengersToPassengerIds(Set<Passenger> passengers) {
        return passengers.stream().map(Passenger::getId).collect(Collectors.toList());
    }
}
