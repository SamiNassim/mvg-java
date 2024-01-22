package com.saminassim.mvgjava.repository;

import com.saminassim.mvgjava.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

}
