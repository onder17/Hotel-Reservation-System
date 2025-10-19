package com.rocketdev.hotelreservation.business.rules;

import org.springframework.stereotype.Service;

import com.rocketdev.hotelreservation.core.utilities.exceptions.BusinessException;
import com.rocketdev.hotelreservation.dataAccess.abstracts.CustomerRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerBusinessRules {
	private CustomerRepository customerRepository;
	
	public void checkIfCustomerNameExists(String identityNum) {
		if(this.customerRepository.existsById(identityNum)) {
			throw new BusinessException("Customer already exists!");
		}
		
	}
	

}
