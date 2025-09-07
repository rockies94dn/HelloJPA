package com.proit.common;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.proit.dto.ProductDto;
import com.proit.dto.ProductViewDto;
import com.proit.model.Category;
import com.proit.model.Product;

/*
 * Chuyển đổi đối tượng từ Entity sang DTO và ngược lại
*/
public class ProductMapper {
	
	public static ProductDto toDto(Product entity) {
		if (entity == null) {
			return null;
		}
		ProductDto dto = new ProductDto();
		try {
			BeanUtils.copyProperties(dto, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getCategoryId() : null);

		return dto;
	}

	public static Product toEntity(ProductDto dto) {
		if (dto == null) {
			return null;
		}
		Product entity = new Product();
		try {
			BeanUtils.copyProperties(entity, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		if (dto.getCategoryId() != null) {
			Category category = new Category();
			category.setCategoryId(dto.getCategoryId());
			entity.setCategory(category);
		}

		return entity;
	}

	public static ProductViewDto toViewDto(Product entity) {
		if (entity == null) {
			return null;
		}
		ProductViewDto dto = new ProductViewDto();
		try {
			BeanUtils.copyProperties(dto, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getCategoryId() : null);

		return dto;
	}

	public static Product toEntity(ProductViewDto dto) {
		if (dto == null) {
			return null;
		}
		Product entity = new Product();
		try {
			BeanUtils.copyProperties(entity, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		if (dto.getCategoryId() != null) {
			Category category = new Category();
			category.setCategoryId(dto.getCategoryId());
			entity.setCategory(category);
		}

		return entity;
	}

	public static List<ProductDto> toDtoList(List<Product> entities) {
		if (entities == null || entities.isEmpty()) {
			return List.of();
		}

		return entities.stream().map(entity -> {
			try {
				return toDto(entity);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}).toList();
	}

	public static List<ProductViewDto> toViewDtoList(List<Product> entities) {
		if (entities == null || entities.isEmpty()) {
			return List.of();
		}

		return entities.stream().map(entity -> {
			try {
				return toViewDto(entity);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}).toList();
	}

	public static List<Product> toEntitiesList(List<ProductDto> dtos) {
		if (dtos == null || dtos.isEmpty()) {
			return List.of();
		}

		return dtos.stream().map(dto -> {
			try {
				return toEntity(dto);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}).toList();
	}

	public static List<Product> toEntitiesListFormViewDtos(List<ProductViewDto> dtos) {
		if (dtos == null || dtos.isEmpty()) {
			return List.of();
		}

		return dtos.stream().map(dto -> {
			try {
				return toEntity(dto);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}).toList();
	}

}
