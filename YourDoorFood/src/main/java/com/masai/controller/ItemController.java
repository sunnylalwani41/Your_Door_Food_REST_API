package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.ItemException;
import com.masai.exception.RestaurantException;
import com.masai.model.Category;
import com.masai.model.Item;
import com.masai.model.Restaurant;
import com.masai.service.IItemService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

	@Autowired
	private IItemService iItemService;

	@PostMapping("/add/{rstname}")
	public ResponseEntity<Item> addItemsHandler(@PathVariable("rstname") String resturantname,
			@Valid @RequestBody Item item) throws ItemException
	{
		Item addeditem = iItemService.addItem(resturantname, item);

		return new ResponseEntity<>(addeditem, HttpStatus.ACCEPTED);
	}

	
	@PutMapping("/update/{rstname}")
	public ResponseEntity<Item> updateItemsHandler(@PathVariable("rstname") String resturantname,
			@Valid @RequestBody Item item) throws ItemException
	{
		Item updateditem = iItemService.updateItem(resturantname, item);

		return new ResponseEntity<>(updateditem, HttpStatus.OK);
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<Item> viewItemHandler(@PathVariable("id")Integer itemId) throws ItemException
	{
		Item item = iItemService.viewItem(itemId);

		return new ResponseEntity<>(item, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{restntId}")
	public ResponseEntity<Item> removeItemHandler(@PathVariable("restntId") Integer restaurantId)throws ItemException
	{
		Item allitem = iItemService.removeItem(restaurantId);

		return new ResponseEntity<>(allitem, HttpStatus.OK);
	}
	
	
	@GetMapping("/view")
	public ResponseEntity<List<Item>> viewAllItemsByCategoryHandler(@RequestBody Category cat) throws ItemException

	{
		List<Item> items= iItemService.viewAllItemsByCategory(cat);

		return new ResponseEntity<>(items, HttpStatus.FOUND);
	}
	
	
	@GetMapping("/viewallitembyres/{restaurentId}")
	public ResponseEntity<List<Item>> viewAllItemsByRestaurantHandler(@RequestBody Restaurant res) throws ItemException, RestaurantException
			
	{
		List<Item> allitem = iItemService.viewAllItemsByRestaurant(res);

		return new ResponseEntity<>(allitem, HttpStatus.OK);
	}
	
	
	@GetMapping("/viewallitembyname/{itemname}")
	public ResponseEntity<List<Item>> viewAllItemsByNameHandler(@PathVariable("itemname") String itemname)
			throws ItemException
	{
		List<Item> allitem = iItemService.viewAllItemsByName(itemname);

		return new ResponseEntity<>(allitem, HttpStatus.OK);
	}
}
