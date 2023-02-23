package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.FoodCartException;
import com.masai.model.FoodCart;
import com.masai.model.Item;
import com.masai.repository.FoodCartRepo;

@Service
public class FoodCartSerivceImpl implements FoodCartService{
	@Autowired
	private FoodCartRepo cartRepo;
	
	@Override
	public FoodCart addItemToCart(FoodCart cart, Item item) throws FoodCartException {
		
		return null;
	}

	@Override
	public FoodCart increaseQuantity(FoodCart cart, Item item, int quantity) throws FoodCartException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodCart reduceQuantity(FoodCart cart, Item item, int quantity) throws FoodCartException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodCart removeItem(FoodCart cart, Item item) throws FoodCartException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoodCart clearCart(FoodCart cart) throws FoodCartException {
		// TODO Auto-generated method stub
		return null;
	}

}
