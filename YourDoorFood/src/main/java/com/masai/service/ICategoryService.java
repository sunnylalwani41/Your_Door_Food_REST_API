package com.masai.service;

import java.util.List;

import com.masai.exception.CategoryException;
import com.masai.model.Category;

public interface ICategoryService {

	public Category addCategory(Category cat);
	public Category updateCategory(Integer catId)throws CategoryException;
	public Category removeCategory(Integer catId)throws CategoryException;
	public Category viewCategory(String categoryName)throws CategoryException;
	public List<Category> viewAllCategory(Category cat)throws CategoryException;
}
