package com.product.app.payload.request;


import com.product.app.util.AppConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationRequest {

	@NotNull(message = AppConstants.PAGE_SIZE_REQUIRED)
	private Integer pageSize;

	@NotNull(message = AppConstants.PAGE_INDEX_REQUIRED)
	private Integer pageNumber;

	@Pattern(regexp = AppConstants.SORT_ORDER_PATTERN, message = AppConstants.SORT_ORDER_PATTERN_MESSAGE)
	@Builder.Default
	private String sortDirection = "DESC";

	private String sortKey;
	private String searchValue;

}
