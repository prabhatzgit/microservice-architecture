package com.pkg.userservice.service.impl;

import com.pkg.userservice.entities.Hotel;
import com.pkg.userservice.entities.Rating;
import com.pkg.userservice.entities.User;
import com.pkg.userservice.exception.ResourceNotFoundException;
import com.pkg.userservice.repository.UserRepository;
//import com.pkg.userservice.service.HotelService;
import com.pkg.userservice.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class UserServicesImpl implements UserService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private UserRepository userRepository;

    private RestTemplate restTemplate;

    // private HotelService hotelService;

    public UserServicesImpl(UserRepository userRepository, RestTemplate restTemplate /*HotelService hotelService*/){
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        //this.hotelService = hotelService;
    }

    @Override
    public User saveUser(User user) {
        String randomUserID = UUID.randomUUID().toString();
        user.setId(randomUserID);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server" + id));
        // case2: fetch rating of above user from rating service
        // http://localhost:8083/ratings/users/693e20f2-41f0-4793-aa25-e13c9c6a4f2b
        // user id is hard coded
        //ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/users/693e20f2-41f0-4793-aa25-e13c9c6a4f2b", ArrayList.class);
        // user id is dynamic
        /*Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getId(), Rating[].class);
        logger.info("{ }" + ratingsOfUser.toString());
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to hotel service
            //http://localhost:8082/hotels/57418ea4-64d2-4ae3-a355-2ab157383f5e
            //ResponseEntity<Hotel> responseEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            //Hotel hotel = responseEntity.getBody();
            //logger.info("{ }" + responseEntity.getStatusCode());
            //set the hotel to rating
//            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            rating.setHotel(hotel);
            // return the rating
            return rating;
        }).collect(Collectors.toList());
        user.setRatingList(ratingList);*/
        return user;
    }
}