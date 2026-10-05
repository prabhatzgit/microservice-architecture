package com.javaexplore.hotel.service;

import com.javaexplore.hotel.entity.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface HotelService {
    //create
    Hotel create(Hotel hotel);
    // get all hotel
    List<Hotel> getAllHotel();
    // get single hotel
    Hotel getHotelById(String id);
}
