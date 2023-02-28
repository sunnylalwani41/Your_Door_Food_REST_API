package com.masai.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.BillException;
import com.masai.exception.CustomerException;
import com.masai.exception.FoodCartException;
import com.masai.exception.ItemException;
import com.masai.exception.LoginException;
import com.masai.exception.OrderDetailsException;
import com.masai.exception.RestaurantException;
import com.masai.model.Bill;
import com.masai.model.CurrentUserSession;
import com.masai.model.Customer;
import com.masai.model.FoodCart;
import com.masai.model.Item;
import com.masai.model.ItemQuantityDTO;
import com.masai.model.OrderDetails;
import com.masai.model.Restaurant;
import com.masai.model.Status;
import com.masai.repository.CustomerRepo;
import com.masai.repository.FoodCartRepo;
import com.masai.repository.ItemRepo;
import com.masai.repository.OrderDetailsRepo;
import com.masai.repository.RestaurantRepo;
import com.masai.repository.SessionRepo;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDetailsRepo orderDetailsRepo;
	
	@Autowired
	private SessionRepo sessionRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private RestaurantRepo restaurantRepo;
	
	@Autowired
	private FoodCartRepo foodCartRepo;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Override
	public OrderDetails placeOrder(String key, String paymentType) throws OrderDetailsException, LoginException, CustomerException, FoodCartException, ItemException, BillException, RestaurantException {
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer = customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		FoodCart foodCart = customer.getFoodCart();

		Map<Integer, Integer> itemsMap = foodCart.getItems();
		if(itemsMap.isEmpty()) throw new FoodCartException("Cart is empty");
		
		if(customer.getAddress() == null) throw new CustomerException("Please add adress to place order");
		
		for(Map.Entry<Integer, Integer> entry : itemsMap.entrySet()) {
			
			Item itemInCart = itemRepo.findById(entry.getKey()).get();
			
			if(itemInCart.getQuantity() < entry.getValue()) {
				throw new ItemException("Insufficient item quantity to the restaurant");				
			}
			
			if(restaurantService.restaurantStatus(itemInCart.getRestaurant().getRestaurantId()).equals("Closed")) {
				throw new RestaurantException(itemInCart.getRestaurant().getRestaurantName() + " is closed");
			}
			
			if(!itemInCart.getRestaurant().getAddress().getPincode().equals(customer.getAddress().getPincode())) {
				throw new CustomerException("This item is not deliverable in your area");
			}
		}

		List<ItemQuantityDTO> itemsDto = new ArrayList<>();
		
		Double sum = 0.0;
		for(Map.Entry<Integer, Integer> entry : itemsMap.entrySet()) {
			
			Item item = itemRepo.findById(entry.getKey()).get();

			item.setQuantity(item.getQuantity() - entry.getValue());
			sum += item.getCost() * entry.getValue();
			
			Restaurant restaurant = item.getRestaurant();
			

			itemRepo.save(item);
			
			
			ItemQuantityDTO dto = new ItemQuantityDTO(item.getItemId(), item.getItemName(), entry.getValue(), item.getCategory().getCategoryName(), item.getCost(), item.getRestaurant().getRestaurantName(), item.getRestaurant().getRestaurantId());
			
			itemsDto.add(dto);
			
			restaurant.getCustomers().add(customer.getCustomerID());
			restaurantRepo.save(restaurant);
		}

		OrderDetails orderDetails= new OrderDetails();
		orderDetails.setItems(itemsDto);
		orderDetails.setOrderDate(LocalDateTime.now());
		orderDetails.setPaymentStatus(Status.valueOf(paymentType));
		orderDetails.setTotalAmount(sum);

		Bill bill = billService.genrateBill(orderDetails);

		orderDetails.setBill(bill);;
		
		foodCart.setItems(new HashMap<Integer, Integer>());
		foodCartRepo.save(foodCart);
		
		orderDetails.setCustomer(customer);
		orderDetails = orderDetailsRepo.save(orderDetails);

		customer.getOrders().add(orderDetails);
		customerRepo.save(customer);

//		Map<Integer, RestaurantOrders> mapForRestaurantOrders = new HashMap<>();
//			
//		for(ItemQuantityDTO i : orderDetails.getItems()) {
//			Integer restaurantId = i.getRestaurantId();
//			
//			Restaurant restaurant = restaurantRepo.findById(restaurantId).get();			
//			if(mapForRestaurantOrders.containsKey(restaurantId)){
//				RestaurantOrders restaurantOrder = mapForRestaurantOrders.get(restaurantId);
//				restaurantOrder.getItems().add(i);
//				restaurantOrder.setTotalCost(restaurantOrder.getTotalCost() + (i.getCost() * i.getOrderedQuantity()));
//				
//				restaurant.getRestaurantOrders().add(restaurantOrder);
//			}else {
//				RestaurantOrders restaurantOrder = new RestaurantOrders();
//				restaurantOrder.setOrderId(orderDetails.getOrderId());
//				restaurantOrder.setRestaurantId(restaurantId);
//				restaurantOrder.setTotalCost(i.getCost() * i.getOrderedQuantity());
//				restaurantOrder.getItems().add(i);
//				
//				mapForRestaurantOrders.put(restaurantId, restaurantOrder);
//				restaurant.getRestaurantOrders().add(restaurantOrder);
//			}
//			
//			restaurantRepo.save(restaurant);
//		}
		
		return orderDetails;
		
	}
	
	@Override
	public String cancelOrder(String key, Integer orderId) throws OrderDetailsException, LoginException, CustomerException {
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer = customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		OrderDetails orderDetails = orderDetailsRepo.findById(orderId).orElseThrow(()-> new OrderDetailsException("Please pass valid order Id"));
		
		if(orderDetails.getCustomer().getCustomerID() != customer.getCustomerID()) throw new CustomerException("Please pass valid order Id");
		
		LocalDateTime deliverTime = orderDetails.getOrderDate().plusMinutes(20);
		
		if(LocalDateTime.now().isAfter(deliverTime.minusMinutes(10))) {
			throw new OrderDetailsException("Order can not be cancelled, time limit exceeded for cancellation");
		}
		
		List<ItemQuantityDTO> itemsDto = orderDetails.getItems();

		System.out.println(orderDetails.getOrderId());
		System.out.println(itemsDto);
		orderDetailsRepo.delete(orderDetails);
		
		System.out.println(itemsDto);
		
		for(ItemQuantityDTO i : itemsDto) {
			
			Item item = itemRepo.findById(i.getItemId()).get();
			item.setQuantity(item.getQuantity() + i.getOrderedQuantity());
			itemRepo.save(item);
		}
		
		return "Order cancelled successfully";
		
	}

	@Override
	public OrderDetails viewOrderByIdByCustomer(String key, Integer orderId) throws OrderDetailsException, CustomerException, LoginException {
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer = customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		OrderDetails orderDetails = orderDetailsRepo.findById(orderId).orElseThrow(()-> new OrderDetailsException("Please pass valid order Id"));
		
		if(orderDetails.getCustomer().getCustomerID() != customer.getCustomerID()) throw new OrderDetailsException("Please pass valid order Id");
		
		return orderDetails;
		
	}
	
	@Override
	public OrderDetails viewOrderByIdByRestaurant(String key, Integer orderId) throws OrderDetailsException, RestaurantException, LoginException{

		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to view your order");
		Restaurant restaurant = restaurantRepo.findById(currentUserSession.getId()).orElseThrow(()-> new RestaurantException("Please login as Restaurant"));
		
		Set<Integer> customersIds = restaurant.getCustomers();
		
		List<Customer> customers = new ArrayList<>();
		for(Integer i : customersIds) {
			customers.add(customerRepo.findById(i).get());
		}
		
		for(Customer c : customers) {
			List<OrderDetails> temp = c.getOrders();
			
			for(OrderDetails o : temp) {

				if(o.getOrderId().equals(orderId)) {
					Double sum = 0.0;

					List<ItemQuantityDTO> itemsDto = o.getItems();
					
					List<ItemQuantityDTO> restaurantOrderDetails = new ArrayList<>();
					
					for(ItemQuantityDTO dto: itemsDto) {
						if(dto.getRestaurantId() == restaurant.getRestaurantId()) {
							restaurantOrderDetails.add(dto);
							sum += dto.getCost() * dto.getOrderedQuantity();
						}
					}

					if(restaurantOrderDetails.isEmpty()) throw new OrderDetailsException("Please enter valid order id");
					o.setTotalAmount(sum);
					o.setItems(restaurantOrderDetails);
					return o;
				}
				
			}
		}

		throw new OrderDetailsException("Please enter valid order id");
		
	}
	
	@Override
	public List<OrderDetails> viewAllOrdersByRestaurant(String key) throws OrderDetailsException, LoginException , RestaurantException{
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Restaurant restaurant = restaurantRepo.findById(currentUserSession.getId()).orElseThrow(()-> new RestaurantException("Please login as Restaurant"));
		
		Set<Integer> customersIds = restaurant.getCustomers();
		
		List<Customer> customers = new ArrayList<>();
		for(Integer i : customersIds) {
			customers.add(customerRepo.findById(i).get());
		}
		
		List<OrderDetails> orders = new ArrayList<>();
		System.out.println(customers.size());
		for(Customer c: customers) {
			List<OrderDetails> temp = c.getOrders();
			
			for(OrderDetails o: temp) {
				
				Double sum = 0.0;
				
				List<ItemQuantityDTO> itemsDto = o.getItems();
				
				List<ItemQuantityDTO> restaurantOrderDetails = new ArrayList<>();
				
				for(ItemQuantityDTO dto: itemsDto) {
					if(dto.getRestaurantId() == restaurant.getRestaurantId()) {
						restaurantOrderDetails.add(dto);
						sum += dto.getCost() * dto.getOrderedQuantity();
					}
				}
				o.setTotalAmount(sum);
				o.setItems(restaurantOrderDetails);
				orders.add(o);
			}
		}
		
		if(orders.isEmpty()) throw new OrderDetailsException("Orders Not Found");
		
		return orders;
		
	}

	@Override
	public List<OrderDetails> viewAllOrdersByCustomer(String key) throws OrderDetailsException, CustomerException, LoginException {

		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Customer customer = customerRepo.findById(currentUserSession.getId()).orElseThrow(()-> new CustomerException("Please login as Customer"));
		
		List<OrderDetails> orders = customer.getOrders();
		
		if(orders.isEmpty()) throw new OrderDetailsException("You have not placed any orders");
		
		return orders;
		
	}
	
	@Override
	public List<OrderDetails> viewAllOrdersByRestaurantByCustomerId(String key, Integer customerId) throws OrderDetailsException, LoginException , RestaurantException, CustomerException{

		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		if(currentUserSession == null) throw new LoginException("Please login to place your order");
		Restaurant restaurant = restaurantRepo.findById(currentUserSession.getId()).orElseThrow(()-> new RestaurantException("Please login as Restaurant"));
		
		Set<Integer> customersIds = restaurant.getCustomers();
		
		List<Customer> customers = new ArrayList<>();
		for(Integer i : customersIds) {
			customers.add(customerRepo.findById(i).get());
		}

		Customer customer= null;
		for(Customer c: customers) {
			if(c.getCustomerID()==customerId) {
				customer = c;
			}
		}
		if(customer == null) throw new CustomerException("No orders found for this customers");
		
		List<OrderDetails> orders = new ArrayList<>();
		
		List<OrderDetails> temp = customer.getOrders();
		
		for(OrderDetails o: temp) {
			
			Double sum = 0.0;
			
			List<ItemQuantityDTO> itemsDto = o.getItems();
			
			List<ItemQuantityDTO> restaurantOrderDetails = new ArrayList<>();
			
			for(ItemQuantityDTO dto: itemsDto) {
				if(dto.getRestaurantId() == restaurant.getRestaurantId()) {
					restaurantOrderDetails.add(dto);
					sum += dto.getCost() * dto.getOrderedQuantity();
				}
			}
			o.setTotalAmount(sum);
			o.setItems(restaurantOrderDetails);
			orders.add(o);
			
		}
		
		if(orders.isEmpty()) throw new OrderDetailsException("Orders Not Found");
		
		return orders;
		
	}
}