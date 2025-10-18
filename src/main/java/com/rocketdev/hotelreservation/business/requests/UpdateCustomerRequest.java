package com.rocketdev.hotelreservation.business.requests;

import com.rocketdev.hotelreservation.entities.concretes.Customer.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequest {
	
	@NotNull(message = "Güncellenecek müşteri ID'si boş olamaz!")
    private Long id;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Türkiye kodlu telefon numarası sadece rakamlardan oluşmalı ve 10 haneli olmalıdır!")
    @NotBlank
    private String phoneNum;

    @Email(message = "Lütfen geçerli bir e-posta adresi giriniz!")
    @NotBlank(message = "E-posta adresi boş bırakılamaz!")
    private String emailAddress;

    
    private String billAddress;
    
    @NotNull(message = "Cinsiyet alanı boş bırakılamaz!")
    private Gender gender;
    

    private String customerImageUrl;

}
