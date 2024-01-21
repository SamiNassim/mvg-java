package com.saminassim.mvgjava.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String title;
    private String author;
    private String imageUrl;
    private Integer year;
    private String genre;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<BookRating> rating;
    private Integer averageRating;

}