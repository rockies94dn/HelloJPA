package com.proit.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, columnDefinition = "NVARCHAR(50)")
	private String name;

	@Column(nullable = false, unique = true, length = 150)
	private String email;

	@Column(nullable = false, length = 30)
	private String password;
	
	private LocalDate createAt;
	
	private LocalDate updateAt;
	
	private LocalDate lastLoginAt;
	
	@Enumerated()
	private UserRole role;

	@Enumerated()
	private UserStatus status;
	
	@PrePersist
	private void prePersist() {
		this.createAt = LocalDate.now();
	}
	
	@PreUpdate
	private void preUpdate() {
		this.updateAt = LocalDate.now();
		if (this.lastLoginAt == null) {
			this.lastLoginAt = LocalDate.now();
		}
	}
}
