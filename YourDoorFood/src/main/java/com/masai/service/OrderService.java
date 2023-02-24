package com.masai.service;

import com.masai.exception.OrderDetailsException;
import com.masai.model.Customer;
import com.masai.model.OrderDetails;
import com.masai.model.Restaurant;

import java.util.List;


public interface OrderService {
	public OrderDetails addOrder(OrderDetails order) throws OrderDetailsException;
	
	public OrderDetails updateOrder(OrderDetails order) throws OrderDetailsException;
	
	public OrderDetails removeOrder(OrderDetails order) throws OrderDetailsException;
	
	public OrderDetails viewOrder(OrderDetails order) throws OrderDetailsException;
	
	public List<OrderDetails> viewAllOrders(Restaurant res) throws OrderDetailsException;
	
	public List<OrderDetails> viewAllOrders(Customer customer) throws OrderDetailsException;
}
