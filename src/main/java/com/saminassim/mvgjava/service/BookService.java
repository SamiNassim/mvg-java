package com.saminassim.mvgjava.service;

import com.saminassim.mvgjava.dto.BookRequest;
import com.saminassim.mvgjava.entity.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    ResponseEntity<String> createBook(BookRequest bookRequest);
    List<Book> getAllBooks();
    Optional<Book> getOneBook(Long bookId);
    List<Book> getBestRating();
    Boolean deleteBook(Long bookId);
}
