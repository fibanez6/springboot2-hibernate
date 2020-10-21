package com.fibanez.springboot.controller;

import com.fibanez.springboot.exception.RecordConflictException;
import com.fibanez.springboot.exception.RecordNotFoundException;
import com.fibanez.springboot.model.Book;
import com.fibanez.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Validated
@RestController
public class CachedBookController {

    @Autowired
    BookService bookService;

    @GetMapping("/book/{id}")
    public Book get(@PathVariable Long id) throws RecordNotFoundException {
        System.out.println("Getting by ID  : " + id);

        return bookService.getById(id);
    }

    @PostMapping(value = "/book/", consumes = "application/json", produces = "application/json")
    public Book post(@Valid @RequestBody Book book) {
        System.out.println("Posting book");
        return bookService.create(book);
    }

    @PutMapping(value = "/book/{id}", consumes = "application/json", produces = "application/json")
    public Book put(@PathVariable Long id, @Valid @RequestBody Book book) throws RecordNotFoundException, RecordConflictException {
        System.out.println("Putting by ID  : " + id);
        if (id != book.getId()) {
            throw new RecordConflictException("Identifier conflict");
        }
        return bookService.update(book);
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable Long id) {
        System.out.println("Deleting by ID  : " + id);
        bookService.delete(id);
    }
}
