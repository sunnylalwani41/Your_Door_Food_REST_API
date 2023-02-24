package com.masai.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CustomerAddresssException;
import com.masai.model.Address;
import com.masai.model.Customer;
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
			throw new CustomerAddresssException("This customer does not exist");
		}
		customerRepo.save(customer);
		return customerOptional.get();

	}

	@Override
	public Customer removeCustomer(Integer customerid) {
		Optional<Customer> customerOptional = customerRepo.findById(customerid);
		if(customerOptional.isEmpty()) {
			throw new CustomerAddresssException("This customer does not exist");
		}
		customerRepo.delete(customerOptional.get());
		return customerOptional.get();
	}

	@Override
	public Customer viewCustomer(Integer customerid) {
		Optional<Customer> customer2 = customerRepo.findById(customerid);
		if(customer2.isEmpty()) {
			throw new CustomerAddresssException("This customer does not exist");
		}
		return customer2.get();
	
	}

	@Override
	public String updateAddress(String mobileNo,Address address) throws CustomerAddresssException {
		
		Customer customer = customerRepo.findbyMobileNumber(mobileNo);
		if(customer==null) {
			throw new CustomerAddresssException("Customer does not exist");
		}
		customer.setAddress(address);
		customerRepo.save(customer);
		return "Address update sucssesfully";
	}

	@Override
	public String updatepasword(String mobileNo, String password) throws CustomerAddresssException {
		Customer customer = customerRepo.findbyMobileNumber(mobileNo);
		if(customer==null) {
			throw new CustomerAddresssException("Customer does not exist");
		}
		customer.setPassword(password);
		customerRepo.save(customer);
		return "Password update sucssesfully";
	}


}
