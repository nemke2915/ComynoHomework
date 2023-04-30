package comyno.homework.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comyno.homework.library.model.Book;
import comyno.homework.library.service.BookService;
import comyno.homework.library.service.JmsSenderService;

@RestController
@RequestMapping("/api")
public class BookController {

	Logger log = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private JmsSenderService jmsSenderService;

	@Autowired
	private BookService bookService;

	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		try {
			List<Book> booksList = bookService.fetchAllBooks();

			if (booksList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(booksList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
		try {
			Book book = bookService.findById(id);

			if (book == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} 
			
			
			return new ResponseEntity<>(book, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		
		try {
			jmsSenderService.addBook(book);

			return new ResponseEntity<>(book, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book request) {

		try {
			Book book = bookService.findById(id);

			if (book != null) {
				
				if (request.getTitle() != null && !"".equalsIgnoreCase(request.getTitle()))
					book.setTitle(request.getTitle());
				
				if (request.getAuthor() != null && !"".equalsIgnoreCase(request.getAuthor()))
					book.setAuthor(request.getAuthor());
				
				if (request.getStatus() != null) {
					book.setStatus(request.getStatus());
				}
			}		
			
			jmsSenderService.updateBook(book);
			
			return new ResponseEntity<>(book, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/books/delete/{id}")
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
		try {
			jmsSenderService.deleteBook(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/books/delete")
	public ResponseEntity<HttpStatus> deleteAllBooks() {
		try {
			bookService.deleteAllBooks();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
