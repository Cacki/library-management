package com.librarymanagement.mapper;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.data.entity.Author;
import com.librarymanagement.data.entity.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "author.name", target = "authorName")
    BookDTO toDTO(Book book);

    @Mapping(source = "authorName", target = "author.name")
    default Book toEntity(BookDTO bookDTO, Author author) {
        Book book = new Book();
        author.setName(bookDTO.getAuthorName());
        book.setAuthor(author);
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setPublicationYear(bookDTO.getPublicationYear());
        return book;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDTO(BookDTO bookDTO, @MappingTarget Book book);
}
