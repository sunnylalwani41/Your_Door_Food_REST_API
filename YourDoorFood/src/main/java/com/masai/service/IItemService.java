package com.masai.service;

import java.util.List;

import com.masai.exception.CategoryException;
import com.masai.exception.ItemException;
import com.masai.exception.RestaurantException;
import com.masai.model.Category;
import com.masai.model.Item;
import com.masai.model.Restaurant;

public interface IItemService {

	public Item addItem(String restName,Item item)throws ItemException;
	public Item updateItem(String restName,Item item) throws ItemException;
	public Item viewItem(Integer itemId)throws ItemException;
	public Item removeItem(Integer itemId)throws ItemException;
	public List<Item> viewAllItemsByCategory(Category cat)throws ItemException;
	public List<Item> viewAllItemsByRestaurant(Restaurant res)throws ItemException,RestaurantException;
	public List<Item> viewAllItemsByName(String name)throws ItemException;
}
