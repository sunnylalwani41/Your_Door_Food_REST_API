package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.CategoryException;
import com.masai.model.Category;
import com.masai.service.ICategoryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	@Autowired
	private ICategoryService iCategoryService;
	
	@PostMapping("/add")
	public ResponseEntity<Category> addCategoryHandler(@RequestBody Category cat) {
		return new ResponseEntity<>(iCategoryService.addCategory(cat),HttpStatus.CREATED);
		
	}
	
	 @PutMapping("/update/{id}")
	public ResponseEntity<Category> updateCategoryHandler(@PathVariable("id") Integer catId) throws CategoryException{
		return new  ResponseEntity<>(iCategoryService.updateCategory(catId),HttpStatus.ACCEPTED);
		
	}
	 

	 @DeleteMapping("/delete/{id}")
	public ResponseEntity<Category> deleteCategoryHandler(@PathVariable("id") Integer catId) throws CategoryException{
		return new  ResponseEntity<>(iCategoryService.removeCategory(catId),HttpStatus.ACCEPTED);
		
	}
	 
	 @GetMapping("/view/{cname}")
		public ResponseEntity<Category> viewCategoryHandler(@PathVariable("cname") String catName) throws CategoryException{
			return new  ResponseEntity<>(iCategoryService.viewCategory(catName),HttpStatus.FOUND);
			
		}
	 
	 @GetMapping("/view")
		public ResponseEntity<List<Category>> viewAllCategoryHandler() throws CategoryException{
			return new  ResponseEntity<>(iCategoryService.viewAllCategory(),HttpStatus.FOUND);
			
		}
	 
	 
}
