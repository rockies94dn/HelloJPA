package com.proit.dto;

import com.proit.model.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

	private Long id;
	private String name;
	private Integer quantity;
	private Double price;
	private String description;
	private ProductStatus status;
	private String imageUrl;
	private Integer categoryId;

}
