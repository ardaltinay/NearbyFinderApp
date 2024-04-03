package com.nearbyfinder.NearbyFinderApp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PlaceDto {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private int radius;
}
