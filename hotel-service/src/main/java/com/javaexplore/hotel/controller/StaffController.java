package com.javaexplore.hotel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/staffs")
public class StaffController {

    @GetMapping("/getstaffs")
    public ResponseEntity<List<String>> getStaffs(){
       List<String> staffList =  Arrays.asList("Samir","Ashish","Adhikari","Mahati","Jeetu");
       return new ResponseEntity<>(staffList, HttpStatus.OK);
    }
}
