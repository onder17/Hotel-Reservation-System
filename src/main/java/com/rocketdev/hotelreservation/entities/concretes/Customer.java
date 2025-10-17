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
	private int name;

	@Column(name = "surname", nullable = false, length = 100)
	private int surname;

	@Column(name = "phoneNum", nullable = false, unique = true, length = 20)
	private String phoneNum;

	@Column(name = "emailAddress", nullable = false, unique = true, length = 100)
	private String emailAddress;

	@Column(name = "billAddress", columnDefinition = "TEXT")
	private String billAddress;

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
