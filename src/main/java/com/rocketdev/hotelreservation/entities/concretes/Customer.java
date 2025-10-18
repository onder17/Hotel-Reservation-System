package com.rocketdev.hotelreservation.entities.concretes;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	@NotBlank(message ="İsim alanı boş bırakılamaz!")
	private String name;

	@Column(name = "surname", nullable = false, length = 100)
	@NotBlank(message = "Soyisim alanı boş bırakılamaz!")
	private String surname;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Türkiye kodlu telefon numarası sadece rakamlardan oluşmalı ve 10 haneli olmalıdır!")
	@Column(name = "phoneNum", nullable = false, unique = true, length = 20)
	private String phoneNum;
	
	
	@Email(message = "Lütfen geçerli bir e-posta adresi giriniz!")
	@NotBlank(message = "E-posta adresi boş bırakılamaz!")
	@Column(name = "emailAddress", nullable = false, unique = true, length = 100)
	private String emailAddress;
	
	
	@Column(name = "billAddress", columnDefinition = "TEXT")
	private String billAddress;
	
	
	@Size(min = 11, max = 11, message = "Kimlik numarası 11 haneli olmalıdır.")
	@Pattern(regexp = "^[0-9]*$", message = "Kimlik numarası sadece rakamlardan oluşmalıdır.")
	@Column(name = "identityNum", nullable = false, unique = true, length = 11)
	private String identityNum;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;

	@Column(name = "imageUrl")
	private String customerImageUrl;

	// enum definition for Gender
	public enum Gender {
		MALE,
		FEMALE,
		NOT_SPECIFIED
	}
}
