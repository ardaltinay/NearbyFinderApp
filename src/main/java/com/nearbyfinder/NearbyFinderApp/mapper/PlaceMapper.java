package com.nearbyfinder.NearbyFinderApp.mapper;

import com.nearbyfinder.NearbyFinderApp.dto.PlaceDto;
import com.nearbyfinder.NearbyFinderApp.entity.Place;
import com.nearbyfinder.NearbyFinderApp.model.response.PlaceResponse;

import java.util.ArrayList;
import java.util.List;

public class PlaceMapper {

    public static PlaceDto toDto(Place place) {
        return PlaceDto.builder()
                .name(place.getName())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .radius(place.getRadius())
                .build();
    }

    public static List<PlaceDto> entityListToDtoList(List<Place> places) {
        List<PlaceDto> placeDtoList = new ArrayList<>();
        places.forEach(place -> {
            PlaceDto placeDto = PlaceDto.builder()
                    .name(place.getName())
                    .address(place.getAddress())
                    .latitude(place.getLatitude())
                    .longitude(place.getLongitude())
                    .radius(place.getRadius())
                    .build();
            placeDtoList.add(placeDto);
        });
        return placeDtoList;
    }

    public static List<Place> placeResponseListToEntityList(List<PlaceResponse> responses) {
        List<Place> places = new ArrayList<>();
        responses.forEach(placeResponse -> {
            Place place = Place.builder()
                    .name(placeResponse.getName())
                    .address(placeResponse.getAddress())
                    .latitude(placeResponse.getGeometry().getLocation().getLat())
                    .longitude(placeResponse.getGeometry().getLocation().getLng())
                    .build();
            places.add(place);
        });
        return places;
    }
}
