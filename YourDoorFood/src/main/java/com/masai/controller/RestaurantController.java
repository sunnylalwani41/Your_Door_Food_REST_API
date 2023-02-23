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

import com.masai.exception.RestaurantException;
import com.masai.model.Restaurant;
import com.masai.service.IRestaurantService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private IRestaurantService resService;
	
	@PostMapping("/add")
	public ResponseEntity<Restaurant> addRestuarants(@Valid @RequestBody Restaurant restaurant) throws RestaurantException{
		return new ResponseEntity<>(resService.addRestaurant(restaurant),HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Restaurant> updateRestuarants(@RequestBody Restaurant restaurant) throws RestaurantException{
		return new ResponseEntity<>(resService.updateRestaurant(restaurant),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Restaurant> deleteRestuarants(@PathVariable("id") Integer resId) throws RestaurantException{
		return new ResponseEntity<>(resService.removeRestaurant(resId),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/view")
	public ResponseEntity<Restaurant> viewRestuarant(@RequestBody Restaurant res) throws RestaurantException{
		return new ResponseEntity<>(resService.viewRestaurant(res),HttpStatus.FOUND);
	}
	@GetMapping("/findbylocation/{location}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByLocation(@PathVariable("location") String location) throws RestaurantException {
		return new ResponseEntity<>(resService.viewNearByRestaurant(location),HttpStatus.FOUND);
	
	}
	
	@GetMapping("/findbyitemname/{itemName}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByItemName(@PathVariable("itemName") String itemName) throws RestaurantException {
		return new ResponseEntity<>(resService.viewRestaurantByItemName(itemName),HttpStatus.FOUND);
	
	}
	}
