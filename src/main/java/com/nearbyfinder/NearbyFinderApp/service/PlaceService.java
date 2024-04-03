package com.nearbyfinder.NearbyFinderApp.service;

import com.nearbyfinder.NearbyFinderApp.dto.PlaceDto;
import com.nearbyfinder.NearbyFinderApp.entity.Place;
import com.nearbyfinder.NearbyFinderApp.mapper.PlaceMapper;
import com.nearbyfinder.NearbyFinderApp.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    @Value("google.maps.api.key")
    private String GOOGLE_MAPS_API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    private final PlaceRepository placeRepository;

    public List<PlaceDto> getNearbyPlaces(double latitude, double longitude, int radius) {
        return new ArrayList<>();
    }

    public List<PlaceDto> findByLatitudeAndLongitudeAndRadius(double latitude, double longitude, int radius) {
        List<Place> places = placeRepository.findByLatitudeAndLongitudeAndRadius(latitude, longitude, radius);
        return PlaceMapper.entityListToDtoList(places);
    }
}
