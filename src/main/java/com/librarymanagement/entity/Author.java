package com.librarymanagement.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "authors")
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author") // One author can write many books
    private List<Book> books;
}
