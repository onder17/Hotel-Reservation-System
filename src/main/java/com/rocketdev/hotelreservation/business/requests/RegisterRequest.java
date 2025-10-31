package com.rocketdev.hotelreservation.business.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email
    private String email;

    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    private String fname;

    @NotBlank
    private String lname;
}
