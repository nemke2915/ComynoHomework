package comyno.homework.library.service;

import comyno.homework.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import comyno.homework.library.repository.IBookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService{

    Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private IBookRepository iBookRepository;

    @Override
    public Book saveBook(Book book) {
        return iBookRepository.save(book);
    }

    @Override
    public List<Book> fetchAllBooks() {
        List<Book> books = (List<Book>) iBookRepository.findAll();
        if (books == null || books.isEmpty()) {
			log.info("No books found, return null");
			return null;
		}
        
        log.info("Found : " + books.size());
        return books;
    }
    
    @Override
    public Book findById(Long id) {
    	Optional<Book> book = iBookRepository.findById(id);
    	
    	if (!book.isPresent()) {
    		log.info("Can't find book with id: " + id + ", return null");
    		return null;
    	}
    	
    	log.info("Found book with id: " + id);
		return book.get();
    }
    
	@Override
	public Book updateBook(Book book) {

        if (book == null) {
            log.error("Book is null, couldnt update. Book : " + book);
            return null;
        }

        if (!"".equalsIgnoreCase(book.getAuthor())) {
            book.setAuthor(book.getAuthor());
        }

        if (!"".equalsIgnoreCase(book.getTitle())) {
            book.setTitle(book.getTitle());
        }

        if (book.getStatus() != null) {
            book.setStatus(book.getStatus());
        }

        return iBookRepository.save(book);
    }
	
    @Override
    public void deleteAllBooks() {
        iBookRepository.deleteAll();
    }
    
    @Override
    public void deleteBookById(Long id) {
        iBookRepository.deleteById(id);
    }

}
