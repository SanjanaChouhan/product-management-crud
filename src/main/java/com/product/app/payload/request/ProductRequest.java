package com.product.app.payload.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.product.app.util.AppConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

	@NotBlank(message = PRODUCT_NAME_NOT_BLANK)
	@Size(min = 2, max = 100, message = PRODUCT_NAME_SIZE)
	private String name;

	@Size(max = 500, message = PRODUCT_DESC_MAX)
	private String description;

	@NotNull(message = PRODUCT_PRICE_REQUIRED)
	@DecimalMin(value = "0.1", message = PRODUCT_PRICE_MIN)
	private BigDecimal price;
}
