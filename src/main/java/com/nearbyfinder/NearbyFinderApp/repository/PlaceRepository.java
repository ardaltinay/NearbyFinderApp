package com.nearbyfinder.NearbyFinderApp.repository;

import com.nearbyfinder.NearbyFinderApp.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByLatitudeAndLongitudeAndRadius(double latitude, double longitude, int radius);
}
