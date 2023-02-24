package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CategoryException;
import com.masai.exception.ItemException;
import com.masai.exception.RestaurantException;
import com.masai.model.Category;
import com.masai.model.Item;
import com.masai.model.Restaurant;
import com.masai.repository.CategoryRepo;
import com.masai.repository.ItemRepo;
import com.masai.repository.RestaurantRepo;

@Service
public class IItemServiceImpl implements IItemService{
	
	@Autowired
	private RestaurantRepo restaurantRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public Item addItem(String restName,Item item)throws ItemException {

		Restaurant existResturant = restaurantRepo.findbyName(restName);

		if (existResturant == null) {
			throw new ItemException(restName + " Resturant Not found with this name");
		}

		existResturant.getItems().add(item);

		return itemRepo.save(item);
	}

	@Override
	public Item updateItem(String restName,Item item) throws ItemException {
		Restaurant existResturant = restaurantRepo.findbyName(restName);

		if (existResturant == null) {
			throw new ItemException(restName + " Resturant Not found");
		}

		List<Item> itemsList = itemRepo.findAll();

		Item updatedItem = null;
		int flag = 0;
		for (Item elemitems : itemsList) {
			if ((elemitems.getItemId() == item.getItemId())) {
				elemitems.setItemName(item.getItemName());
				elemitems.setCategory(item.getCategory());
				elemitems.setCost(item.getCost());
				elemitems.setQuantity(item.getQuantity());
				updatedItem = elemitems;
				flag = 1;
			}
		}

		if (flag == 0) {
			throw new ItemException("Item Not Found with provided item ID");
		} else {
			itemRepo.save(updatedItem);
		}

		return updatedItem;
	}

	@Override
	public Item viewItem(Integer itemId) throws ItemException{
		Optional<Item> item = itemRepo.findById(itemId);
		if(item==null) throw new  ItemException("No such item exists with this itemName :"+itemId);	
		
		return item.get();
	}

	@Override
	public Item removeItem(Integer itemId) throws ItemException {
		Optional<Item> item = itemRepo.findById(itemId);
		if(item.isEmpty())
			throw new ItemException("No item found with this ID:"+itemId);
		
		itemRepo.delete(item.get());
		
		return item.get();
	}

	@Override
	public List<Item> viewAllItemsByCategory(Category cat) throws ItemException {
        List<Item> items =itemRepo.findByCategory(cat);
		
		if(items.isEmpty()) throw new ItemException("No item found with this category");
		
		return items;
		
	}

	@Override
	public List<Item> viewAllItemsByRestaurant(Restaurant res) throws ItemException ,RestaurantException{
    Restaurant restaurant =restaurantRepo.findbyName(res.getRestaurantName());
		
		if(restaurant==null) throw new RestaurantException("No item found in this restaurantName");
		List<Item> itemsList = itemRepo.findAll();
		if(itemsList==null) throw new ItemException("No item found ..");
		return itemsList;
	}

	@Override
	public List<Item> viewAllItemsByName(String name) throws ItemException {
		List<Item> items=itemRepo.findAll();
		if(items.isEmpty()) throw new ItemException("Items not found..");
		        
		return items;
	}



}
