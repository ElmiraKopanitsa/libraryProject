package kz.itgirl.libraryproject.repository;

import kz.itgirl.libraryproject.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
