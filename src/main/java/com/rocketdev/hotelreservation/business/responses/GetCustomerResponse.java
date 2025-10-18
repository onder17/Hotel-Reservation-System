package com.rocketdev.hotelreservation.business.responses;

import com.rocketdev.hotelreservation.entities.concretes.Customer.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerResponse {
    
    private Long id;
    
    private String name;

    private String surname;
    
    private String phoneNum;
    
    private String emailAddress;
    
    private String billAddress;
    
    private Gender gender;
    
    private String customerImageUrl;

    private String identityNum;
}