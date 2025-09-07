package com.proit.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfor {
	private int pageNo;
	private int pageSize;
	private int totalItems;
	private int totalPages;
}