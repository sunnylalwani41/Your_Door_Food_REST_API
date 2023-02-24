package com.masai.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.BillException;
import com.masai.model.Bill;
import com.masai.repository.BillRepo;

@Service
public class BillServiceImpl implements BillService{
	
	@Autowired
	private BillRepo billRepo;

	@Override
	public Bill addBill(Bill bill) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill updateBill(Bill bill) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill removeBill(Bill bill) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bill viewBill(Bill bill) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bill> viewBill(LocalDate startDate, LocalDate endDate) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bill> viewBills(int customerId) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double calCulateTotalCost(Bill bill) throws BillException {
		// TODO Auto-generated method stub
		return null;
	}
}
