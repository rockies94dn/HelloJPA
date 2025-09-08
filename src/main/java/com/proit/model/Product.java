package com.proit.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
@NamedQuery(name = "Product.findByStatus", query = "SELECT p FROM Product p WHERE p.status = :status")
@NamedQuery(name = "Product.countByStatus", query = "SELECT COUNT(p) FROM Product p WHERE p.status = :status")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "NVARCHAR(255)", nullable = false, length = 100)
	private String name;
	private Integer quantity;
	private Double price;
	@Column(nullable = true, length = 500)
	private String description;
	@Column(length = 150)
	private String imageUrl;
	private ProductStatus status;
	@Temporal(TemporalType.DATE)
	private Date enteredDate;

	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Category category;

}
