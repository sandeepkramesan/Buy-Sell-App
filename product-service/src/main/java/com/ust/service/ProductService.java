package com.ust.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ust.entity.Product;
import com.ust.repositorty.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Product addNewProduct(Product product) {
		return repository.save(product);
	}

	public List<Product> getAllProduct() {
		return repository.findAll();
	}

	public Product productById(int id) {
		Optional<Product> findById = repository.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;

	}

	public List<Product> getProductByUsername(String username) {
		List<Product> findByPostedByUser = repository.findByOwnedByUser(username);
		if (!findByPostedByUser.isEmpty())
			return findByPostedByUser;

		return null;
	}

	public Product deleteById(int productId) {
		Optional<Product> findById = repository.findById(productId);

		if (findById.isPresent()) {
			Product deletedProduct = findById.get();
			repository.deleteById(productId);
			return deletedProduct;
		}
		return null;
	}

	public Product updateProduct(Product product) {
		Product updatedProduct = new Product();
		Optional<Product> findById = repository.findById(product.getProductId());

		if (findById.isPresent()) {
			updatedProduct.setProductId(findById.get().getProductId());
			updatedProduct.setProductName(product.getProductName());
			updatedProduct.setProductDesc(product.getProductDesc());
			return repository.save(updatedProduct);
		}

		return null;
	}

	public Product buyProduct(Product product) {
		Product updatedProduct = new Product();
		Optional<Product> findById = repository.findById(product.getProductId());

		if (findById.isPresent()) {
			updatedProduct = findById.get();
			updatedProduct.setOwnedByUser(product.getOwnedByUser());
			return repository.save(updatedProduct);
		}

		return null;
	}

}
