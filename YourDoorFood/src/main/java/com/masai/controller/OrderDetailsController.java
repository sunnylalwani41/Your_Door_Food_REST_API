package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.BillException;
import com.masai.exception.CustomerException;
import com.masai.exception.FoodCartException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.OrderDetailsException;
import com.masai.exception.RestaurantException;
import com.masai.model.OrderDetails;
import com.masai.service.OrderService;

@RestController
@RequestMapping("/YourDoorFood")
public class OrderDetailsController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/orderdetailsByCOD/{loginKey}")
	public ResponseEntity<OrderDetails> placeOrderHandlerByCOD(@PathVariable("loginKey") String key) throws OrderDetailsException, LoginException, CustomerException, FoodCartException, ItemException, BillException{
		
		OrderDetails order= orderService.placeOrder(key, "CASH_ON_DELIVERY");
		return new ResponseEntity<OrderDetails>(order, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/orderdetailsByPrepaid/{loginKey}")
	public ResponseEntity<OrderDetails> placeOrderHandlerByPrepaid(@PathVariable("loginKey") String key) throws OrderDetailsException, LoginException, CustomerException, FoodCartException, ItemException, BillException{
		
		OrderDetails order= orderService.placeOrder(key, "PAYMENT_SUCCESS");
		return new ResponseEntity<OrderDetails>(order, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/orderdetails/{loginKey}/{orderId}")
	public ResponseEntity<String> cancelOrderHandler(@PathVariable("loginKey") String key, @PathVariable("orderId") Integer orderId) throws OrderDetailsException, LoginException, CustomerException{
		
		String result= orderService.cancelOrder(key, orderId);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@GetMapping("/orderdetailsByCustomer/{loginKey}/{orderId}")
	public ResponseEntity<OrderDetails> viewOrderByIdByCustomerHandler(@PathVariable("loginKey") String key, @PathVariable("orderId") Integer orderId) throws OrderDetailsException, CustomerException, LoginException{
		
		OrderDetails orderDetails= orderService.viewOrderByIdByCustomer(key, orderId);
		return new ResponseEntity<OrderDetails>(orderDetails, HttpStatus.OK);
	}
	
	@GetMapping("/orderdetailsByRestaurant/{loginKey}/{orderId}")
	public ResponseEntity<OrderDetails> viewOrderByIdByRestaurantHandler(@PathVariable("loginKey") String key, @PathVariable("orderId") Integer orderId) throws OrderDetailsException, LoginException, RestaurantException {
		
		OrderDetails orderDetails= orderService.viewOrderByIdByRestaurant(key, orderId);
		return new ResponseEntity<OrderDetails>(orderDetails, HttpStatus.OK);
	}
	
	@GetMapping("/orderdetails/{loginKey}")
	public ResponseEntity<List<OrderDetails>> viewAllOrderByCustomerHandler(@PathVariable("loginKey") String key) throws OrderDetailsException, CustomerException, LoginException {
		
		List<OrderDetails> orders= orderService.viewAllOrdersByCustomer(key);
		return new ResponseEntity<List<OrderDetails>>(orders, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/orderdetails/restaurant/{loginKey}")
	public ResponseEntity<List<OrderDetails>> viewAllOrderByRestaurantHandler(@PathVariable("loginKey") String key) throws OrderDetailsException, LoginException, RestaurantException {
		
		List<OrderDetails> orders= orderService.viewAllOrdersByRestaurant(key);
		return new ResponseEntity<List<OrderDetails>>(orders, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/orderdetails/{loginKey}/{customerId}")
	public ResponseEntity<List<OrderDetails>> viewAllOrderByRestaurantByCustomerIdHandler(@PathVariable("loginKey") String key, @PathVariable("customerId") Integer customerId) throws OrderDetailsException, LoginException, RestaurantException, CustomerException {
		
		List<OrderDetails> orders= orderService.viewAllOrdersByRestaurantByCustomerId(key, customerId);
		return new ResponseEntity<List<OrderDetails>>(orders, HttpStatus.ACCEPTED);
	}
	
}
