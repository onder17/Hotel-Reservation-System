package com.rocketdev.hotelreservation.entities.concretes;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "customer_id" , nullable = false)
	private Customer customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id" , nullable = false)
	private Room room;
	
	@Column(name = "check_in_date", nullable = false)
	private LocalDate checkInDate;
	
	@Column(name = "check_out_date", nullable=false)
	private LocalDate checkOutDate;
	
	@Column(name = "total_price", nullable = false)
	private BigDecimal totalPrice; //the amount of nights * per night price of room
	
	@Enumerated(EnumType.STRING)
	@Column(name= "status" , nullable = false)
	private ReservationStatus status;
	
	
	public enum ReservationStatus{
		CONFIRMED,
		CANCELLED
	}
	
	
	
	
}
