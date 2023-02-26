package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.CustomerException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.RestaurantException;
import com.masai.model.Item;
import com.masai.service.IItemService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/YourDoorFood")
public class ItemController {

	@Autowired
	private IItemService iItemService;

	@PostMapping("/items/{logginKey}")
	public ResponseEntity<Item> addItemsHandler(@PathVariable("logginKey") String key,
			@Valid @RequestBody Item item) throws ItemException, LoginException, RestaurantException
	{
		Item addeditem = iItemService.addItem(key, item);

		return new ResponseEntity<>(addeditem, HttpStatus.ACCEPTED);
	}

	
	@PutMapping("/items/{logginKey}")
	public ResponseEntity<Item> updateItemsHandler(@PathVariable("logginKey") String key,
			@Valid @RequestBody Item item) throws ItemException, LoginException, RestaurantException
	{
		Item updateditem = iItemService.updateItem(key, item);

		return new ResponseEntity<>(updateditem, HttpStatus.OK);
	}
	
	@GetMapping("/items/{restaurantId}")
	public ResponseEntity<List<Item>> viewAllItemsByRestaurantHandler(@PathVariable("restaurantId")Integer restaurantId) throws ItemException, RestaurantException
	{
		List<Item> items = iItemService.viewAllItemsByRestaurant(restaurantId);

		return new ResponseEntity<>(items, HttpStatus.FOUND);
	}
	
	@PutMapping("/items/{logginKey}/{itemId}")
	public ResponseEntity<String> setItemNotAvailableHandler(@PathVariable("logginKey") String logginKey, @PathVariable("itemId") Integer itemId) throws ItemException, RestaurantException, LoginException{
		
		String result = iItemService.setItemNotAvailable(logginKey, itemId);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
	
	@GetMapping("/items/{itemName}/{restaurantId}")
	public ResponseEntity<Item> viewItemHandler(@PathVariable("itemName") String itemName,@PathVariable("restaurantId") Integer restaurantId) throws ItemException, RestaurantException{
		
		Item  item = iItemService.viewItem(itemName, restaurantId);
		
		return new ResponseEntity<>(item, HttpStatus.FOUND);
	}
	
	@GetMapping("/items/{loginKey}/{itemName}")
	public ResponseEntity<List<Item>> viewItemsOnMyAddressHandler(@PathVariable("loginKey") String loginKey, @PathVariable("itemName") String itemName) throws ItemException, RestaurantException, LoginException, CustomerException

	{
		List<Item> items= iItemService.viewItemsOnMyAddress(loginKey, itemName);

		return new ResponseEntity<>(items, HttpStatus.FOUND);
	}

}
