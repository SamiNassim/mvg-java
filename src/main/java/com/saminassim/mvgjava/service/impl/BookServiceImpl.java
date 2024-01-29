package com.saminassim.mvgjava.service.impl;

import com.saminassim.mvgjava.dto.BookRatingRequest;
import com.saminassim.mvgjava.dto.BookRequest;
import com.saminassim.mvgjava.entity.Book;
import com.saminassim.mvgjava.entity.BookRating;
import com.saminassim.mvgjava.exception.BookAlreadyRatedException;
import com.saminassim.mvgjava.exception.BookCannotBeDeletedException;
import com.saminassim.mvgjava.repository.BookRatingRepository;
import com.saminassim.mvgjava.repository.BookRepository;
import com.saminassim.mvgjava.repository.UserRepository;
import com.saminassim.mvgjava.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookRatingRepository bookRatingRepository;

    @Override
    public ResponseEntity<String> createBook(BookRequest bookRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = Objects.requireNonNull(userRepository.findByEmail(authentication.getName()).orElse(null)).getId();


        Book newBook = new Book();
        BookRating newBookRating = new BookRating();

        newBook.setTitle(bookRequest.getTitle());
        newBook.setAuthor(bookRequest.getAuthor());
        newBook.setImageUrl(bookRequest.getImageUrl());
        newBook.setYear(bookRequest.getYear());
        newBook.setGenre(bookRequest.getGenre());
        newBook.setUserId(currentUserId);
        newBook.setAverageRating(bookRequest.getCreatorRating());

        newBookRating.setBook(newBook);
        newBookRating.setUserId(currentUserId);
        newBookRating.setGrade(bookRequest.getCreatorRating());

        bookRepository.save(newBook);
        bookRatingRepository.save(newBookRating);

        return new ResponseEntity<>("Livre enregistré !", HttpStatus.CREATED);

    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getOneBook(UUID bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> getBestRating() {
        return bookRepository.findTop3BooksByOrderByAverageRatingDesc();
    }

    public void deleteBook(UUID bookId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = Objects.requireNonNull(userRepository.findByEmail(authentication.getName()).orElse(null)).getId();
        Optional<Book> selectedBook = bookRepository.findById(bookId);
        if(!currentUserId.equals(selectedBook.orElseThrow().getUserId())) {
            throw new BookCannotBeDeletedException("Vous ne pouvez pas supprimer ce livre.");
        }
        bookRepository.deleteBookById(bookId);
    }

    @Override
    public Book createRating(UUID bookId, BookRatingRequest bookRatingRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = Objects.requireNonNull(userRepository.findByEmail(authentication.getName()).orElse(null)).getId();
        Optional<Book> selectedBook = bookRepository.findById(bookId);

        // Check if the user has already rated the book
        for (BookRating rating : selectedBook.orElseThrow().getRatings()) {
            if(rating.getUserId().equals(currentUserId)) {
                throw new BookAlreadyRatedException("Vous avez déjà noté ce livre.");
            }
        }

        BookRating newRating = new BookRating();
        newRating.setUserId(currentUserId);
        newRating.setBook(selectedBook.orElseThrow());
        newRating.setGrade(bookRatingRequest.getRating());

        bookRatingRepository.save(newRating);

        int allRatings = selectedBook.orElseThrow().getRatings().size();
        Integer sum = 0;
        for(BookRating rating : selectedBook.orElseThrow().getRatings()) {
            sum += rating.getGrade();
        }

        Integer newAverage = Math.toIntExact(Math.round((double) sum / allRatings));

        selectedBook.orElseThrow().setAverageRating(newAverage);

        return bookRepository.save(selectedBook.orElseThrow());

    }


}
