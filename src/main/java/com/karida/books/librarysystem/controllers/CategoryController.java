package com.karida.books.librarysystem.controllers;
import com.karida.books.librarysystem.models.Category;
import com.karida.books.librarysystem.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/categories")
public class CategoryController {
}
