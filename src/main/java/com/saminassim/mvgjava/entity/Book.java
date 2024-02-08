package com.saminassim.mvgjava.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID _id;
    private UUID userId;
    private String title;
    private String author;
    private String imageUrl;
    private Integer year;
    private String genre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<BookRating> ratings;
    private Integer averageRating;

}
