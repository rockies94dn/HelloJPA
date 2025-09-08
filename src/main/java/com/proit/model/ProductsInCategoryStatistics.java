package com.proit.model;

import java.math.BigDecimal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsInCategoryStatistics {
	private String categoryName;
	private long productCount;
	private double totalPrice;
	private double averagePrice;
	private long totalQuantity;
}

