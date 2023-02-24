package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;
import com.masai.model.Restaurant;
import com.masai.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public Customer addCustomer(Customer customer) {	
		return customerRepo.save(customer);
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		Optional<Customer> customerOptional = customerRepo.findById(customer.getCustomerID());
		if(customerOptional.isEmpty()){ 
			throw new CustomerException("This customer dose not exist");
		}
		return customerOptional.get();

	}

	@Override
	public Customer removeCustomer(Customer customer) {
		Optional<Customer> customerOptional = customerRepo.findById(customer.getCustomerID());
		if(customerOptional.isEmpty()) {
			throw new CustomerException("This customer dose not exist");
		}
		customerRepo.delete(customerOptional.get());
		return customerOptional.get();
	}

	@Override
	public Customer viewCustomer(Customer customer) {
		Optional<Customer> customer2 = customerRepo.findById(customer.getCustomerID());
		if(customer2.isEmpty()) {
			throw new CustomerException("This customer dose not exist");
		}
		return customer2.get();
	
	}

	@Override
	public List<Customer> viewAllCustomers(Restaurant restaurant) {		
		return null;
	}

}
