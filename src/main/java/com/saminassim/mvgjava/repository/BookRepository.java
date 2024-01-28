package com.saminassim.mvgjava.repository;

import com.saminassim.mvgjava.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findTop3BooksByOrderByAverageRatingDesc();
    Optional<Book> findById(UUID bookId);
    @Transactional
    void deleteBookById(UUID bookId);
}
