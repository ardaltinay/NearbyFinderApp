package com.nearbyfinder.NearbyFinderApp.mapper;

import com.nearbyfinder.NearbyFinderApp.dto.PlaceDto;
import com.nearbyfinder.NearbyFinderApp.entity.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceMapper {

    public static PlaceDto toDto(Place place) {
        return PlaceDto.builder()
                .name(place.getName())
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
                    .latitude(place.getLatitude())
                    .longitude(place.getLongitude())
                    .radius(place.getRadius())
                    .build();
            placeDtoList.add(placeDto);
        });
        return placeDtoList;
    }
}
