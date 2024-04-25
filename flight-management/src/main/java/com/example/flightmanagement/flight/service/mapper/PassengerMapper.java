package com.example.flightmanagement.flight.service.mapper;

import com.example.flightmanagement.flight.domain.Flight;
import com.example.flightmanagement.flight.domain.Passenger;
import com.example.flightmanagement.flight.dto.PassengerCreateRequest;
import com.example.flightmanagement.flight.dto.PassengerResource;
import com.example.flightmanagement.flight.dto.PassengerUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    Passenger mapPassengerCreateRequestToPassenger(PassengerCreateRequest passengerCreateRequest);
    Passenger mapPassengerUpdateRequestToPassenger(PassengerUpdateRequest passengerUpdateRequest);
    @Mapping(source = "flight", target = "flightId", qualifiedByName = "flightToFlightId")
    PassengerResource mapPassengerToPassengerResource(Passenger passenger);

    @Named("flightToFlightId")
    default UUID flightToFlightId(Flight flight) {
        return flight.getId();
    }
}
