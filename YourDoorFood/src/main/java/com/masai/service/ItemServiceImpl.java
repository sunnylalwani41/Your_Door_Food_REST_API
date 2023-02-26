package com.masai.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CustomerException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.RestaurantException;
import com.masai.model.Category;
import com.masai.model.CurrentUserSession;
import com.masai.model.Customer;
import com.masai.model.Item;
import com.masai.model.Restaurant;
import com.masai.repository.CategoryRepo;
import com.masai.repository.CustomerRepo;
import com.masai.repository.ItemRepo;
import com.masai.repository.RestaurantRepo;
import com.masai.repository.SessionRepo;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private RestaurantRepo restaurantRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private SessionRepo sessionRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	
	@Override
	public Item addItem(String key,Item item)throws ItemException, LoginException, RestaurantException{
		
		CurrentUserSession currentUserSession= sessionRepo.findByUuid(key); 
		if(currentUserSession==null) throw new LoginException("Please login to add item");
		Restaurant restaurant = restaurantRepo.findById(currentUserSession.getId()).orElseThrow(()-> new RestaurantException("Please login as Restaurant"));
		
		if(item.getRestaurant()!=null && restaurant.getRestaurantId()!=item.getRestaurant().getRestaurantId()) throw new RestaurantException("Item can not be added"); 
		
		List<Item> items= restaurant.getItems();
		for(Item i: items) {
			if(i.getItemName().equals(item.getItemName())) {
				throw new ItemException("Item already exist");
			}
		}
		
		String categoryName = item.getCategory().getCategoryName();
		
		List<Category> categories= categoryRepo.findAll();
		
		Category category= null;
		
		for(Category c: categories) {
			if(c.getCategoryName().contains(categoryName)) {
				category=c;
				break;
			}
		}
		
		if(category==null) {
			category=new Category();
			category.setCategoryName(categoryName);
		}
		
		category.getItems().add(item);
		item.setCategory(category);
		item.setRestaurant(restaurant);
		restaurant.getItems().add(item);
		
		return itemRepo.save(item);
		
	}

	@Override
	public Item updateItem(String key,Item item) throws ItemException, LoginException, RestaurantException {
		
		CurrentUserSession currentUserSession= sessionRepo.findByUuid(key); 
		if(currentUserSession==null) throw new LoginException("Please login to add item");
		Restaurant restaurant = restaurantRepo.findById(currentUserSession.getId()).orElseThrow(()-> new RestaurantException("Please as Restaurant"));
		
		if(item.getRestaurant() != null && restaurant.getRestaurantId() != item.getRestaurant().getRestaurantId()) throw new RestaurantException("Item can not be added"); 
		
		List<Item> items= restaurant.getItems();
		
		Item verifiedItem = null;
		for(Item i: items) {
			if(i.getItemName().equalsIgnoreCase(item.getItemName())) {
				verifiedItem= i;
				break;
			}
		}
		
		if(verifiedItem==null) {
			return addItem(key, item);
		}
		
		if(item.getCost() != null)
			verifiedItem.setCost(item.getCost());
		if(item.getQuantity() != null)
			verifiedItem.setQuantity(item.getQuantity());
		
		return itemRepo.save(verifiedItem);
		
	}

	@Override
	public Item viewItem(String itemName, Integer restaurantId) throws ItemException, RestaurantException{
		
		Restaurant restaurant= restaurantRepo.findById(restaurantId).orElseThrow(()-> new RestaurantException("Restaurant not found"));
		
		List<Item> items= restaurant.getItems();
		
		for(Item i: items) {
			if(i.getItemName().equals(itemName) && i.getQuantity()>0) {
				
				return i;
			}
		}
		
		throw new ItemException("Item is not available in the restaurant"); 
		
	}

	@Override
	public String setItemNotAvailable(String key, Integer itemId) throws ItemException, RestaurantException, LoginException {
		
		CurrentUserSession currentUserSession= sessionRepo.findByUuid(key); 
		if(currentUserSession==null) throw new LoginException("Please login to add item");
		Restaurant restaurant = restaurantRepo.findById(currentUserSession.getId()).orElseThrow(()-> new RestaurantException("Please as Restaurant"));
		
		Item item= itemRepo.findById(itemId).orElseThrow(() -> new ItemException("Item not found with id "+ itemId));
		
		if(item.getRestaurant().getRestaurantId()!=restaurant.getRestaurantId()) throw new ItemException("Item not found");
		
		item.setQuantity(0);
		
		itemRepo.save(item);
		
		return "Item successfully set to not available";
		
	}

	@Override
	public List<Item> viewAllItemsByRestaurant(Integer restaurantId) throws ItemException ,RestaurantException{
		
		Restaurant restaurant =restaurantRepo.findById(restaurantId).orElseThrow(() -> new RestaurantException("Restaurant not found with id: "+ restaurantId));
		
		List<Item> items = restaurant.getItems();
		if(items.isEmpty()) throw new ItemException("No items found in this restaurant");
		
		List<Item> filteredItems= new ArrayList<>();
		for(Item i: items) {
			if(i.getQuantity()>0) filteredItems.add(i);
		}
			
		if(filteredItems.isEmpty()) throw new ItemException("No items found in this restaurant");
		return filteredItems;
		
	}

	@Override
	public List<Item> viewItemsOnMyAddress(String key, String itemName) throws ItemException, RestaurantException, LoginException, CustomerException{
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer = customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		if(customer.getAddress()==null) throw new CustomerException("please add address first");
		
		String pincode= customer.getAddress().getPincode();
		
		List<Restaurant> restaurants = restaurantRepo.findAll();
		
		List<Restaurant> filteredRestaurants= new ArrayList<>();
		
		for(Restaurant r: restaurants) {
			if(r.getAddress().getPincode().equals(pincode)) filteredRestaurants.add(r);
		}
		if(filteredRestaurants.isEmpty()) throw new RestaurantException("No Restaurant found in your area");
		
		List<Item> items= new ArrayList<>();
		
		for(Restaurant r: filteredRestaurants) {
			List<Item> temp= r.getItems();
			if(temp==null) continue;
			
			for(Item i: temp) {
				if(i.getItemName().equals(itemName)) items.add(i);
			}
		}
		
		if(items.isEmpty()) throw new ItemException("No Items Found");
		return items;
		
	}
}
