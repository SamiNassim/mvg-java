package com.saminassim.mvgjava.service;

import com.saminassim.mvgjava.entity.Book;
import com.saminassim.mvgjava.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getOneBook(Long bookId) {
        return bookRepository.findById(bookId);
    }
}
