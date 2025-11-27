package com.product.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.product.app.payload.request.PaginationRequest;
import com.product.app.payload.request.ProductRequest;
import com.product.app.payload.response.ApiResponse;
import com.product.app.payload.response.PaginatedResponse;
import com.product.app.payload.response.ProductResponse;
import com.product.app.service.IProductService;
import com.product.app.util.AppConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

	private final IProductService productService;

	@PostMapping
	public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductRequest request) {
		ApiResponse response = productService.createProduct(request);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable(name = AppConstants.ID) String id,
			@Valid @RequestBody ProductRequest request) {
		ApiResponse response = productService.updateProduct(id, request);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable(name = AppConstants.ID) String id) {
		ApiResponse response = productService.getProductById(id);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

	@GetMapping("/pagination")
	public ResponseEntity<PaginatedResponse<ProductResponse>> getAllProducts(
			@RequestParam(name = AppConstants.PAGE_SIZE) Integer pageSize,
			@RequestParam(name = AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name = AppConstants.SORT_DIRECTION, defaultValue = AppConstants.SORT_DESC, required = false) String sortDirection,
			@RequestParam(name = AppConstants.SORT_KEY, required = false) String sortKey,
			@RequestParam(name = AppConstants.SEARCH_VALUE, required = false) String searchValue) {

		PaginationRequest request = PaginationRequest.builder().pageSize(pageSize).pageNumber(pageNumber)
				.sortDirection(sortDirection).sortKey(sortKey).searchValue(searchValue).build();

		PaginatedResponse<ProductResponse> response = productService.getAllProducts(request);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable(name = AppConstants.ID) String id) {
		ApiResponse response = productService.deleteProduct(id);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
