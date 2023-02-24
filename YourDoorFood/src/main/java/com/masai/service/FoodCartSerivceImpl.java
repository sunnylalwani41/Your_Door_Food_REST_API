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
import com.masai.model.ItemRestaurantDTO;
import com.masai.model.Restaurant;
import com.masai.repository.CustomerRepo;
import com.masai.repository.FoodCartRepo;
import com.masai.repository.ItemRepo;
import com.masai.repository.RestaurantRepo;
import com.masai.repository.SessionRepo;

import java.util.HashMap;
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
		Set<Item> items= restaurant.getItems();
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
		
		Map<ItemRestaurantDTO, Integer> dtoMap= foodCart.getItemsDTO();
		if(item.getQuantity()>0) {
			
			ItemRestaurantDTO dto= new ItemRestaurantDTO(item, restaurant);
			
			if(dtoMap.containsKey(dto)) {
				dtoMap.put(dto, dtoMap.get(dto)+1);
			}
			else {
				dtoMap.put(dto, 1);
				
			}
		}
		else {
			throw new ItemException("Insufficient item quantity");
		}
		foodCart.setCustomer(customer);
		
		return cartRepo.save(foodCart);
	}

	@Override
	public FoodCart increaseQuantity(String mobileNo, String itemName, 	int quantity) throws FoodCartException, LoginException, ItemException, CustomerException{
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
		Map<ItemRestaurantDTO, Integer> dtoMap= foodCart.getItemsDTO();
		ItemRestaurantDTO dto= null;
		for(Map.Entry<ItemRestaurantDTO, Integer> entry:dtoMap.entrySet()) {
			if(entry.getKey().getItem().getItemName().equals(itemName)) {
				dto= entry.getKey();
				break;
			}
		}
		if(dto==null)
			throw new FoodCartException("Item is not available in the cart, please add the item first");
		if(dto.getItem().getQuantity()>(quantity+dtoMap.get(dto))) {
			
			dtoMap.put(dto, dtoMap.get(dto)+quantity);
		}
		else {
			throw new ItemException("Insufficient item quantity");
		}
		return cartRepo.save(foodCart);
	}

	@Override
	public FoodCart reduceQuantity(String mobileNo, String itemName, int quantity) throws FoodCartException, LoginException, ItemException, CustomerException{


		Customer customer= customerRepo.findByMobileNumber(mobileNo);
		if(customer==null) {
			throw new CustomerException("Customer is not registered");
		}
		CurrentUserSession existingUser= sessionRepo.findById(customer.getCustomerID()).orElseThrow(()-> new LoginException("User currently not logged-in"));
		
		
		FoodCart foodCart= customer.getFoodCart();
		
		Map<ItemRestaurantDTO, Integer> dtoMap= foodCart.getItemsDTO();
		ItemRestaurantDTO dto= null;
		for(Map.Entry<ItemRestaurantDTO, Integer> entry:dtoMap.entrySet()) {
			if(entry.getKey().getItem().getItemName().equals(itemName)) {
				dto= entry.getKey();
				break;
			}
		}
		if(dto==null)
			throw new FoodCartException("Item is not available in the cart, please add the item first");
				
		if(dto.getItem().getQuantity()==0) {
			
			dtoMap.remove(dto);
			cartRepo.save(foodCart);
			throw new ItemException("Item is already out of stock");
			
		}
		if(quantity<=dtoMap.get(dto)) {
			dtoMap.put(dto, dtoMap.get(dto)-quantity);
			if(dtoMap.get(dto)==0)
				dtoMap.remove(dto);
		}
		else {
			dtoMap.remove(dto);
			cartRepo.save(foodCart);
			throw new ItemException(dto.getItem().getItemName()+ " is removed from the cart");
		}
		return cartRepo.save(foodCart);
	}

	@Override
	public FoodCart removeItem(String mobileNo, String itemName) throws FoodCartException, CustomerException, LoginException{
		Customer customer= customerRepo.findByMobileNumber(mobileNo);
		if(customer==null) {
			throw new CustomerException("Customer is not registered");
		}
		CurrentUserSession existingUser= sessionRepo.findById(customer.getCustomerID()).orElseThrow(()-> new LoginException("User currently not logged-in"));
		
		
		FoodCart foodCart= customer.getFoodCart();
		
		Map<ItemRestaurantDTO, Integer> dtoMap= foodCart.getItemsDTO();
		ItemRestaurantDTO dto= null;
		for(Map.Entry<ItemRestaurantDTO, Integer> entry:dtoMap.entrySet()) {
			if(entry.getKey().getItem().getItemName().equals(itemName)) {
				dto= entry.getKey();
				break;
			}
		}
		if(dto==null)
			throw new FoodCartException("Item is not available in the cart.");
			
		dtoMap.remove(dto);
		
		return cartRepo.save(foodCart);
	}
	
	@Override
	public FoodCart clearCart(String mobileNo, String itemName) throws FoodCartException, CustomerException, LoginException {

		
		Customer customer= customerRepo.findByMobileNumber(mobileNo);
		if(customer==null) {
			throw new CustomerException("Customer is not registered");
		}
		CurrentUserSession existingUser= sessionRepo.findById(customer.getCustomerID()).orElseThrow(()-> new LoginException("User currently not logged-in"));
		
		
		FoodCart foodCart= customer.getFoodCart();
		
		Map<ItemRestaurantDTO, Integer> dtoMap= foodCart.getItemsDTO();
		
		
		
		if(dtoMap.size()==0)
			throw new FoodCartException("Cart already empty");
		dtoMap.clear();
		foodCart.setItemsDTO(dtoMap);
		return cartRepo.save(foodCart);
	}

}
