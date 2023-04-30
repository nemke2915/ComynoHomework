package comyno.homework.library.repository;

import comyno.homework.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface IBookRepository extends JpaRepository<Book, Long> {
}
