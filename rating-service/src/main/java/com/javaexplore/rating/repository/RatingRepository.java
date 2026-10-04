package com.javaexplore.rating.repository;

import com.javaexplore.rating.entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    // custom methods
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
