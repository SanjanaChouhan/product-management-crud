package com.product.app.service;

import com.product.app.payload.request.PaginationRequest;
import com.product.app.payload.request.ProductRequest;
import com.product.app.payload.response.ApiResponse;
import com.product.app.payload.response.PaginatedResponse;
import com.product.app.payload.response.ProductResponse;

public interface IProductService {

	ApiResponse createProduct(ProductRequest request);

	ApiResponse updateProduct(String id, ProductRequest request);

	ApiResponse getProductById(String id);

	PaginatedResponse<ProductResponse> getAllProducts(PaginationRequest request);

	ApiResponse deleteProduct(String id);
}
