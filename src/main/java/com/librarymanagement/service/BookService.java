package com.librarymanagement.service;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.entity.Author;
import com.librarymanagement.entity.Book;
import com.librarymanagement.exception.ResourceNotFoundException;
import com.librarymanagement.mapper.BookMapper;
import com.librarymanagement.repository.AuthorRepository;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository repository, BookMapper mapper, AuthorRepository authorRepository) {
        this.bookRepository = repository;
        this.mapper = mapper;
        this.authorRepository = authorRepository;
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public BookDTO addBook(BookDTO bookDTO) {
        Author author = authorRepository.findByName(bookDTO.getAuthorName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(Messages.AUTHOR_NOT_FOUND, bookDTO.getAuthorName())));

        Book book = mapper.toEntity(bookDTO, author);
        book = bookRepository.save(book);
        return mapper.toDTO(book);
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(Messages.BOOK_NOT_FOUND, id)));
        return mapper.toDTO(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(Messages.BOOK_NOT_FOUND, id)));

        bookRepository.delete(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(Messages.BOOK_NOT_FOUND, id)));

        if (bookDTO.getAuthorName() != null) {
            Author author = authorRepository.findByName(bookDTO.getAuthorName())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format(Messages.AUTHOR_NOT_FOUND, bookDTO.getAuthorName())));
            book.setAuthor(author);
        }
        mapper.updateBookFromDTO(bookDTO, book);
        book = bookRepository.save(book);
        return mapper.toDTO(book);
    }
}
