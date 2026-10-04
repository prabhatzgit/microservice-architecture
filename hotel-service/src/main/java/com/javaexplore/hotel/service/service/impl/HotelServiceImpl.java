package com.javaexplore.hotel.service.service.impl;

import com.javaexplore.hotel.entity.Hotel;
import com.javaexplore.hotel.repository.HotelRepository;
import com.javaexplore.hotel.service.HotelService;
import com.javaexplore.hotel.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository){
        this.hotelRepository = hotelRepository;
    }
    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setHotelId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotelById(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel with given id not found " + id) );
    }
}
