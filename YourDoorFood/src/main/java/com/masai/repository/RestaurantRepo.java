package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import com.masai.model.Item;
=======
import com.masai.exception.RestaurantException;
>>>>>>> branch 'main' of https://github.com/sunnylalwani41/tasty-hour-5423.git
import com.masai.model.Restaurant;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Integer>{

	public Restaurant findbyName(String resName);
	public List<Restaurant> getRestByLocation(String location);
	
	public Restaurant findByMobileNumber(String mobileNumer) throws RestaurantException;

}
