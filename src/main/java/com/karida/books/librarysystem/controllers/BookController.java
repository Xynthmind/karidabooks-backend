package com.karida.books.librarysystem.controllers;
import com.karida.books.librarysystem.models.Book;
import com.karida.books.librarysystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/library/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getBooks(){
        try{
            List<Book> book= bookRepository.findAll();
            if (book.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(book, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBookById(@PathVariable Long id) {
        try{
            Book book = bookRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(book);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the book does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/data/{isbn}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBookByISBN(@PathVariable String isbn) {
        try{
            Book book = bookRepository.finByISBN(isbn);
            if (book != null) {
                return new ResponseEntity<>(book, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. " +
                    "We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/cat/{id_category}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBookByCategory(@PathVariable Long id_category) {
        try{
            List <Book> book = bookRepository.finByCategory(id_category);
            if (book != null) {
                return new ResponseEntity<>(book, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. " +
                    "We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertBook(@RequestBody Book newbook) {
        try{
            String isbn = newbook.getIsbn();
            if (verificationIfBookExistByISBN(isbn)) {
                return new ResponseEntity<>("Book already exist", HttpStatus.CONFLICT);
            } else {
                bookRepository.save(newbook);
                return new ResponseEntity<>("Insert successful", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public boolean verificationIfBookExistByISBN(@PathVariable String isbn) {
        Book book = bookRepository.finByISBN(isbn);
        return book != null;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book bookRequest){
        Object[] bookDataToVerifyDuplicated;
        try{
            Book book = bookRepository.findById(id).orElseThrow();
            bookDataToVerifyDuplicated = verificationIfBookExistByISBN(bookRequest.getIsbn(), id);
            if((boolean) bookDataToVerifyDuplicated[0]){
                return new ResponseEntity<>("Book already exist (ISBN)", HttpStatus.CONFLICT);
            }else{
                return putOperation(book, bookRequest, 1);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the book does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public Object[] verificationIfBookExistByISBN(@PathVariable String isbn, @PathVariable Long id_book){
        Object[] bookDataQuery = new Object[2];
        Book book = bookRepository.findTheISBNInOtherBook(isbn, id_book);
        if (book != null){
            bookDataQuery[0] = true;
            bookDataQuery[1] = book;
        }else{
            bookDataQuery[0] = false;
            bookDataQuery[1] = null;
        }
        return bookDataQuery;

    }
    @RequestMapping(value = "/del/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id, @RequestBody Book bookRequest){
        try{
            Book book = bookRepository.findById(id).orElseThrow();
            return putOperation(book, bookRequest, 2);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the book does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public ResponseEntity<String> putOperation(@PathVariable Book book, Book bookRequest, int type) {
        book.setId_category(bookRequest.getId_category());
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPhoto(bookRequest.getPhoto());
        book.setEditorial(bookRequest.getEditorial());
        book.setDescription(bookRequest.getDescription());
        book.setIsbn(bookRequest.getIsbn());
        book.setPrice(bookRequest.getPrice());
        book.setDiscount(bookRequest.getDiscount());
        book.setStock(bookRequest.getStock());
        book.setStatus_c(bookRequest.getStatus_c());
        bookRepository.save(book);
        if(type == 1) {
            return new ResponseEntity<>("Update finished", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Logic delete successful", HttpStatus.OK);
        }
    }
}
