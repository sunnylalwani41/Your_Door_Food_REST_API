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
import com.masai.exception.LoginException;
import com.masai.exception.RestaurantException;
import com.masai.model.Restaurant;
import com.masai.model.Suggestion;
import com.masai.service.RestaurantService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/yourdoorfood")
public class RestaurantController {

	@Autowired
	private RestaurantService resService;
	
	@PostMapping("/restaurants/{verficationId}")
	public ResponseEntity<Restaurant> addRestuarants(@PathVariable("verficationId") Integer verificationId,@Valid @RequestBody Restaurant restaurant) throws RestaurantException{
		return new ResponseEntity<>(resService.addRestaurant(verificationId,restaurant),HttpStatus.CREATED);
	}
	
	@PutMapping("/restaurants/{loginkey}")
	public ResponseEntity<Restaurant> updateRestuarants( @PathVariable("loginkey") String  key,@Valid @RequestBody Restaurant restaurant) throws RestaurantException, LoginException{
		return new ResponseEntity<>(resService.updateRestaurant(key,restaurant),HttpStatus.ACCEPTED);
	}

	
	@GetMapping("/restaurants/{resId}")
	public ResponseEntity<Restaurant> viewRestuarant(@PathVariable("resId") Integer resId) throws RestaurantException{
		return new ResponseEntity<>(resService.viewRestaurant(resId),HttpStatus.FOUND);
	
	}
	@GetMapping("/restaurants/location/{city}/{pincode}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByLocation(@PathVariable("city") String cityName,@PathVariable("pincode") String pincode) throws RestaurantException {
		return new ResponseEntity<>(resService.viewNearByRestaurant(cityName,pincode),HttpStatus.FOUND);
	
	}
	
	@GetMapping("/restaurants/{itemName}/{pincode}")
	public ResponseEntity<List<Restaurant>> getRestaurantsByItemName(@PathVariable("itemName") String itemName, @PathVariable("pincode") String pincode) throws RestaurantException {
		return new ResponseEntity<>(resService.viewRestaurantByItemName(itemName,pincode),HttpStatus.FOUND);
	
	}
	
	
	@GetMapping("/restaurants/status/{resId}")	
	public	ResponseEntity<String> restaurantStatus(@PathVariable("resId") Integer restaurantId) throws RestaurantException {
			return new ResponseEntity<>(resService.restaurantStatus(restaurantId),HttpStatus.FOUND);
		}
	@PostMapping("/restaurants/{loginkey}/{suggestion}/{pincode}")
	public ResponseEntity<String> giveSuggestionsAboutItem(@PathVariable("loginkey") String key,@Valid @RequestBody Suggestion suggestion, @PathVariable("pincode") String pincode) throws CustomerException, LoginException, RestaurantException{
		return new ResponseEntity<>(resService.giveSuggestionAboutItem(key,suggestion,pincode),HttpStatus.ACCEPTED);
		
	}
	
	@GetMapping("/restaurants/{loginkey}")
	public ResponseEntity<List<Suggestion>> viewSuggestions(@PathVariable("loginkey") String key) throws LoginException, RestaurantException{
		return new ResponseEntity<>(resService.viewSuggestions(key),HttpStatus.FOUND);
		
	}
}
