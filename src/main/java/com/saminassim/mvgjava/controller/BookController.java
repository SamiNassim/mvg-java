package com.saminassim.mvgjava.controller;

import com.saminassim.mvgjava.dto.BookRequest;
import com.saminassim.mvgjava.entity.Book;
import com.saminassim.mvgjava.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/bestrating")
    public List<Book> getBestRating() {
        return bookService.getBestRating();
    }

    @GetMapping("/:id")
    public Optional<Book> getOneBook(Long id) {
        return bookService.getOneBook(id);
    }

    @DeleteMapping("/:id")
    @PreAuthorize("hasAuthority('USER')")
    public Boolean deleteBook(Long id) {
        return bookService.deleteBook(id);
    }



}
