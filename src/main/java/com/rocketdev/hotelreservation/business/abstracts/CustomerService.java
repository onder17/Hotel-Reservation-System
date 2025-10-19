package com.rocketdev.hotelreservation.business.abstracts;

import java.util.List;

import com.rocketdev.hotelreservation.business.requests.CreateCustomerRequest;
import com.rocketdev.hotelreservation.business.requests.UpdateCustomerRequest;
import com.rocketdev.hotelreservation.business.responses.GetAllCustomersResponse;
import com.rocketdev.hotelreservation.business.responses.GetCustomerResponse;

public interface CustomerService {
    void add(CreateCustomerRequest createCustomerRequest);

    void update(UpdateCustomerRequest updateCustomerRequest);

    void delete(Long id);

    GetCustomerResponse getById(Long id);

    List<GetAllCustomersResponse> getAll();
}