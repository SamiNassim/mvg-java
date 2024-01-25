package com.saminassim.mvgjava.service;

import com.saminassim.mvgjava.dto.BookRequest;
import com.saminassim.mvgjava.entity.Book;
import com.saminassim.mvgjava.repository.BookRepository;
import com.saminassim.mvgjava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<String> createBook(BookRequest bookRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = Objects.requireNonNull(userRepository.findByEmail(authentication.getName()).orElse(null)).getId();


        Book newBook = new Book();

        newBook.setTitle(bookRequest.getTitle());
        newBook.setAuthor(bookRequest.getAuthor());
        newBook.setImageUrl(bookRequest.getImageUrl());
        newBook.setYear(bookRequest.getYear());
        newBook.setGenre(bookRequest.getGenre());
        newBook.setUserId(currentUserId);

        bookRepository.save(newBook);

        return new ResponseEntity<>("Livre enregistré !", HttpStatus.CREATED);

    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getOneBook(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> getBestRating() {
        return bookRepository.findTop3BooksByOrderByAverageRatingDesc();
    }

    public Boolean deleteBook(Long bookId) {
        return bookRepository.deleteBookById(bookId);
    }
}
