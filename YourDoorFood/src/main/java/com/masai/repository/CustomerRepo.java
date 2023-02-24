package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.exception.CustomerException;
import com.masai.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{

<<<<<<< HEAD
	public Customer findByMobileNumber(String mobileNo);
=======
	public Customer findByMobileNumber(String mobileNumer) throws CustomerException;
>>>>>>> branch 'main' of https://github.com/sunnylalwani41/tasty-hour-5423.git

}
