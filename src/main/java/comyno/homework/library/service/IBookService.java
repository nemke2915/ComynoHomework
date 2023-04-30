package comyno.homework.library.service;

import comyno.homework.library.model.Book;

import java.util.List;

public interface IBookService {

    // Save book to db
    Book saveBook(Book book);
    
    // Find Book by id
    Book findById(Long id);

    // Fetch all books
    List<Book> fetchAllBooks();

    // Update book
    Book updateBook(Book book);

    // Delete book from db
    void deleteBookById(Long id);
    
    // Delete all books
    void deleteAllBooks();
    
}
