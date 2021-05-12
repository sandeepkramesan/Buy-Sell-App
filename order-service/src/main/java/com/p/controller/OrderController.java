package com.p.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.p.entity.Order;
import com.p.entity.Product;
import com.p.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	public OrderService orderService;

	/**
	 * Used By Product-Service for PostForObject
	 */
	@PostMapping("/add/buy")
	public ResponseEntity<Order> addNewBuyOrder(@RequestBody Product product) {
		Order order = new Order();
		order.setProductName(product.getProductName());
		order.setUserName(product.getOwnedByUser());
		order.setStatus("BUY");
		Order saveOrderDetails = orderService.saveOrderDetails(order);

		return new ResponseEntity<Order>(saveOrderDetails, HttpStatus.CREATED);

	}

	/**
	 * Used By Product-Service for PostForObject
	 */
	@PostMapping("/add/sell")
	public ResponseEntity<Order> addNewSellOrder(@RequestBody Product product) {
		Order order = new Order();
		order.setProductName(product.getProductName());
		order.setUserName(product.getOwnedByUser());
		order.setStatus("SELL");
		Order saveOrderDetails = orderService.saveOrderDetails(order);

		return new ResponseEntity<Order>(saveOrderDetails, HttpStatus.CREATED);

	}

	/**
	 * To View All Orders Made / Order History
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<Object> getAllOrder() {
		List<Order> allOrder = orderService.getAllOrders();
		if (allOrder.isEmpty()) {
			return new ResponseEntity<>("No Orders Found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(allOrder, HttpStatus.OK);
		}
	}

	/**
	 * View Order By Its Id
	 */
	@GetMapping(path = "/order/{id}")
	public ResponseEntity<?> searchByOrderId(@PathVariable("id") Long Order_Id) {
		Order searchByOrderId = orderService.searchByOrderId(Order_Id);
		if (searchByOrderId == null) {
			return new ResponseEntity<>("Order Id does not exist!", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(searchByOrderId, HttpStatus.OK);
	}

	/**
	 * View Order By Name Of Product
	 */
	@GetMapping(path = "/product/{name}")
	public ResponseEntity<?> searchByProductName(@PathVariable("name") String productname) {
		List<Order> searchByProductName = orderService.searchByProductName(productname);
		if (searchByProductName.isEmpty()) {
			return new ResponseEntity<>("No order(s) in that product name!", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(searchByProductName, HttpStatus.OK);
	}

	/**
	 * View Order By Name Of Member Who Owns It
	 */
	@GetMapping(path = "/username/{name}")
	public ResponseEntity<?> searchByOrderId(@PathVariable("name") String username) {
		List<Order> searchByUsername = orderService.searchByUsername(username);
		if (searchByUsername.isEmpty()) {
			return new ResponseEntity<>("No order(s) for that User", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(searchByUsername, HttpStatus.OK);
	}

}
