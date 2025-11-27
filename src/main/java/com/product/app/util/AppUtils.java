package com.product.app.util;

import java.util.Arrays;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.product.app.exception.BadRequestException;
import com.product.app.payload.request.PaginationRequest;
import com.product.app.payload.response.ApiResponse;

@Component
public class AppUtils {

	/**
	 * Builds Pageable object using PaginationRequest.
	 */
	public static Pageable buildPageableRequest(PaginationRequest pageRequest, Class<?> entityClass) {

		String sortKey = pageRequest.getSortKey();

		// Validate sort key
		if (sortKey != null && !sortKey.isBlank()) {

			if (!validateSortKey(entityClass, sortKey)) {
				throw new BadRequestException("Invalid sort key: " + sortKey);
			}

		} else {
			// DEFAULT SORT KEY → name (because your entity does NOT contain createdDate)
			sortKey = "name";
		}

		// Sort Direction
		Sort.Direction direction = Sort.Direction.DESC;
		String dirValue = pageRequest.getSortDirection();
		if (pageRequest.getSortDirection() != null && !pageRequest.getSortDirection().isBlank()) {
			try {
				direction = Sort.Direction.valueOf(dirValue.toUpperCase());
			} catch (IllegalArgumentException ignored) {
				throw new BadRequestException("Invalid sort direction: " + dirValue);
			}
		}

		return PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.by(direction, sortKey));
	}

	/**
	 * Validates if sortKey exists inside entity fields
	 */
	public static boolean validateSortKey(Class<?> modelClass, String sortKey) {
		Class<?> currentClass = modelClass;

		while (currentClass != null) {

			boolean exists = Arrays.stream(currentClass.getDeclaredFields())
					.anyMatch(field -> field.getName().equals(sortKey));

			if (exists)
				return true;

			currentClass = currentClass.getSuperclass();
		}
		return false;
	}

	// Generic reusable method
	public static ApiResponse buildResponse(boolean success, String message, HttpStatus status, Object data) {
		return ApiResponse.builder().success(success).message(message).http(status).statusCode(status.value())
				.data(data).build();
	}

	// Overloads for common use cases
	public static ApiResponse buildSuccessResponse(String message) {
		return buildResponse(true, message, HttpStatus.OK, null);
	}

	public static ApiResponse buildSuccessResponse(String message, Object data) {
		return buildResponse(true, message, HttpStatus.OK, data);
	}

	public static ApiResponse buildCreatedResponse(String message, Object data) {
		return buildResponse(true, message, HttpStatus.CREATED, data);
	}

	public static ApiResponse buildCreatedResponse(String message) {
		return buildResponse(true, message, HttpStatus.CREATED, null);
	}

	public static ApiResponse buildFailureResponse(String message) {
		return buildResponse(false, message, HttpStatus.BAD_REQUEST, null);
	}

	public static ApiResponse buildFailureResponse(String message, HttpStatus status) {
		return buildResponse(false, message, status, null);
	}

}
