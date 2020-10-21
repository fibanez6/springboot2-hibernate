package com.fibanez.springboot.service;

import com.fibanez.springboot.exception.RecordNotFoundException;
import com.fibanez.springboot.model.Book;

public interface BookService {

    Book getById(Long id) throws RecordNotFoundException;

    Book create(Book book);

    Book update(Book book) throws RecordNotFoundException;

    void delete(Long id);
}