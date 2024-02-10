package com.saminassim.mvgjava.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saminassim.mvgjava.dto.BookFrontendRequest;
import com.saminassim.mvgjava.dto.BookRatingRequest;
import com.saminassim.mvgjava.dto.ModifyBookFrontendRequest;
import com.saminassim.mvgjava.dto.ModifyBookRequest;
import com.saminassim.mvgjava.entity.Book;
import com.saminassim.mvgjava.exception.BookAlreadyRatedException;
import com.saminassim.mvgjava.exception.BookCannotBeDeletedException;
import com.saminassim.mvgjava.exception.BookCannotBeModifiedException;
import com.saminassim.mvgjava.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> createBook(@ModelAttribute BookFrontendRequest book) throws JsonProcessingException {
        return bookService.createBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/bestrating")
    public List<Book> getBestRating() {
        return bookService.getBestRating();
    }

    @GetMapping("/{id}")
    public Optional<Book> getOneBook(@PathVariable UUID id) {
        return bookService.getOneBook(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> modifyBook(@RequestBody ModifyBookRequest modifyBookRequest, ModifyBookFrontendRequest modifyBookFrontendRequest, @PathVariable UUID id) throws JsonProcessingException {
        try {
            bookService.modifyBook(modifyBookFrontendRequest, modifyBookRequest, id);
            return ResponseEntity.ok().build();
        } catch (BookCannotBeModifiedException e) {
            return ResponseEntity.status(401)
                    .body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> modifyBookImg(@ModelAttribute ModifyBookFrontendRequest modifyBookFrontendRequest, ModifyBookRequest modifyBookRequest, @PathVariable UUID id) throws JsonProcessingException {
        try {
            bookService.modifyBook(modifyBookFrontendRequest, modifyBookRequest, id);
            return ResponseEntity.ok().build();
        } catch (BookCannotBeModifiedException e) {
            return ResponseEntity.status(401)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (BookCannotBeDeletedException e) {
            return ResponseEntity.status(403)
                    .body(e.getMessage());
        }

    }

    @PostMapping("/{id}/rating")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createRating(@PathVariable UUID id, @RequestBody BookRatingRequest bookRatingRequest) {
        try {
            return ResponseEntity.ok().body(bookService.createRating(id, bookRatingRequest));
        } catch (BookAlreadyRatedException e) {
            return ResponseEntity.status(401)
                    .body(e.getMessage());
        }
    }



}
