package com.rocketdev.hotelreservation.entities.concretes;

import java.math.BigDecimal;

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
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "room_number" , unique = true , nullable = false)
	private String roomNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name= "block" , nullable = false)
	private BlockType block; // A , B , C
	
	
	
	@Enumerated(EnumType.STRING)
	@Column(name= "room_type" , nullable = false)
	private RoomType type; // Standart, Lux, Premium
	
	@Column(name = "price_per_night" , nullable = false )
	private BigDecimal pricePerNight;
	
	@Column(name = "capacity")
	private int capacity = 4; //The capacity for each room is 4-person.
	
	public enum BlockType {
		A, //Garden
		B, //Close the beach
		C  //The closest one
	}

	public enum RoomType {
		STANDARD_LAND_VIEW,
        STANDARD_SEA_VIEW,
        LUXURY_LAND_VIEW,
        LUXURY_SEA_VIEW,
        PREMIUM_SEA_VIEW
	}
	
	
}
