package com.masai.service;


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
import com.masai.repository.ItemRepo;
import com.masai.repository.RestaurantRepo;
import com.masai.repository.SessionRepo;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FoodCartSerivceImpl implements FoodCartService{
	@Autowired
	private FoodCartRepo cartRepo;
	
	@Autowired
	private SessionRepo sessionRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private RestaurantRepo restaurantRepo;
	
	@Override
	public FoodCart addItemToCart(String mobileNo, String itemName, Integer restaurantId) throws FoodCartException, LoginException, ItemException, RestaurantException, CustomerException{
		Restaurant restaurant= restaurantRepo.findById(restaurantId).orElseThrow(() -> new RestaurantException("Restaurant does not exist"));
		List<Item> items= restaurant.getItems();
		Item item=null;
		for(Item i: items) {
			if(i.getItemName().equals(itemName)) {
				item=i;
				break;
			}
		}
		if(item==null) {
			throw new ItemException("Item is not available in this restaurant");
		}
		
		Customer customer= customerRepo.findByMobileNumber(mobileNo);
		if(customer==null) {
			throw new CustomerException("Customer is not registered");
		}
		CurrentUserSession existingUser= sessionRepo.findById(customer.getCustomerID()).orElseThrow(()-> new LoginException("User currently not logged-in"));
		FoodCart foodCart= customer.getFoodCart();
		Map<Item, Integer> itemMap= foodCart.getItems();
		if(item.getQuantity()>0) {
			if(itemMap.containsKey(item)) {
				itemMap.put(item, itemMap.get(item)+1);
			}
			else {
				itemMap.put(item, 1);
				
			}
		}
		else {
			throw new ItemException("Insufficient item quantity");
		}
		
		return cartRepo.save(foodCart);
	}

	@Override
	public FoodCart increaseQuantity(String mobileNo, String itemName) throws FoodCartException, LoginException, ItemException, CustomerException{
//		Restaurant restaurant= restaurantRepo.findById(restaurantId).orElseThrow(() -> new RestaurantException("Restaurant does not exist"));
//		List<Item> items= restaurant.getItems();
		
//		if(item==null) {
//			throw new ItemException("Item is not available in this restaurant");
//		}
		
		Customer customer= customerRepo.findByMobileNumber(mobileNo);
		if(customer==null) {
			throw new CustomerException("Customer is not registered");
		}
		CurrentUserSession existingUser= sessionRepo.findById(customer.getCustomerID()).orElseThrow(()-> new LoginException("User currently not logged-in"));
		FoodCart foodCart= customer.getFoodCart();
		Map<Item, Integer> itemMap= foodCart.getItems();
		Item item=null;
		for(Map.Entry<Item, Integer> entry:itemMap.entrySet()) {
			if(entry.getKey().getItemName().equals(itemName)) {
				item= entry.getKey();
				break;
			}
		}
		if(item==null)
			throw new ItemException("Item is not available in the cart, please add the item first");
		if(item.getQuantity()>0) {
			if(itemMap.containsKey(item)) {
				itemMap.put(item, itemMap.get(item)+1);
			}
			else {
				itemMap.put(item, 1);
				
			}
		}
		else {
			throw new ItemException("Insufficient item quantity");
		}
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
