package com.karida.books.librarysystem.repository;

import org.springframework.stereotype.Repository;
import com.karida.books.librarysystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
}
