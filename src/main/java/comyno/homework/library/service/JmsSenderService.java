package comyno.homework.library.service;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import comyno.homework.library.model.Book;

@Service
public class JmsSenderService {

    Logger log = LoggerFactory.getLogger(JmsSenderService.class);

    @Autowired
    JmsTemplate jmsTemplate;
    
    @Value("${jms.queue}")
    String jmsQueue;

    @Value("${jms.queue.book.update}")
    String updateBookQueue;

    @Value("${jms.queue.book.add}")
    String addBookQueue;

    @Value("${jms.queue.book.delete}")
    String deleteBookQueue;
    

    @Autowired
	private ObjectMapper mapper;

    public void sendMessage(String message) {
        log.info("Sending message: " + message);
        jmsTemplate.convertAndSend(jmsQueue, message);
    }

    public void sendErrorMessage(String message) {
        log.info("Sending error message: " + message);
        jmsTemplate.convertAndSend(jmsQueue, message);
    }

    public void addBook(Book book) {
        log.info("Sending book to add queue: " + book);
        try {
			String message = mapper.writeValueAsString(book);
			jmsTemplate.convertAndSend(addBookQueue, message);
		} catch (JsonProcessingException e) {
            sendErrorMessage("Error processing message from jms.queue.book.add : " + e.getMessage());
			e.printStackTrace();
		}
    }
    
    public void updateBook(Book book) {
        log.info("Updating book " + book.getBookId());
		try {
			String message = mapper.writeValueAsString(book);
			jmsTemplate.convertAndSend(updateBookQueue, message);
		} catch (JsonProcessingException e) {
            sendErrorMessage("Error processing message from jms.queue.book.update : " + e.getMessage());
			e.printStackTrace();
		}
    }

    public void deleteBook(Long id) {
        log.info("Deleting book with id" + id);
		try {
			String message = mapper.writeValueAsString(id);
			jmsTemplate.convertAndSend(deleteBookQueue, message);
		} catch (JsonProcessingException e) {
            sendErrorMessage("Error processing message from jms.queue.book.delete : " + e.getMessage());
			e.printStackTrace();
		}
    }
}
