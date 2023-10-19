package kz.itgirl.libraryproject.repository;

import kz.itgirl.libraryproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}
