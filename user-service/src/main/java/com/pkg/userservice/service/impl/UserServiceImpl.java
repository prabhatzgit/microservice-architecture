package com.pkg.userservice.service.impl;


import com.pkg.userservice.entities.Hotel;
import com.pkg.userservice.entities.Rating;
import com.pkg.userservice.entities.User;
import com.pkg.userservice.exception.ResourceNotFoundException;
import com.pkg.userservice.external.services.HotelService;
import com.pkg.userservice.repository.UserRepository;
import com.pkg.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RestTemplate restTemplate;

    private HotelService hotelService;

    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate, HotelService hotelService){
            this.userRepository = userRepository;
            this.restTemplate = restTemplate;
            this.hotelService = hotelService;
    }

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate  unique userid
        String randomUserId = UUID.randomUUID().toString();
        user.setId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        //implement RATING SERVICE CALL: USING REST TEMPLATE
        return userRepository.findAll();
    }

    //get single user
    @Override
    public User getUser(String userId) {
        //get user from database with the help  of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));
        // fetch rating of the above  user from RATING SERVICE
        //http://localhost:8083/ratings/users/47e38dac-c7d0-4c40-8582-11d15f185fad

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getId(), Rating[].class);
        logger.info("{} ", ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
        //api call to hotel service to get the hotel
        //http://localhost:8082/hotels/1cbaf36d-0b28-4173-b5ea-f1cb0bc0a791
        ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
        //Hotel hotel = forEntity.getBody();
        // Using feign client approach
        Hotel hotel = hotelService.getHotel(rating.getHotelId());
        logger.info("response status code: {} ",forEntity.getStatusCode());
        //set the hotel to rating
        rating.setHotel(hotel);
        //return the rating
        return rating;
        }).collect(Collectors.toList());
        user.setRatingList(ratingList);
        return user;
    }

    /*To remove hardcode localhost and port and make flexible communication between microservice, use the microservice name registered
    * in discovery registry
    * //http://localhost:8082/hotels/1cbaf36d-0b28-4173-b5ea-f1cb0bc0a791 --> http://HOTEL-SERVICE/hotels/
    * */
}