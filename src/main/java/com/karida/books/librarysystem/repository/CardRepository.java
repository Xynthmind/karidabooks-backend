package com.karida.books.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.karida.books.librarysystem.models.Card;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
    @Query(value = "SELECT * FROM cards WHERE id_user = ?1", nativeQuery = true)
    List<Card> findById_User(Long id);
    @Query(value = "SELECT * FROM cards WHERE card_number = ?1", nativeQuery = true)
    Card findByCard_number(Long card_number);
}
