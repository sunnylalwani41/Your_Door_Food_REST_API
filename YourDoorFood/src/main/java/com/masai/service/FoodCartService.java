package com.masai.service;

import com.masai.model.FoodCart;
import com.masai.model.Item;
import com.masai.exception.FoodCartException;

public interface FoodCartService {
	public FoodCart addItemToCart(FoodCart cart, Item item) throws FoodCartException;
	
	public FoodCart increaseQuantity(FoodCart cart, Item item, int quantity) throws FoodCartException;
	
	public FoodCart reduceQuantity(FoodCart cart, Item item, int quantity) throws FoodCartException;
	
	public FoodCart removeItem(FoodCart cart, Item item) throws FoodCartException;
	
	public FoodCart clearCart(FoodCart cart) throws FoodCartException;
}
