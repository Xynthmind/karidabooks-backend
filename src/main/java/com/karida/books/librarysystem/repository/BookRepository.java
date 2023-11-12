package com.karida.books.librarysystem.repository;
import org.springframework.stereotype.Repository;
import com.karida.books.librarysystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
    @Query(value = "SELECT * FROM books WHERE isbn = ?1", nativeQuery = true)
    Book finByISBN(String isbn);
    @Query(value = "SELECT * FROM books WHERE isbn = ?1 AND id_book != ?2", nativeQuery = true)
    Book findTheISBNInOtherBook(String isbn, Long id_book);
}
