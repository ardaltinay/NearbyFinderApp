package com.nearbyfinder.NearbyFinderApp.service;

import com.nearbyfinder.NearbyFinderApp.dto.PlaceDto;
import com.nearbyfinder.NearbyFinderApp.entity.Place;
import com.nearbyfinder.NearbyFinderApp.exception.PlaceNotFoundException;
import com.nearbyfinder.NearbyFinderApp.mapper.PlaceMapper;
import com.nearbyfinder.NearbyFinderApp.model.response.PlaceResponse;
import com.nearbyfinder.NearbyFinderApp.model.response.PlacesNearbySearchResponse;
import com.nearbyfinder.NearbyFinderApp.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    @Value("${google.maps.api.key}")
    private String GOOGLE_MAPS_API_KEY;

    @Value("${google.maps.api.url}")
    private String URL;

    private final RestTemplate restTemplate;

    private final PlaceRepository placeRepository;

    public List<PlaceDto> getNearbyPlaces(double latitude, double longitude, int radius) {
        double searchParam = latitude + longitude + radius;
        List<PlaceDto> placeDtoList = findBySearchParam(searchParam);
        if (placeDtoList.isEmpty()) {
            List<Place> places = getPlaces(latitude, longitude, radius);
            places.forEach(place -> {
                place.setRadius(radius);
                place.setSearchParam(searchParam);
            });
            List<Place> savedPlaces = placeRepository.saveAll(places);
            return PlaceMapper.entityListToDtoList(savedPlaces);
        }
        return placeDtoList;
    }

    @Cacheable(value = "places", key = "#searchParam")
    private List<PlaceDto> findBySearchParam(double searchParam) {
        List<Place> places = placeRepository.findBySearchParam(searchParam);
        return PlaceMapper.entityListToDtoList(places);
    }

    private List<PlaceResponse> getPlaceResponsesDataFromGoogleApi(double latitude, double longitude, int radius) {
        PlacesNearbySearchResponse object = restTemplate.getForObject(URL, PlacesNearbySearchResponse.class,
                latitude, longitude, radius, GOOGLE_MAPS_API_KEY);
        if (object != null && !object.getPlaces().isEmpty()) {
            return object.getPlaces();
        }
        return new ArrayList<>();
    }

    private List<Place> getPlaces(double latitude, double longitude, int radius) {
        List<PlaceResponse> responsesDataFromGoogleApi = getPlaceResponsesDataFromGoogleApi(latitude, longitude, radius);
        if (responsesDataFromGoogleApi.isEmpty()) {
            throw new PlaceNotFoundException("We can not find any places with given information! Please try with different information.");
        }
        return PlaceMapper.placeResponseListToEntityList(responsesDataFromGoogleApi);
    }

}
