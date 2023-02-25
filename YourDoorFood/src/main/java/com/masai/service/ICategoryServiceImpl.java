package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CategoryException;
import com.masai.model.Category;
import com.masai.model.Item;
import com.masai.repository.CategoryRepo;

@Service
public class ICategoryServiceImpl implements  ICategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public List<Item> getItemsByCategoryName(String categoryName) throws CategoryException {
	    
	    Category category = categoryRepo.findByCategoryName(categoryName);
	    if(category == null) throw new CategoryException("No category found as: " + categoryName);
	    
	    List<Item> items = category.getItems();
	    if(items.isEmpty()) throw new CategoryException("No items found in this category");
	    
	    return items;
	}

}
