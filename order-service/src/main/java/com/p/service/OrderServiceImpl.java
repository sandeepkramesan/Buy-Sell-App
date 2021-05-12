package com.p.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p.entity.Order;
import com.p.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order saveOrderDetails(Order order) {
		Order saveOrder = order;
//		saveOrder.setStatus("SELL");
		return orderRepository.save(saveOrder);
	}

	@Override
	public Order searchByOrderId(Long Order_Id) {

		Optional<Order> findById = orderRepository.findById(Order_Id);
		if (findById.isPresent())
			return findById.get();

		return null;
	}

	@Override
	public List<Order> getAllOrders() {

		return orderRepository.findAll();
	}

	@Override
	public List<Order> searchByUsername(String username) {
		List<Order> findByUserName = orderRepository.findByUserName(username);
		return findByUserName;
	}

	@Override
	public List<Order> searchByProductName(String productname) {
		List<Order> findByProductName = orderRepository.findByProductName(productname);
		return findByProductName;
	}

	@Override
	public Order deleteByOrderId(Long Order_Id) {
		// TODO Auto-generated method stub
		return null;
	}

}