package com.masai.service;

import com.masai.exception.CustomerAddresssException;
import com.masai.model.Address;
import com.masai.model.Customer;

public interface CustomerService {
	
    public Customer addCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer)throws CustomerAddresssException;
	
	public Customer removeCustomer(Integer customerid)throws CustomerAddresssException;
	
	public Customer viewCustomer(Integer customerid)throws CustomerAddresssException;
	
	public String updateAddress(String mobileNo,Address address) throws CustomerAddresssException;
	
	public String updatepasword(String mobileNo,String password) throws CustomerAddresssException;
}
