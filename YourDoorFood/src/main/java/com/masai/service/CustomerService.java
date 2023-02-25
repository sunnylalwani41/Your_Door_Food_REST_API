package com.masai.service;


import com.masai.exception.CustomerException;
import com.masai.model.Address;
import com.masai.model.Customer;

public interface CustomerService {
	
    public Customer addCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer) throws CustomerException;
	
    public Customer removeCustomer(Integer customerid)throws CustomerException;
	
	public Customer viewCustomer(Integer customerid)throws CustomerException;
	
	public String updateAddress(String mobileNo,Address address) throws CustomerException;
	
	public String updatepasword(String mobileNo,String currentPassword,String password) throws CustomerException;
}
