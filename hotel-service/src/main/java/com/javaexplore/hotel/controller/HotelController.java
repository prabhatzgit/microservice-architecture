package com.javaexplore.hotel.controller;

import com.javaexplore.hotel.entity.Hotel;
import com.javaexplore.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private HotelService hotelService;

    public HotelController(HotelService hotelService){
        this.hotelService = hotelService;
    }
    // create hotel
    @PostMapping("/create")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelService.create(hotel));
    }

    // get all hotel
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelById(hotelId));
    }

    // get hotel by id
    @GetMapping("/allhotels")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAllHotel());
    }
}
