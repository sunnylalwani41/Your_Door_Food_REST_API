package com.masai.service;

import com.masai.exception.LoginException;
import com.masai.model.CurrentUserSession;
import com.masai.model.LoginDTO;

public interface RestaurantLoginService {

	public CurrentUserSession login(LoginDTO dto) throws LoginException;
	
	public String logout(String key) throws LoginException;
	
}
