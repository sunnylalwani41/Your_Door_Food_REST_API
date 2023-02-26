package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.CustomerException;
import com.masai.exception.FoodCartException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.RestaurantException;
import com.masai.model.FoodCart;
import com.masai.service.FoodCartService;


@RestController
@RequestMapping("/YourDoorFood")
public class FoodCartController {
	
	@Autowired
	private FoodCartService foodCartService;
	
	@GetMapping("/foodcart/{loginKey}/{itemName}/{restaurantId}")
	public ResponseEntity<FoodCart> addItemToCartHandler(@PathVariable("loginKey") String key,  @PathVariable("itemName") String itemName, @PathVariable("restaurantId") Integer restaurantId) throws FoodCartException, LoginException, ItemException, RestaurantException, CustomerException{
		FoodCart cart= foodCartService.addItemToCart(key, itemName, restaurantId);
		return new ResponseEntity<FoodCart>(cart, HttpStatus.OK);
	}
	
	@GetMapping("/foodcartIncreaseQuantity/{loginKey}/{itemName}/{quantity}")
	public ResponseEntity<FoodCart> increaseQuantityInCartHandler(@PathVariable("loginKey") String key,  @PathVariable("itemName") String itemName, @PathVariable("quantity") Integer quantity) throws FoodCartException, LoginException, ItemException, CustomerException{
		FoodCart cart= foodCartService.increaseQuantity(key, itemName, quantity);
		return new ResponseEntity<FoodCart>(cart, HttpStatus.OK);
	}
	
	@GetMapping("/foodcartReduceQuantity/{loginKey}/{itemName}/{quantity}")
	public ResponseEntity<FoodCart> reduceQuantityInCartHandler(@PathVariable("loginKey") String key,  @PathVariable("itemName") String itemName, @PathVariable("quantity") Integer quantity) throws FoodCartException, LoginException, ItemException, CustomerException{
		FoodCart cart= foodCartService.reduceQuantity(key, itemName, quantity);
		return new ResponseEntity<FoodCart>(cart, HttpStatus.OK);
	}
	
	@DeleteMapping("/foodcart/{loginKey}/{itemName}")
	public ResponseEntity<FoodCart> removeItemInCartHandler(@PathVariable("loginKey") String key,  @PathVariable("itemName") String itemName) throws FoodCartException, CustomerException, LoginException{
		FoodCart cart= foodCartService.removeItem(key, itemName);
		return new ResponseEntity<FoodCart>(cart, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/foodcart/{loginKey}")
	public ResponseEntity<FoodCart> clearCartHandler(@PathVariable("loginKey") String key) throws FoodCartException, CustomerException, LoginException{
		FoodCart cart= foodCartService.clearCart(key);
		return new ResponseEntity<FoodCart>(cart, HttpStatus.ACCEPTED);
	} 
}
