package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.BillException;
import com.masai.model.Bill;
import com.masai.repository.BillRepo;

import java.time.LocalDate;
import java.util.List;



public interface BillService {
	
	
	
	public Bill addBill(Bill bill) throws BillException;
	
	public Bill updateBill(Bill bill) throws BillException;
	
	public Bill removeBill(Bill bill) throws BillException;
	
	public Bill viewBill(Bill bill) throws BillException;
	
	public List<Bill> viewBill(LocalDate startDate, LocalDate endDate) throws BillException;
	
	public List<Bill> viewBills(int customerId) throws BillException;
	
	public Double calCulateTotalCost(Bill bill) throws BillException;
}
