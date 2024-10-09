package com.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;
    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

}
