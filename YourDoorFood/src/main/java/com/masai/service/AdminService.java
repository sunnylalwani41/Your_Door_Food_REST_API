package com.masai.service;

import java.util.List;

import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.model.Customer;
import com.masai.model.LoginDTO;

public interface AdminService {

	public String deleteAccounts(LoginDTO loginDTO) throws LoginException;
	
	public List<Customer> showToBeDeletedAccounts(LoginDTO loginDTO) throws CustomerException, LoginException;
}
