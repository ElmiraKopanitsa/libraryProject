package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.AuthorCreateDto;
import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.dto.AuthorUpdateDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.model.Author;
import kz.itgirl.libraryproject.model.Book;
import kz.itgirl.libraryproject.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;
import java.util.stream.Collectors;
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
    public void testGetAuthorByNameV3() {
        Long id = 1L;
        String name = "Александр";
        String surname = "Пушкин";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        Specification<Author> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), name);

        when(authorRepository.findOne(any(Specification.class))).thenReturn(Optional.of(author));
        AuthorDto authorDto = authorService.getAuthorByNameV3(name);
        verify(authorRepository).findOne(any(Specification.class));
        Assertions.assertEquals(author.getId(), authorDto.getId());
        Assertions.assertEquals(author.getName(), authorDto.getName());
    }

    @Test
    public void testGetAuthorByNameV3Failed() {
        Long id = 1L;
        String name = "Александр";
        String surname = "Пушкин";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        Specification<Author> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), name);

        when(authorRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

       Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByNameV3(name));
        verify(authorRepository).findOne(any(Specification.class));
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

    @Test
    public void testGetAllAuthors() {
        Long id =1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> book = new HashSet<>();
        Author author = new Author(id, name, surname, book);

        List<Author> authorList = new ArrayList<>();
        authorList.add(author);

        when(authorRepository.findAll()).thenReturn(authorList);
        List<AuthorDto> expectedAuthorDtoList = authorList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
        List<AuthorDto> authorDtoList = authorService.getAllAuthors();
        verify(authorRepository).findAll();
        Assertions.assertEquals(expectedAuthorDtoList, authorDtoList);
    }

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
    }
}
