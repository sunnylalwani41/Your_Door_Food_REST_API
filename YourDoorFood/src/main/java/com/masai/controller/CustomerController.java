package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.CustomerAddresssException;
import com.masai.model.Customer;
import com.masai.service.CustomerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/addcustomer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer)throws CustomerAddresssException{
		ResponseEntity<Customer> customerResponseEntity = new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
		return customerResponseEntity;
	}
	
	@PutMapping("/updatecustomer")
	public ResponseEntity<Customer> updateCustomerDetails(@RequestBody Customer customer)throws CustomerAddresssException{
		ResponseEntity<Customer> customerResponseEntity = new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.ACCEPTED);
		return customerResponseEntity;
	}
	
	@DeleteMapping("/updatecustomer")
	public ResponseEntity<Customer> deleteCustomerByid(@RequestBody Customer customer)throws CustomerAddresssException{
		ResponseEntity<Customer> customerResponseEntity = new ResponseEntity<>(customerService.removeCustomer(customer), HttpStatus.ACCEPTED);
		return customerResponseEntity;
	}
	
	@DeleteMapping("/findcustomer")
	public ResponseEntity<Customer> findCustomer(@RequestBody Customer customer)throws CustomerAddresssException{
		ResponseEntity<Customer> customerResponseEntity = new ResponseEntity<>(customerService.viewCustomer(customer), HttpStatus.FOUND);
		return customerResponseEntity;
	}
}
