package com.masai.service;

import com.masai.model.FoodCart;
import com.masai.model.Item;
import com.masai.exception.CustomerException;
import com.masai.exception.FoodCartException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.RestaurantException;

public interface FoodCartService {
	public FoodCart addItemToCart(String mobileNo, String itemName, Integer restaurantId) throws FoodCartException, LoginException, ItemException, RestaurantException, CustomerException;
	
	public FoodCart increaseQuantity(String mobileNo, String itemName, 	int quantity) throws FoodCartException, LoginException, ItemException, CustomerException;
	
	public FoodCart reduceQuantity(String mobileNo, String itemName, int quantity) throws FoodCartException, LoginException, ItemException, CustomerException;
	
	public FoodCart removeItem(String mobileNo, String itemName) throws FoodCartException, CustomerException, LoginException;
	
	public FoodCart clearCart(String mobileNo, String itemName) throws FoodCartException, CustomerException, LoginException;
}
