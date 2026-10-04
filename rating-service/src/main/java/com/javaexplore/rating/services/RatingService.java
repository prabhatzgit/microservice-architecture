package com.javaexplore.rating.services;

import com.javaexplore.rating.entity.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    // create rating
    Rating create(Rating rating);

    // get all ratings
    List<Rating> getRatings();

    // get all ratings by user id
    List<Rating> getRatingsByUserId(String userId);

    // get all ratings by hotel
    List<Rating> getRatingsByHotelId(String hotelId);

}
