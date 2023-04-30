package comyno.homework.library.service;

import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import comyno.homework.library.model.Book;


@Service
public class JmsListenerService {

    Logger log = LoggerFactory.getLogger(JmsListenerService.class);
    
    @Autowired
    BookService bookService;

    @Autowired
    JmsSenderService jmsSenderService;
    
    @Autowired
	private ObjectMapper mapper;


    @JmsListener(destination = "${jms.queue}")
    public void reciveMessage(String message) {
        log.info("Recive message: " + message);
    }
    
    @JmsListener(destination = "${jms.queue.book.add}")
    public void addBook(String message) {
        log.debug("Recive message on queue jms.queue.book.add : " + message);
        
        try {
			Book book = mapper.readValue(message, Book.class);
			bookService.saveBook(book);
            log.info("Saved book to db : " + book);
		} catch (JsonProcessingException e) {
            jmsSenderService.sendErrorMessage("Error processing message from jms.queue.book.update : " + e.getMessage());
			e.printStackTrace();
		}
        
    }
    
    @JmsListener(destination = "${jms.queue.book.delete}")
    public void deleteBook(Long id) {
        log.debug("Recive message on queue ${jms.queue.book.delete}: " + id);

        try {
			bookService.deleteBookById(id);
		} catch (Exception e) {
            jmsSenderService.sendErrorMessage("Error processing message from jms.queue.book.update : " + e.getMessage());
			e.printStackTrace();
        }
    }
    
    @JmsListener(destination = "${jms.queue.book.update}")
    public void updateBook(String message) {
        log.debug("Recive message on queue jms.queue.book.update: " + message);

        try {
			Book book = mapper.readValue(message, Book.class);
			bookService.updateBook(book);
		} catch (JsonProcessingException e) {
            jmsSenderService.sendErrorMessage("Error processing message from jms.queue.book.update : " + e.getMessage());
			e.printStackTrace();
        }
    }
    
    @JmsListener(destination = "${jms.queue.error}")
    public void readJMSErrorMsg(Message message) {
        log.debug("Recive message: " + message);
    }
}
