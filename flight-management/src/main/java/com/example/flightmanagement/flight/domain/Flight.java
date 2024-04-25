package com.example.flightmanagement.flight.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(unique = true)
    String flightNumber;

    String departurePlace;

    LocalDateTime departureTime;

    String arrivalPlace;


    LocalDateTime arrivalTime;

    Integer numberOfSeats;

    Integer availableSeats;

    @OneToMany(mappedBy = "flight", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Builder.Default
    Set<Passenger> passengers = Set.of();
}
