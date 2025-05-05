package com.librarymanagement.service;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.data.entity.Author;
import com.librarymanagement.data.entity.Book;
import com.librarymanagement.mapper.BookMapper;
import com.librarymanagement.data.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookMapper mapper;

    @Test
    void testGetBookById() {
        Book mockBook = new Book(1L, "Some title", "123456789", 2024, new Author("Author"));
        BookDTO mockBookDTO = new BookDTO(1L, "Some title", "123456789", "Author", 2024);

        // Mocking repository and mapper behaviors
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));
        when(mapper.toDTO(mockBook)).thenReturn(mockBookDTO);

        BookDTO result = bookService.getBookById(1L);

        // Verifications
        assertEquals("Some title", result.getTitle());
        verify(bookRepository).findById(1L); // Ensures findById was called
        verify(mapper).toDTO(mockBook); // Ensures mapper was called
    }

}