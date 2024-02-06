package com.saminassim.mvgjava.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saminassim.mvgjava.dto.BookFrontendRequest;
import com.saminassim.mvgjava.dto.BookRatingRequest;
import com.saminassim.mvgjava.dto.ModifyBookRequest;
import com.saminassim.mvgjava.entity.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {

    ResponseEntity<String> createBook(BookFrontendRequest bookFrontendRequest) throws JsonProcessingException;
    List<Book> getAllBooks();
    Optional<Book> getOneBook(UUID bookId);
    List<Book> getBestRating();
    Book modifyBook(UUID bookId, ModifyBookRequest modifyBookRequest);
    void deleteBook(UUID bookId);
    Book createRating(UUID bookId, BookRatingRequest rating);
}
