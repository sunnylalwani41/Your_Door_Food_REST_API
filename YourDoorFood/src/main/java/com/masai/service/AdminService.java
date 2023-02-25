package com.masai.service;

import java.util.List;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;

public interface AdminService {

	public String deletedAccounts();
	
	public List<Customer> showToBeDeletedAccounts() throws CustomerException;
}
