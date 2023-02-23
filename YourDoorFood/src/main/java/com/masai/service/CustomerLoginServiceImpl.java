package com.masai.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.LoginException;
import com.masai.model.CurrentUserSession;
import com.masai.model.Customer;
import com.masai.model.LoginDTO;
import com.masai.model.Restaurant;
import com.masai.repository.RestaurantRepo;
import com.masai.repository.SessionRepo;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerLoginServiceImpl implements CustomerLoginService{

	@Autowired
	private SessionRepo sessionRepo;
	
	@Autowired
	private RestaurantRepo restaurantRepo;
	
	@Override
	public CurrentUserSession login(LoginDTO dto) throws LoginException {
		Customer customer = restaurantRepo.findByMobileNo();
		
		if(customer==null) throw new LoginException("Please enter a valid mobile number!");
		
		Optional<CurrentUserSession> currentUserSession = sessionRepo.findById(customer.getCustomerID());
		
		if(currentUserSession.isPresent()) throw new LoginException("User already logged in with this mobile number!");
		
		if(!customer.getPassword().equals(dto.getPassword())) throw new LoginException("Incorrect password!");

		String key = RandomString.make(6);
		
		CurrentUserSession genrateSession = new CurrentUserSession(customer.getCustomerID(), key, LocalDateTime.now());
		
		sessionRepo.save(genrateSession);
		
		return genrateSession;
	}

	@Override
	public String logout(String key) throws LoginException {
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		
		if(currentUserSession==null) throw new LoginException("Invalid User key!");
		
		sessionRepo.delete(currentUserSession);
		
		return "Logged out successfully...";
	}

}
