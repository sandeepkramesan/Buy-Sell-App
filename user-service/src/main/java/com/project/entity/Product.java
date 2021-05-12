package com.project.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Product {

	private Integer productId;
	private String productName;
	private String productDesc;
	private String ownedByUser;

}
