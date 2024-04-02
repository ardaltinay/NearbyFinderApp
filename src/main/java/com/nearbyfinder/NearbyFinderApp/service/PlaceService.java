package com.nearbyfinder.NearbyFinderApp.service;

import com.nearbyfinder.NearbyFinderApp.dto.PlaceDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceService {
    

    public List<PlaceDto> getNearbyPlaces(double latitude, double longitude, double radius) {
        return new ArrayList<>();
    }
}
