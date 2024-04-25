package com.example.flightmanagement.flight.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;

    String email;

    String phoneNumber;

    @ManyToOne
    Flight flight;
}
