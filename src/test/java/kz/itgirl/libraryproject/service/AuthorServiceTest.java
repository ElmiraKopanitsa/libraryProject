package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.AuthorCreateDto;
import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.dto.AuthorUpdateDto;
import kz.itgirl.libraryproject.model.Author;
import kz.itgirl.libraryproject.model.Book;
import kz.itgirl.libraryproject.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    public void testGetAuthorById() {
        Long id =1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        AuthorDto authorDto = authorService.getAuthorById(id);
        verify(authorRepository).findById(id);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByIdFailed() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }

    @Test
    public void testGetAuthorByName() {
        Long id =1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));
        AuthorDto authorDto = authorService.getAuthorByNameV1(name);
        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameFailed() {
        String name = "John";

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByNameV1(name));
        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void testCreateAuthor() {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();
        authorCreateDto.setName("John");
        authorCreateDto.setSurname("Doe");

        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        when(authorRepository.save(Mockito.any(Author.class))).thenReturn(author);
        AuthorDto createdAuthor = authorService.createAuthor(authorCreateDto);

        verify(authorRepository).save(Mockito.any(Author.class));
        Assertions.assertEquals(authorCreateDto.getName(), createdAuthor.getName());
    }

    @Test
    public void testUpdateAuthor() {
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto();
        authorUpdateDto.setId(1L);
        authorUpdateDto.setName("John");
        authorUpdateDto.setSurname("Doe");

        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorRepository.save(Mockito.any(Author.class))).thenReturn(author);
        AuthorDto authorDto = authorService.updateAuthor(authorUpdateDto);
        verify(authorRepository).findById(id);
        verify(authorRepository).save(Mockito.any(Author.class));
        Assertions.assertEquals(authorUpdateDto.getName(), authorDto.getName());
    }

    @Test
    public void testUpdateAuthorFailed() {
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto();
        authorUpdateDto.setId(1L);
        authorUpdateDto.setName("John");
        authorUpdateDto.setSurname("Doe");

        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.updateAuthor(authorUpdateDto));
        verify(authorRepository).findById(id);
    }

    @Test
    public void testDeleteAuthor() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        String delete = "Author removed.";

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).deleteById(id);
        String result = authorService.deleteAuthor(id);
        verify(authorRepository).deleteById(id);
        Assertions.assertEquals(delete, result);
    }
}
