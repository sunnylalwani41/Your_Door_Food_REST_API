package com.masai.service;

import java.util.List;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;
import com.masai.model.Restaurant;

public interface CustomerService {
	
    public Customer addCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer) throws CustomerException;
	
	public Customer removeCustomer(Customer customer) throws CustomerException;
	
	public Customer viewCustomer(Customer customer) throws CustomerException;
	
	public List<Customer> viewAllCustomers(Restaurant restaurant);
}
