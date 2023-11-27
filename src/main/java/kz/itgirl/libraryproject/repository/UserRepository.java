package kz.itgirl.libraryproject.repository;

import kz.itgirl.libraryproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    Optional<User> findBySurname(String surname);

    Optional<User> findByNameOrSurname(String name, String surname);

    Optional<User> findByLogin(String login);

    boolean existsByName(String name);

    boolean existsBySurname(String surname);

    boolean existsByLogin(String login);
}
