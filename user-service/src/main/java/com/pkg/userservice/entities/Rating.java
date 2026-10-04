package com.pkg.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String remark;
    private String feedback;

    private Hotel hotel;
}