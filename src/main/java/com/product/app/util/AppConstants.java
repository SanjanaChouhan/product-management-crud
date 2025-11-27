package com.product.app.util;

public final class AppConstants {
	// ========== PAGINATION & S ORTING==========
	public static final String PAGE_SIZE = "pageSize";
	public static final String PAGE_NUMBER = "pageNumber";
	public static final String SORT_DIRECTION = "sortDirection";
	public static final String SORT_KEY = "sortKey";
	public static final String SEARCH_VALUE = "searchValue";
	public static final String SORT_ASC = "ASC";
	public static final String SORT_DESC = "DESC";
	public static final String SORT_ORDER_PATTERN = "ASC|DESC|asc|desc";
	public static final String SORT_ORDER_PATTERN_MESSAGE = "Sort order must be either 'ASC' or 'DESC'";

	public static final String PAGE_SIZE_REQUIRED = "Page size is required";
	public static final String PAGE_INDEX_REQUIRED = "Page index is required";

	// ========== COMMON ==========
	public static final String ID = "id";
	public static final String ACCESS_DENIED = "Access denied.";
	public static final String UNAUTHORIZED_ACCESS = "Unauthorized access.";

	// ========== MESSAGES ==========
	public static final String PRODUCT_CREATED = "Product created successfully";
	public static final String PRODUCT_UPDATED = "Product updated successfully";
	public static final String PRODUCT_DELETED = "Product deleted successfully";
	public static final String PRODUCT_FETCHED = "Product details fetched";
	public static final String PRODUCT_LIST_FETCHED = "Product list fetched";
	public static final String PRODUCT_NOT_FOUND = "Product not found or deleted with id ";

	// ========== PRODUCT VALIDATION ==========
	public static final String PRODUCT_NAME_NOT_BLANK = "Name cannot be blank";
	public static final String PRODUCT_NAME_SIZE = "Name must be between 2 and 100 characters";
	public static final String PRODUCT_DESC_MAX = "Description cannot exceed 500 characters";
	public static final String PRODUCT_PRICE_REQUIRED = "Price is required";
	public static final String PRODUCT_PRICE_MIN = "Price must be greater than 0";

	// ========== ERROR KEYS ==========
	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String STATUS = "status";

	// ========== EXCEPTION MESSAGES ==========
	public static final String VALIDATION_FAILED = "Validation failed.";
	public static final String REQUEST_BODY_MISSING = "Required request body is missing.";
	public static final String METHOD_NOT_ALLOWED = "Method not allowed.";

}
