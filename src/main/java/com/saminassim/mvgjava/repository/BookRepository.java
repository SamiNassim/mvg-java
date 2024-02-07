package com.saminassim.mvgjava.repository;

import com.saminassim.mvgjava.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findTop3BooksByOrderByAverageRatingDesc();
    @Transactional
    void deleteBookBy_id(UUID bookId);
}
