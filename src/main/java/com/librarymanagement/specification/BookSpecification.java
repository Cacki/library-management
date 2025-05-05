package com.librarymanagement.specification;

import com.librarymanagement.data.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title != null ? criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%") : null;
    }

    public static Specification<Book> hasAuthorName(String authorName) {
        return (root, query, criteriaBuilder) ->
                authorName != null ? criteriaBuilder.like(criteriaBuilder.lower(root.join("author").get("name")), "%" + authorName.toLowerCase() + "%") : null;
    }

    public static Specification<Book> publishedAfter(int year) {
        return (root, query, criteriaBuilder) ->
                year > 0 ? criteriaBuilder.greaterThanOrEqualTo(root.get("publicationYear"), year) : null;
    }
}
