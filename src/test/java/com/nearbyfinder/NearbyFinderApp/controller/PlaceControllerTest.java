package com.nearbyfinder.NearbyFinderApp.controller;

import com.nearbyfinder.NearbyFinderApp.dto.PlaceDto;
import com.nearbyfinder.NearbyFinderApp.service.PlaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PlaceControllerTest {

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private PlaceController placeController;

    @Test
    void getNearbyPlaces() throws Exception {
        // given
        double latitude = 40.7128;
        double longitude = -74.0060;
        int radius = 1500;
        List<PlaceDto> placeDtoList = Arrays.asList(
                new PlaceDto("place1", "address1", latitude, longitude, radius),
                new PlaceDto("place2", "address2", latitude, longitude, radius));

        // when
        ResponseEntity<?> nearbyPlaces = placeController.getNearbyPlaces(latitude, longitude, radius);

        // then
        assertEquals(HttpStatusCode.valueOf(200), nearbyPlaces.getStatusCode());

    }
}