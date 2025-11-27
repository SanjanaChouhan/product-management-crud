package com.product.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.app.model.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, String> {

	boolean existsByNameIgnoreCase(String name);

	@Query("""
			    SELECT p FROM Product p
			    WHERE p.isDeleted = false
			      AND (
			            :keyword IS NULL
			            OR :keyword = ''
			            OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
			      )
			""")
	Page<Product> findActiveProducts(String keyword, Pageable pageable);

	boolean existsByNameIgnoreCaseAndIsDeletedFalse(String name);

}
