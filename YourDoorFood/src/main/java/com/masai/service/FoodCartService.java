package com.masai.service;

import com.masai.model.FoodCart;
import com.masai.model.Item;
import com.masai.exception.CustomerException;
import com.masai.exception.FoodCartException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.RestaurantException;

public interface FoodCartService {
	public FoodCart addItemToCart(String mobileNo, String itemName, Integer restaurantId) throws FoodCartException, LoginException, ItemException, RestaurantException;
	
	public FoodCart increaseQuantity(String mobileNo, String itemName) throws FoodCartException, LoginException, ItemException, CustomerException;
	
	public FoodCart reduceQuantity(FoodCart cart, Item item, int quantity) throws FoodCartException;
	
	public FoodCart removeItem(FoodCart cart, Item item) throws FoodCartException;
	
	public FoodCart clearCart(FoodCart cart) throws FoodCartException;
}
