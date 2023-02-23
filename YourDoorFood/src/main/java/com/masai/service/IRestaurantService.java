package com.masai.service;

import java.util.List;

import com.masai.exception.RestaurantException;
import com.masai.model.Restaurant;

public interface IRestaurantService {

	public Restaurant addRestaurant(Restaurant res)throws RestaurantException;
	public Restaurant updateRestaurant(Restaurant res)throws RestaurantException;
	public Restaurant removeRestaurant(Integer resId)throws RestaurantException;
	public Restaurant viewRestaurant(Restaurant res)throws RestaurantException;
	public List<Restaurant> viewNearByRestaurant(String location)throws RestaurantException;
	public List<Restaurant> viewRestaurantByItemName(String itemname)throws RestaurantException;
	
	
}
