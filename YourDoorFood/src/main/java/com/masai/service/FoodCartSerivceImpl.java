package com.masai.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CustomerException;
import com.masai.exception.FoodCartException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.RestaurantException;
import com.masai.model.CurrentUserSession;
import com.masai.model.Customer;
import com.masai.model.FoodCart;
import com.masai.model.Item;
import com.masai.model.Restaurant;
import com.masai.repository.CustomerRepo;
import com.masai.repository.FoodCartRepo;
import com.masai.repository.RestaurantRepo;
import com.masai.repository.SessionRepo;

@Service
public class FoodCartSerivceImpl implements FoodCartService{
	@Autowired
	private FoodCartRepo cartRepo;
	
	@Autowired
	private SessionRepo sessionRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private RestaurantRepo restaurantRepo;
	
	@Override
	public FoodCart addItemToCart(String key, String itemName, Integer restaurantId) throws FoodCartException, LoginException, ItemException, RestaurantException, CustomerException{
		
		Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(() -> new RestaurantException("Restaurant does not exist"));
		
		List<Item> items = restaurant.getItems();
		Item item = null;
		for(Item i: items) {
			if(i.getItemName().equals(itemName)) {
				item = i;
				break;
			}
		}
		if(item == null) throw new ItemException("Item is not available in this restaurant");
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer= customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		FoodCart foodCart= customer.getFoodCart();
		
		Map<Item, Integer> itemsMap = foodCart.getItems();
		
		if(item.getQuantity()>0) {
			
			if(itemsMap.containsKey(item)) {
				itemsMap.put(item, itemsMap.get(item)+1);
			}
			else {
				itemsMap.put(item, 1);
			}
		}
		else {
			throw new ItemException("Insufficient item quantity");
		}
		
		foodCart.setCustomer(customer);
		customer.setFoodCart(foodCart);
		
		return cartRepo.save(foodCart);
	}

	@Override
	public FoodCart increaseQuantity(String key, String itemName, 	int quantity) throws FoodCartException, LoginException, ItemException, CustomerException{
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer = customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		FoodCart foodCart = customer.getFoodCart();
		Map<Item, Integer> itemsMap = foodCart.getItems();
		Item item= null;
		
		for(Map.Entry<Item, Integer> entry : itemsMap.entrySet()) {
			if(entry.getKey().getItemName().equals(itemName)) {
				item= entry.getKey();
				break;
			}
		}
		
		if(item == null) throw new FoodCartException("Item is not available in the cart, please add the item first");
		
		if(item.getQuantity() >= (quantity + itemsMap.get(item)))  itemsMap.put(item, itemsMap.get(item) + quantity);
		else throw new ItemException("Insufficient item quantity");
		
//		foodCart.setItems(itemsMap);
//		foodCart.setCustomer(customer);
//		customer.setFoodCart(foodCart);
		
		return cartRepo.save(foodCart);
	}

	@Override
	public FoodCart reduceQuantity(String key, String itemName, int quantity) throws FoodCartException, LoginException, ItemException, CustomerException{

		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer= customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		FoodCart foodCart = customer.getFoodCart();
		
		Map<Item, Integer> itemsMap = foodCart.getItems();
		Item item = null;
		for(Map.Entry<Item, Integer> entry : itemsMap.entrySet()) {
			if(entry.getKey().getItemName().equals(itemName)) {
				item = entry.getKey();
				break;
			}
		}
		if(item==null) throw new FoodCartException("Item is not available in the cart, please add the item first");
				
		if(item.getQuantity() == 0) {
			itemsMap.remove(item);
			cartRepo.save(foodCart);
			throw new ItemException("Item is already out of stock");
		}
		
		if(quantity < itemsMap.get(item)) itemsMap.put(item, itemsMap.get(item) - quantity);
		else {
			itemsMap.remove(item);
			cartRepo.save(foodCart);
			throw new ItemException(item.getItemName()+ " is removed from the cart");
		}
		
		return cartRepo.save(foodCart);
	}

	@Override
	public FoodCart removeItem(String key, String itemName) throws FoodCartException, CustomerException, LoginException{

		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer= customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		FoodCart foodCart= customer.getFoodCart();
		
		Map<Item, Integer> itmesMap = foodCart.getItems();
		Item item = null;
		for(Map.Entry<Item, Integer> entry : itmesMap.entrySet()) {
			if(entry.getKey().getItemName().equals(itemName)) {
				item = entry.getKey();
				break;
			}
		}
		if(item == null) throw new FoodCartException("Item is not available in the cart.");
			
		itmesMap.remove(item);
		
		return cartRepo.save(foodCart);
	}
	
	@Override
	public FoodCart clearCart(String key, String itemName) throws FoodCartException, CustomerException, LoginException {

		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer= customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		FoodCart foodCart = customer.getFoodCart();
		
		Map<Item, Integer> itemsMap = foodCart.getItems();
		
		if(itemsMap.size()==0) throw new FoodCartException("Cart already empty");
		
		itemsMap.clear();
		
		return cartRepo.save(foodCart);
	}

}
