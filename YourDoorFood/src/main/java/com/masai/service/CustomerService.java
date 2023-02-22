package com.masai.service;

import java.util.List;

import com.masai.model.Customer;

public interface CustomerService {
	
    public Customer addCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer);
	
	public Customer removeCustomer(Customer customer);
	
	public Customer viewCustomer(Customer customer);
	
	public List<Customer> viewAllCustomers();
}
