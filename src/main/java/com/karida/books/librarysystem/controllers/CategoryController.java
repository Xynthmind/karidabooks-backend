package com.karida.books.librarysystem.controllers;

import com.karida.books.librarysystem.models.Category;
import com.karida.books.librarysystem.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/library/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getCategories(){
        try{
            List<Category>  attention = categoryRepository.findAll();
            if (attention.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(attention, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertAddress(@RequestBody Category category){
        try{
            categoryRepository.save(category);
            return new ResponseEntity<>("Insert successful", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category newcategory){
        try{
            return updateRecordOperation(id, newcategory, 1);
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/del/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> deleteCategory(@PathVariable Long id, @RequestBody Category newcategory){
        try{
           return updateRecordOperation(id, newcategory, 2);
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public ResponseEntity<String> updateRecordOperation(@PathVariable Long id, @PathVariable Category newcategory, Integer type){
            Category category = categoryRepository.findById(id).orElseThrow();
            category.setCategory_name(newcategory.getCategory_name());
            category.setStatus_c(newcategory.getStatus_c());
            categoryRepository.save(category);
            if(type == 1){
                return new ResponseEntity<>("Update successful", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Logic delete successful", HttpStatus.OK);
            }
    }
}
