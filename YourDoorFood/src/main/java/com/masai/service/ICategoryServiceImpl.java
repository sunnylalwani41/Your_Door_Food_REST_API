package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CategoryException;
import com.masai.exception.RestaurantException;
import com.masai.model.Category;
import com.masai.model.Restaurant;
import com.masai.repository.CategoryRepo;

@Service
public class ICategoryServiceImpl implements  ICategoryService{
	@Autowired
	private CategoryRepo categoryRepo;
	@Override
	public Category addCategory(Category cat) {
	  Category category =categoryRepo.save(cat);
	return category;
	}

	@Override
	public Category updateCategory(Integer catId) throws CategoryException {
		Optional<Category> category = categoryRepo.findById(catId);
		if(category.isEmpty())
			throw new CategoryException("No Category found with this ID:"+catId);
		return category.get();
	}

	@Override
	public Category removeCategory(Integer catId) throws CategoryException {
		Optional<Category> category = categoryRepo.findById(catId);
		if(category.isEmpty())
			throw new CategoryException("No category found with this ID:"+catId);
		
		categoryRepo.delete(category.get());
		
		return category.get();
	}

	@Override
	public Category viewCategory(String categoryName) throws CategoryException {
		 Category category = categoryRepo.findByCategoryName(categoryName);
		if(category==null) throw new  CategoryException("No such Category exists with this categoryName :"+categoryName);	
		return category;
	}

	@Override
	public List<Category> viewAllCategory() throws CategoryException {

    List<Category> categories=categoryRepo.findAll();
    if(categories.isEmpty()) throw new CategoryException("No such category founds..");
    return categories;
	}

}
