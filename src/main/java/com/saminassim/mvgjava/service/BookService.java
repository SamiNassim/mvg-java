package com.saminassim.mvgjava.service;

import com.saminassim.mvgjava.dto.BookRatingRequest;
import com.saminassim.mvgjava.dto.BookRequest;
import com.saminassim.mvgjava.entity.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {

    ResponseEntity<String> createBook(BookRequest bookRequest);
    List<Book> getAllBooks();
    Optional<Book> getOneBook(UUID bookId);
    List<Book> getBestRating();
    void deleteBook(UUID bookId);
    Book createRating(UUID bookId, BookRatingRequest rating);
}
