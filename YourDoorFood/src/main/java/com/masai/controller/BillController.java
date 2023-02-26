package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.BillException;
import com.masai.exception.CustomerException;
import com.masai.exception.LoginException;
import com.masai.model.Bill;
import com.masai.model.DateDTO;
import com.masai.service.BillService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/YourDoorFood")
public class BillController {
	@Autowired
	private BillService billService;
	
	@GetMapping("/bills/{loginId}/{billId}")
	public ResponseEntity<Bill> viewBillHandler(@PathVariable("loginId") String key, @PathVariable("billId") Integer billId) throws BillException, CustomerException, LoginException{
		Bill bill= billService.viewBill(key, billId);
		return new ResponseEntity<Bill>(bill, HttpStatus.ACCEPTED);
	}
	
	
	@PostMapping("/bills/{loginId}")
	public ResponseEntity<List<Bill>> viewBillBetweenDateHandler(@PathVariable("loginId") String key, @Valid @RequestBody DateDTO dateDTO) throws BillException, CustomerException, LoginException{
		List<Bill> bills= billService.viewBill(key, dateDTO);
		return new ResponseEntity<List<Bill>>(bills, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/bills/{loginId}")
	public ResponseEntity<List<Bill>> viewAllBillHandler(@PathVariable("loginId") String key) throws BillException, LoginException, CustomerException{
		List<Bill> bills= billService.viewBills(key);
		return new ResponseEntity<List<Bill>>(bills, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getTotalAmountOfbills/{loginId}/{billId}")
	public ResponseEntity<Double> getTotalCostOfBillHandler(@PathVariable("loginId") String key, @PathVariable("billId") Integer billId) throws BillException, CustomerException, LoginException{
		Double totalCost= billService.getTotalCost(key, billId);
		return new ResponseEntity<Double>(totalCost, HttpStatus.ACCEPTED);
	}
}
