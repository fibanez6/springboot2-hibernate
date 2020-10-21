package com.fibanez.springboot.service;

import com.fibanez.springboot.exception.RecordNotFoundException;
import com.fibanez.springboot.model.Book;
import com.fibanez.springboot.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {
     
    @Autowired
    BookRepository repository;

    /**
     * to enable caching behavior for a method
     * will skip running the method
     */
    @Cacheable("books")
    public Book getById(Long id) throws RecordNotFoundException {
        Optional<Book> book = repository.findById(id);
        if(book.isPresent()) {
            return book.get();
        }
        throw new RecordNotFoundException("No book record exist for given id");
    }

//    No possible because the inout book.id is null
//    @Cacheable(value = "books", key = "#book.id")
    public Book create(Book book) {
        Book newEntity = new Book(book.getIsbn(), book.getTitle());
        return repository.save(newEntity);
    }

    @CachePut(value = "books", key = "#book.id")
    public Book update(Book book) throws RecordNotFoundException {
        Optional<Book> entity = repository.findById(book.getId());

        if (entity.isPresent()) {
            Book newEntity = entity.get();
            newEntity.setIsbn(book.getIsbn());
            newEntity.setTitle(book.getTitle());
            return repository.save(newEntity);
        }
        throw new RecordNotFoundException("No book record exist for given id");
    }

    @CacheEvict(value = "books", key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }
}