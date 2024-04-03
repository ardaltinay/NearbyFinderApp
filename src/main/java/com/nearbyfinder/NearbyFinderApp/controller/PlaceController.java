package com.nearbyfinder.NearbyFinderApp.controller;

import com.nearbyfinder.NearbyFinderApp.dto.PlaceDto;
import com.nearbyfinder.NearbyFinderApp.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyPlaces(@RequestParam double latitude,
                                             @RequestParam double longitude,
                                             @RequestParam int radius) {
        List<PlaceDto> nearbyPlaces = placeService.getNearbyPlaces(latitude, longitude, radius);
        return ResponseEntity.ok(nearbyPlaces);
    }
}
