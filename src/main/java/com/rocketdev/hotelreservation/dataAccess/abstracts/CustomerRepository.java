package com.rocketdev.hotelreservation.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rocketdev.hotelreservation.entities.concretes.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	boolean existsById(String identityNum); //Spring jpa keywords
}
