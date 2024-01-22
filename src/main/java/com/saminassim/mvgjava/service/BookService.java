package com.saminassim.mvgjava.service;

import com.saminassim.mvgjava.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Optional<Book> getOneBook(Long bookId);
}
