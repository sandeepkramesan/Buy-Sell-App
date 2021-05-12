package com.p.service;

import java.util.List;

import com.p.entity.Order;

public interface OrderService {

	public Order saveOrderDetails(Order Order);

	public Order searchByOrderId(Long Order_Id);

	public Order deleteByOrderId(Long Order_Id);

	public List<Order> searchByUsername(String username);

	public List<Order> searchByProductName(String productname);

	List<Order> getAllOrders();

}
