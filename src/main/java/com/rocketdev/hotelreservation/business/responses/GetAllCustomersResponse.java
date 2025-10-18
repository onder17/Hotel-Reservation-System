package com.rocketdev.hotelreservation.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCustomersResponse {
    
    private Long id;
    private String name;
    private String surname;
    private String emailAddress;
}
