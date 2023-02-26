package com.masai.service;

import java.util.Map;

import com.masai.exception.CustomerException;
import com.masai.exception.FoodCartException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.RestaurantException;
import com.masai.model.FoodCart;
import com.masai.model.Item;

public interface FoodCartService {
	public FoodCart addItemToCart(String key, String itemName, Integer restaurantId) throws FoodCartException, LoginException, ItemException, RestaurantException, CustomerException;
	
	public FoodCart increaseQuantity(String key, String itemName, 	int quantity) throws FoodCartException, LoginException, ItemException, CustomerException;
	
	public FoodCart reduceQuantity(String key, String itemName, int quantity) throws FoodCartException, LoginException, ItemException, CustomerException;
	
	public FoodCart removeItem(String key, String itemName) throws FoodCartException, CustomerException, LoginException;
	
	public FoodCart clearCart(String key) throws FoodCartException, CustomerException, LoginException;
	
	public Map<Item, Integer> viewCart(String key) throws LoginException, CustomerException, FoodCartException; 
}
