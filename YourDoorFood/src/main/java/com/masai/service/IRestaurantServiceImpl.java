package com.masai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.RestaurantException;
import com.masai.model.Item;
import com.masai.model.Restaurant;
import com.masai.repository.ItemRepo;
import com.masai.repository.RestaurantRepo;
import com.masai.repository.SessionRepo;

@Service
public class IRestaurantServiceImpl implements IRestaurantService {

	@Autowired
	private RestaurantRepo rRepo;

	@Autowired
	private ItemRepo iRepo;

	@Autowired
	private SessionRepo sRepo;

	@Override
	public Restaurant addRestaurant(Restaurant res) throws RestaurantException {
		Restaurant restaurant =rRepo.save(res);
		return restaurant;
	}

	@Override
	public Restaurant updateRestaurant(Restaurant res) throws RestaurantException {
		Optional<Restaurant> restaurant = rRepo.findById(res.getRestaurantId());
		if(restaurant.isEmpty())
			throw new RestaurantException("No Restaurant found with this ID:"+res.getRestaurantId());
		return restaurant.get();
	}


	@Override
	public Restaurant removeRestaurant(Integer resId) throws RestaurantException {
		Optional<Restaurant> restaurant = rRepo.findById(resId);
		if(restaurant.isEmpty())
			throw new RestaurantException("No Restaurant found with this ID:"+resId);
		
		rRepo.delete(restaurant.get());
		
		return restaurant.get();
	}

	@Override
	public Restaurant viewRestaurant(Restaurant res) throws RestaurantException {
		Restaurant restaurant = rRepo.findbyName(res.getRestaurantName());
		if(restaurant==null) throw new  RestaurantException("No such restaurant exists with this restaurantName :"+res.getRestaurantName());	
		return restaurant;
	}

	@Override
	public List<Restaurant> viewNearByRestaurant(String location) throws RestaurantException {

		List<Restaurant> restaurant =rRepo.getRestByLocation(location);
		if(restaurant.isEmpty())
        throw new RestaurantException("Restaurants not found with this location :"+rRepo.getRestByLocation(location));		
		return restaurant;
	}

	@Override
	public List<Restaurant> viewRestaurantByItemName(String itemname) throws RestaurantException {

		List<Item> itemsList=iRepo.findByItemname(itemname);
		
		
		List<Restaurant> allResturant = new ArrayList<>();

		
				itemsList.forEach(s -> System.out.println(s.getItemName() + "==>" + s.getItemId() + "==++ "));

		for (Item items : itemsList) {
			allResturant.add(items.getRestaurants().get(0));
		}

		System.out.println("********************************************************");
		for (Restaurant restaurants : allResturant) {
			System.out.println(restaurants.getRestaurantName());
		}

		return allResturant;
		
	}

	


	
}
