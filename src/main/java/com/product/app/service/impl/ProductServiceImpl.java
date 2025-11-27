package com.product.app.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.app.exception.ResourceNotFoundException;
import com.product.app.model.Product;
import com.product.app.payload.request.PaginationRequest;
import com.product.app.payload.request.ProductRequest;
import com.product.app.payload.response.ApiResponse;
import com.product.app.payload.response.PaginatedResponse;
import com.product.app.payload.response.ProductResponse;
import com.product.app.repository.IProductRepository;
import com.product.app.service.IProductService;
import com.product.app.util.AppConstants;
import com.product.app.util.AppUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

	private final IProductRepository productRepository;

	@Override
	public ApiResponse createProduct(ProductRequest request) {

		if (productRepository.existsByNameIgnoreCaseAndIsDeletedFalse(request.getName())) {
			return AppUtils.buildFailureResponse("Product with name '" + request.getName() + "' already exists",
					HttpStatus.BAD_REQUEST);
		}

		Product saved = productRepository.save(Product.builder().name(request.getName())
				.description(request.getDescription()).price(request.getPrice()).isDeleted(false).build());

		return AppUtils.buildCreatedResponse(AppConstants.PRODUCT_CREATED, mapToResponse(saved));
	}

	@Override
	public ApiResponse updateProduct(String id, ProductRequest request) {
		Product existing = getActiveProductOrThrow(id.toString());

		boolean nameExistsForAnother = productRepository.existsByNameIgnoreCaseAndIsDeletedFalse(request.getName())
				&& !existing.getName().equalsIgnoreCase(request.getName());

		if (nameExistsForAnother) {
			return AppUtils.buildFailureResponse("Another product with name '" + request.getName() + "' already exists",
					HttpStatus.BAD_REQUEST);
		}

		existing.setName(request.getName());
		existing.setDescription(request.getDescription());
		existing.setPrice(request.getPrice());

		Product updated = productRepository.save(existing);

		return AppUtils.buildSuccessResponse(AppConstants.PRODUCT_UPDATED, mapToResponse(updated));
	}

	@Override
	public ApiResponse getProductById(String id) {
		Product product = getActiveProductOrThrow(id.toString());

		return AppUtils.buildSuccessResponse(AppConstants.PRODUCT_FETCHED, mapToResponse(product));
	}

	@Override
	public PaginatedResponse<ProductResponse> getAllProducts(PaginationRequest request) {

		Pageable pageable = AppUtils.buildPageableRequest(request, Product.class);

		String keyword = (request.getSearchValue() == null) ? null : request.getSearchValue().trim();

		Page<Product> page = productRepository.findActiveProducts(keyword, pageable);

		Page<ProductResponse> mapped = page.map(this::mapToResponse);

		return new PaginatedResponse<>(mapped);
	}

	@Override
	public ApiResponse deleteProduct(String id) {
		Product existing = getActiveProductOrThrow(id);

		existing.setIsDeleted(true);
		productRepository.save(existing);

		return AppUtils.buildSuccessResponse(AppConstants.PRODUCT_DELETED);
	}

	private Product getActiveProductOrThrow(String id) {
		return productRepository.findById(id).filter(p -> !p.getIsDeleted())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND + id));
	}

	private ProductResponse mapToResponse(Product product) {
		return ProductResponse.builder().id(product.getId()).name(product.getName())
				.description(product.getDescription()).price(product.getPrice()).build();
	}

}
