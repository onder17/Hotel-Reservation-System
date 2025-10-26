package com.rocketdev.hotelreservation.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rocketdev.hotelreservation.entities.concretes.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
	//Special methods
	

}
