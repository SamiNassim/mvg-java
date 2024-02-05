package com.saminassim.mvgjava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long userId;
    private String title;
    private String author;
    private String imageUrl;
    private Integer year;
    private String genre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    @JsonIgnore
    private List<BookRating> ratings;
    private Integer averageRating;

}
