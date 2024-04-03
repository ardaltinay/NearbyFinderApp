package com.nearbyfinder.NearbyFinderApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "places")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int radius;
}
