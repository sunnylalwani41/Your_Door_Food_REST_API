package com.masai.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.DeliveryException;
import com.masai.exception.LoginException;
import com.masai.exception.OrderDetailsException;
import com.masai.model.CurrentUserSession;
import com.masai.model.OrderDetails;
import com.masai.repository.OrderDetailsRepo;
import com.masai.repository.SessionRepo;

@Service
public class DeliveryServiceImpl implements DeliveryService{
	
	@Autowired
	private OrderDetailsRepo orderDetailsRepo;
	
	@Autowired
	private SessionRepo sessionRepo;
	
	@Override
	public String getOrderDetails(String key, Integer orderId) throws DeliveryException, LoginException, OrderDetailsException {
		
		CurrentUserSession currentUserSession = sessionRepo.findByUuid(key);
		
		if(currentUserSession==null) throw new LoginException("Please login to get your order details...");
		
		Optional<OrderDetails> orderDetailsOpt = orderDetailsRepo.findById(orderId);
		
		if(orderDetailsOpt.isEmpty()) throw new OrderDetailsException("No order found...");
		
		OrderDetails orderDetails = orderDetailsOpt.get();
		
		if(currentUserSession.getId() != orderDetails.getCustomer().getCustomerID()) throw new OrderDetailsException("No order found with this order id: "+orderId);
		
		LocalDateTime deliverTime = orderDetails.getOrderDate().plusMinutes(20);
		
		if(LocalDateTime.now().isAfter(deliverTime)) {
			return "Your order was delivered on time";
		}
		
		String paymentStatus = orderDetails.getPaymentStatus().toString();
		
		String result = "Your order will be delivered at: "+ deliverTime.toLocalTime()  + " and your payment status is: "+ paymentStatus;
		
		return result;
	}

	
}
