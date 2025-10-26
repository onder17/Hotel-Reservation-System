package com.rocketdev.hotelreservation.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rocketdev.hotelreservation.entities.concretes.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	//Special methods - controls
	

}
