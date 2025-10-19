package com.rocketdev.hotelreservation.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rocketdev.hotelreservation.business.abstracts.CustomerService;
import com.rocketdev.hotelreservation.business.requests.CreateCustomerRequest;
import com.rocketdev.hotelreservation.business.requests.UpdateCustomerRequest;
import com.rocketdev.hotelreservation.business.responses.GetAllCustomersResponse;
import com.rocketdev.hotelreservation.business.responses.GetCustomerResponse;
import com.rocketdev.hotelreservation.business.rules.CustomerBusinessRules;
import com.rocketdev.hotelreservation.core.utilities.mappers.ModelMapperService;
import com.rocketdev.hotelreservation.dataAccess.abstracts.CustomerRepository;
import com.rocketdev.hotelreservation.entities.concretes.Customer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerManager implements CustomerService {
	private CustomerRepository customerRepository;
	private ModelMapperService modelMapperService;
	private CustomerBusinessRules customerBusinessRules;
	
	
	@Override
	public void add(CreateCustomerRequest createCustomerRequest) {
		
		this.customerBusinessRules
		.checkIfCustomerNameExists(createCustomerRequest.getName() +
				createCustomerRequest.getSurname() + 
				createCustomerRequest.getIdentityNum());
		
		
		Customer customer = this.modelMapperService.forRequest()
				.map(createCustomerRequest, Customer.class);
		this.customerRepository.save(customer);
		
	}

	@Override
	public void update(UpdateCustomerRequest updateCustomerRequest) {
		Customer customer = this.modelMapperService.forRequest()
				.map(updateCustomerRequest, Customer.class);
		this.customerRepository.save(customer);
		
		
	}

	@Override
	public void delete(Long id) {
		
		this.customerRepository.deleteById(id);
	}

	@Override
	public GetCustomerResponse getById(Long id) {
		Customer customer = this.customerRepository.findById(id).orElseThrow();	
		
		GetCustomerResponse response 
		= this.modelMapperService.forResponse()
		.map(customer,GetCustomerResponse.class);
		
		return response;
		
		
	}

	@Override
	public List<GetAllCustomersResponse> getAll() {
		
		List<Customer> customers = customerRepository.findAll();
		
		List<GetAllCustomersResponse> customersResponse = customers.stream()
				.map(customer->this.modelMapperService.forResponse().
						map(customer,GetAllCustomersResponse.class))
				.collect(Collectors.toList());
		
		return customersResponse;
	}
	

}
