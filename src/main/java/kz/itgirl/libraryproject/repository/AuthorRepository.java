package kz.itgirl.libraryproject.repository;

import kz.itgirl.libraryproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long>{
}
