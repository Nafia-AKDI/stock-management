package com.hahn.stock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Category DTO")
public class CategoryDto {
    @Schema(description = "Unique identifier of the category", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Name of the category", example = "Electronics", required = true)
    private String name;

    @Schema(description = "Description of the category", example = "All electronic devices")
    private String description;

    @Schema(description = "Date when the category was created", example = "2025-01-01T00:00:00")
    private LocalDateTime createdDate;

    @Schema(description = "Date when the category was last updated", example = "2025-01-02T00:00:00")
    private LocalDateTime updatedDate;

    @Schema(description = "Number of products in this category", example = "15")
    private Integer productCount;
}