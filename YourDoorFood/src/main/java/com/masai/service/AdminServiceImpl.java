package com.masai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;
import com.masai.model.ToBeDeletedCustomerAccount;
import com.masai.repository.CustomerRepo;
import com.masai.repository.DeletedCustomerAccountRepo;

import io.swagger.v3.oas.annotations.servers.Server;

@Server
public class AdminServiceImpl implements AdminService{

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private DeletedCustomerAccountRepo deletedCustomerAccountRepo;
	
	@Override
	public String deletedAccounts() {
		Integer noOfAccountsDeleted = 0;
		
		List<ToBeDeletedCustomerAccount> deletedCustomerAccounts = deletedCustomerAccountRepo.findAll();
		
		for(ToBeDeletedCustomerAccount e : deletedCustomerAccounts) {
			if(e.getDeletionSheduledAt().isAfter(e.getDeletionSheduledAt().plusHours(24))) {
				Customer customer = customerRepo.findById(e.getCustomerId()).get();
				customerRepo.delete(customer);
				noOfAccountsDeleted++;
			}
		}
		
		return noOfAccountsDeleted + " Accounts deleted successfully";
	}

	@Override
	public List<Customer> showToBeDeletedAccounts() throws CustomerException {
		List<Customer> customers = new ArrayList<>();
		
		List<ToBeDeletedCustomerAccount> deletedCustomerAccounts = deletedCustomerAccountRepo.findAll();
		for(ToBeDeletedCustomerAccount e : deletedCustomerAccounts) {
			if(e.getDeletionSheduledAt().isAfter(e.getDeletionSheduledAt().plusHours(24))) {
				Customer customer = customerRepo.findById(e.getCustomerId()).get();
				customers.add(customer);
			}
		}
		
		if(customers.isEmpty()) throw new CustomerException("No customers found to be deleted");
		
		return customers;
		
	}

}
