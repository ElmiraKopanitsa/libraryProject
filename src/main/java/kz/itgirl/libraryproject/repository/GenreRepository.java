package kz.itgirl.libraryproject.repository;

import kz.itgirl.libraryproject.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {

    Optional<Genre> findByName(String name);
}
