package com.librarymanagement.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    @NotNull(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    private String title;
    @NotNull(message = "ISBN is required")
    private String isbn;
    @NotNull(message = "Author name is required")
    @Size(min = 2, message = "Author name should have at least 2 characters")
    private String authorName;
    @Min(value = 1450, message = "Publication year must be greater than 1450")
    @Max(value = 2024, message = "Publication year must be less than or equal to the current year")
    private int publicationYear;
}
