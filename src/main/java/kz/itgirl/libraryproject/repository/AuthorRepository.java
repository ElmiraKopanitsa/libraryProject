package kz.itgirl.libraryproject.repository;

import kz.itgirl.libraryproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    Optional<Author> findAuthorByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM AUTHOR WHERE name=?")
    Optional<Author> findAuthorByNameSql(String name);

    Optional<Author> findByNameAndSurname(String name, String surname);
}
