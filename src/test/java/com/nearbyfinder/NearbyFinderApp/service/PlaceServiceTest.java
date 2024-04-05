package com.nearbyfinder.NearbyFinderApp.service;

import com.nearbyfinder.NearbyFinderApp.dto.PlaceDto;
import com.nearbyfinder.NearbyFinderApp.entity.Place;
import com.nearbyfinder.NearbyFinderApp.exception.PlaceNotFoundException;
import com.nearbyfinder.NearbyFinderApp.mapper.PlaceMapper;
import com.nearbyfinder.NearbyFinderApp.model.response.GeometryResponse;
import com.nearbyfinder.NearbyFinderApp.model.response.LocationResponse;
import com.nearbyfinder.NearbyFinderApp.model.response.PlaceResponse;
import com.nearbyfinder.NearbyFinderApp.model.response.PlacesNearbySearchResponse;
import com.nearbyfinder.NearbyFinderApp.repository.PlaceRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class PlaceServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceService placeService;

    private static MockedStatic<PlaceMapper> mockedStatic;

    @BeforeEach
    public void setUp() {
        mockedStatic = mockStatic(PlaceMapper.class);
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void close() {
        mockedStatic.close();
    }

    @Test
    public void getNearbyPlaces_NoCachedData_ReturnsNewPlaces() {
        // given
        double latitude = 37.7749;
        double longitude = -122.4194;
        int radius = 1000;
        double searchParam = latitude + longitude + radius;
        PlacesNearbySearchResponse response = new PlacesNearbySearchResponse();
        LocationResponse location = new LocationResponse(latitude, longitude);
        GeometryResponse geometry = new GeometryResponse(location);
        PlaceResponse placeResponse = new PlaceResponse("test", "address", geometry);
        response.setPlaces(List.of(placeResponse));

        Place place = new Place(null, "test", "address", latitude, longitude, radius, searchParam);
        Place place2 = new Place(null, "test", "address", latitude, longitude, 0, 0.0);
        PlaceDto placeDto = new PlaceDto("test", "address", latitude, longitude, radius);

        when(placeRepository.findBySearchParam(searchParam)).thenReturn(Collections.emptyList());
        when(restTemplate.getForObject(any(), eq(PlacesNearbySearchResponse.class),
                eq(latitude), eq(longitude), eq(radius), any())).thenReturn(response);
        when(PlaceMapper.placeResponseListToEntityList(List.of(placeResponse))).thenReturn(List.of(place2));
        when(placeRepository.saveAll(any())).thenReturn(List.of(place));
        when(PlaceMapper.entityListToDtoList(List.of(place))).thenReturn(List.of(placeDto));

        // when
        List<PlaceDto> actualPlaces = placeService.getNearbyPlaces(latitude, longitude, radius);

        // then
        assertEquals(List.of(placeDto), actualPlaces);
    }

    @Test
    public void getNearbyPlaces_CachedData_ReturnsCachedPlaces() {
        // given
        double latitude = 37.7749;
        double longitude = -122.4194;
        int radius = 1000;
        double searchParam = latitude + longitude + radius;
        List<PlaceDto> expectedPlaces = List.of(new PlaceDto( "test", "address",
                latitude, longitude, radius));
        List<Place> cachedPlaces = List.of(new Place(0L, "test", "address",
                latitude, longitude, radius, 0.0));
        when(placeRepository.findBySearchParam(searchParam)).thenReturn(cachedPlaces);
        when(PlaceMapper.entityListToDtoList(cachedPlaces)).thenReturn(expectedPlaces);

        // when
        List<PlaceDto> actualPlaces = placeService.getNearbyPlaces(latitude, longitude, radius);

        // then
        assertEquals(expectedPlaces, actualPlaces);
    }

    @Test
    public void getNearbyPlaces_NoPlacesFound_ThrowsException() {
        // given
        double latitude = 37.7749;
        double longitude = -122.4194;
        int radius = 1000;
        when(restTemplate.getForObject(anyString(), eq(PlacesNearbySearchResponse.class),
                eq(latitude), eq(longitude), eq(radius), anyString())).thenReturn(null);

        // then
        assertThrows(PlaceNotFoundException.class, () -> {
            placeService.getNearbyPlaces(latitude, longitude, radius);
        });
    }
}