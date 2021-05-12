package com.ust.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ust.entity.Product;
import com.ust.entity.User;
import com.ust.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Lets Members Add/ Post/ Sell Product
	 */
	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {

		ResponseEntity<User[]> forEntity = restTemplate.getForEntity("http://USER-SERVICE/user/all", User[].class);
		User[] users = forEntity.getBody();

		long count = 0;

		for (User user : users) {
			log.warn(user.getUsername());

			if (user.getUsername().equals(product.getOwnedByUser()))
				count = 1;
		}

		if (count == 1) {
			Product newProduct = service.addNewProduct(product);
			if (newProduct == null) {
				return new ResponseEntity<>("Error in adding product", HttpStatus.CONFLICT);
			} else {
				org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<Product> httpEntity = new HttpEntity<>(newProduct, headers);

				restTemplate.postForObject("http://ORDER-SERVICE/orders/add/sell", httpEntity, Product.class);

				return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
			}
		}

		return new ResponseEntity<>("Register Member First", HttpStatus.CONFLICT);

	}

	/**
	 * View All Products
	 */
	@GetMapping("/all")
	public ResponseEntity<?> getAllProducts() {
		List<Product> allproduct = service.getAllProduct();
		if (allproduct.isEmpty()) {
			return new ResponseEntity<>("No Product(s) Available", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(allproduct, HttpStatus.OK);
		}

	}

	/**
	 * View All Products by User Who Owns it.
	 */
	@GetMapping("/{name}")
	public List<Product> getProductByUsername(@PathVariable("name") String username) {
		List<Product> productByUsername = service.getProductByUsername(username);

		return productByUsername;

	}

	/**
	 * Delete Products By Id
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(name = "id") int productId) {
		Product deleteById = service.deleteById(productId);
		if (deleteById != null) {
			return new ResponseEntity<>("ProductId" + productId + "deleted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("ProductId" + productId + "not found", HttpStatus.OK);
		}
	}

	/**
	 * Update Product By Id
	 */
	@PutMapping("/update")
	public ResponseEntity<?> updateById(@RequestBody Product prod) {

		Product productById = service.productById(prod.getProductId());

		if (productById.getOwnedByUser().equals(prod.getOwnedByUser())) {
			Product updatedProduct = service.updateProduct(prod);
			if (updatedProduct != null) {
				return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("No Product with that Id", HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<>("The product cant be updated by " + prod.getOwnedByUser() + " NOT HIS PRODUCT",
				HttpStatus.CONFLICT);
	}

	/**
	 * Lets Members Buy a Product
	 */
	@PutMapping("/buy")
	public ResponseEntity<?> buyProduct(@RequestBody Product prod) {

		ResponseEntity<User[]> forEntity = restTemplate.getForEntity("http://USER-SERVICE/user/all", User[].class);
		User[] users = forEntity.getBody();

		long count = 0;

		for (User user : users) {
			log.warn(user.getUsername());

			if (user.getUsername().equals(prod.getOwnedByUser()))
				count = 1;
		}

		if (count == 1) {

			Product productById = service.productById(prod.getProductId());

			if (productById.getOwnedByUser().equals(prod.getOwnedByUser())) {
				return new ResponseEntity<>(prod.getOwnedByUser() + " Owns this Product. Cant Buy!!",
						HttpStatus.CONFLICT);
			} else {
				Product updatedProduct = service.buyProduct(prod);
				if (updatedProduct != null) {

					org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity<Product> httpEntity = new HttpEntity<>(updatedProduct, headers);

					restTemplate.postForObject("http://ORDER-SERVICE/orders/add/buy", httpEntity, Product.class);

					return new ResponseEntity<>(updatedProduct, HttpStatus.CREATED);
				} else {
					return new ResponseEntity<>("No Product with that Id", HttpStatus.CONFLICT);
				}
			}
		}
		return new ResponseEntity<>("Register Member First", HttpStatus.CONFLICT);

	}
}