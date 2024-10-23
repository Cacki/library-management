package com.librarymanagement.mapper;

import com.librarymanagement.dto.BookDTO;
import com.librarymanagement.entity.Author;
import com.librarymanagement.entity.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "author.name", target = "authorName")
    BookDTO toDTO(Book book);

    @Mapping(source = "authorName", target = "author.name")
    Book toEntity(BookDTO bookDTO, Author author);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDTO(BookDTO bookDTO, @MappingTarget Book book);
}
