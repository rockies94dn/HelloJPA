package com.proit.dto;

import com.proit.model.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatusSummary {
	private ProductStatus status;
	private Long count;

	public ProductStatusSummary(Integer status, Long count) {
		this.status = ProductStatus.values()[status];
		this.count = count;
	}

}
