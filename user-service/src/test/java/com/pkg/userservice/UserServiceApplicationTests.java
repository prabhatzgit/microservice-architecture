package com.pkg.userservice;

import com.pkg.userservice.entities.Rating;
import com.pkg.userservice.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private RatingService ratingService;

	@Test
	void contextLoads() {
	}

	@Test
	void createRating(){
		Rating rating = Rating
				.builder()
				.rating(10)
				.userId("")
				.hotelId("")
				.feedback("This is created using feign client")
				.build();

		ResponseEntity<Rating> savedRating = ratingService.createRating(rating);
		savedRating.getBody();
		savedRating.getStatusCode();
		savedRating.getHeaders();
		System.out.println("New rating created");
	}
}
