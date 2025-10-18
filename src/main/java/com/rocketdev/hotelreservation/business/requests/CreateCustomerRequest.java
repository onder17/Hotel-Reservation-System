package com.rocketdev.hotelreservation.business.requests;

import com.rocketdev.hotelreservation.entities.concretes.Customer.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

    @NotBlank(message = "İsim alanı boş bırakılamaz!")
    private String name;

    @NotBlank(message = "Soyisim alanı boş bırakılamaz!")
    private String surname;

    @Pattern(regexp = "^[0-9]{10}$", message = "Türkiye kodlu telefon numarası sadece rakamlardan oluşmalı ve 10 haneli olmalıdır!")
    @NotBlank
    private String phoneNum;

    @Email(message = "Lütfen geçerli bir e-posta adresi giriniz!")
    @NotBlank(message = "E-posta adresi boş bırakılamaz!")
    private String emailAddress;

    
    private String billAddress;

    @Size(min = 11, max = 11, message = "Kimlik numarası 11 haneli olmalıdır.")
    @Pattern(regexp = "^[0-9]*$", message = "Kimlik numarası sadece rakamlardan oluşmalıdır.")
    @NotBlank
    private String identityNum;


    @NotNull(message = "Cinsiyet alanı boş bırakılamaz!")
    private Gender gender;
    

    private String customerImageUrl;
}