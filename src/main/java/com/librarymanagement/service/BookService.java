package com.librarymanagement.service;

import com.librarymanagement.data.entity.Author;
import com.librarymanagement.data.entity.Book;
import com.librarymanagement.data.repository.AuthorRepository;
import com.librarymanagement.data.repository.BookRepository;
import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.exception.DuplicateResourceException;
import com.librarymanagement.exception.ResourceNotFoundException;
import com.librarymanagement.mapper.BookMapper;
import com.librarymanagement.specification.BookSpecification;
import com.librarymanagement.util.Messages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    public Page<BookDTO> getAllBooks(String title, String authorName, Pageable pageable) {
        Page<Book> books;

        if (title != null && authorName != null) {
            books = bookRepository.findByTitleAndAuthorName(title, authorName, pageable);
        } else if (title != null) {
            books = bookRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (authorName != null) {
            books = bookRepository.findByAuthorNameContainingIgnoreCase(authorName, pageable);
        } else {
            books = bookRepository.findAll(pageable);
        }
        // Convert to DTOs
        return books.map(mapper::toDTO);
    }

    public Page<BookDTO> searchBooks(String title, String authorName, Integer year, Pageable pageable) {
        Specification<Book> spec = Specification.where(BookSpecification.hasTitle(title))
                .and(BookSpecification.hasAuthorName(authorName))
                .and(year != null ? BookSpecification.publishedAfter(year) : null);

        Page<Book> books = bookRepository.findAll(spec, pageable);
        return books.map(mapper::toDTO);
    }


    @Transactional
    public BookDTO addBook(BookDTO bookDTO) throws DuplicateResourceException {
        Book book = validateAddBook(bookDTO);
        book = bookRepository.save(book);
        return mapper.toDTO(book);
    }

    private Book validateAddBook(BookDTO bookDTO) throws DuplicateResourceException, ResourceNotFoundException {
        Author author = authorRepository.findByName(bookDTO.getAuthorName().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(Messages.AUTHOR_NOT_FOUND, bookDTO.getAuthorName())));

        if (bookRepository.existsByTitleAndAuthor(bookDTO.getTitle(), author)) {
            throw new DuplicateResourceException(Messages.BOOK_ALREADY_EXIST);
        }

        return mapper.toEntity(bookDTO, author);
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(Messages.BOOK_NOT_FOUND, id)));
        return mapper.toDTO(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(Messages.BOOK_NOT_FOUND, id)));

        bookRepository.delete(book);
    }

    @Transactional
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
