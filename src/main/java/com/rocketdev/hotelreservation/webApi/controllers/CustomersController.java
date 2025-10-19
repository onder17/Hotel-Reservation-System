package com.rocketdev.hotelreservation.webApi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rocketdev.hotelreservation.business.abstracts.CustomerService;
import com.rocketdev.hotelreservation.business.requests.CreateCustomerRequest;
import com.rocketdev.hotelreservation.business.requests.UpdateCustomerRequest;
import com.rocketdev.hotelreservation.business.responses.GetAllCustomersResponse;
import com.rocketdev.hotelreservation.business.responses.GetCustomerResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomersController {
	private CustomerService customerService;
	
	   @GetMapping()
	    public List<GetAllCustomersResponse> getAll(){
	        //IoC
	        return customerService.getAll();
	        
	        }
	    
	    @GetMapping("/{id}")
	    public GetCustomerResponse getById(@PathVariable Long id){
	        //IoC
	        return customerService.getById(id);
	    }
	    
	    @PostMapping()
	    @ResponseStatus(code=HttpStatus.CREATED)
	    public void add(@RequestBody @Valid() CreateCustomerRequest createCustomerRequest) {
	    	this.customerService.add(createCustomerRequest);
	    }
	    
	    @PutMapping
	    public void update(@RequestBody UpdateCustomerRequest updateCustomerRequest) {
	    	
	    		this.customerService.update(updateCustomerRequest);
	    }
	    
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	    	this.customerService.delete(id);
	    	
	    }
	
}
