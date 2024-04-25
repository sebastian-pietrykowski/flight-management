package com.example.flightmanagement.flight.repository;

import com.example.flightmanagement.flight.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}
