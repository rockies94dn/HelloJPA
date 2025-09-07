package com.proit.common;

import jakarta.servlet.http.HttpServletRequest;

public class PaginationUtils {
	public static final int DEFAULT_PAGE_SIZE = 5;

	public static int caculatePageCount(int totalItems, int pageSize) {
		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return (int) Math.ceil((double) totalItems / pageSize);
	}

	public static int caculateOffset(int pageNo, int pageSize) {
		if (pageNo < 0) {
			pageNo = 0;
		}

		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		return pageNo * pageSize;
	}

	public static PageInfor getPageFromRequest(HttpServletRequest request) {
		PageInfor pageInfo = new PageInfor();

		String pageNoStr = request.getParameter("page");
		String pageSizeStr = request.getParameter("size");
		int pageNo = 0;
		int pageSize = DEFAULT_PAGE_SIZE;

		if (pageNoStr != null && !pageNoStr.isEmpty()) {
			pageNo = Integer.parseInt(pageNoStr) - 1;
		}
		if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
			pageSize = Integer.parseInt(pageSizeStr);
		}

		pageInfo.setPageNo(pageNo);
		pageInfo.setPageSize(pageSize);
		return pageInfo;
	}

}
