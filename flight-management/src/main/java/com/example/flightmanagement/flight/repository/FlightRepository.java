package com.example.flightmanagement.flight.repository;

import com.example.flightmanagement.flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {
}
