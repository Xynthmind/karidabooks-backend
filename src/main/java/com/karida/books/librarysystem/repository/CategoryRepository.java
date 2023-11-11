package com.karida.books.librarysystem.repository;

import org.springframework.stereotype.Repository;
import com.karida.books.librarysystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
}
