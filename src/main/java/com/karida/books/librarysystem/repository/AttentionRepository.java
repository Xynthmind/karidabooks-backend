package com.karida.books.librarysystem.repository;

import com.karida.books.librarysystem.models.Attention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttentionRepository extends JpaRepository<Attention, Long>{
}
