package com.librarymanagement.dto;

import com.librarymanagement.data.entity.Book;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    @NotNull(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    private String name;
    private List<Book> books;
}
