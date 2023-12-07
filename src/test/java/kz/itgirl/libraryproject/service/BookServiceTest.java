package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.dto.BookCreateDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.dto.BookUpdateDto;
import kz.itgirl.libraryproject.model.Author;
import kz.itgirl.libraryproject.model.Book;
import kz.itgirl.libraryproject.model.Genre;
import kz.itgirl.libraryproject.repository.AuthorRepository;
import kz.itgirl.libraryproject.repository.BookRepository;
import kz.itgirl.libraryproject.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    GenreRepository genreRepository;

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    BookServiceImpl bookService;

    private Book book;

    private AuthorDto authorDto;

    @BeforeEach
    public void setUp() {
        Long id = 1L;
        String name = "Idiot";
        Genre genre = new Genre();
        Set<Author> authors = new HashSet<>();
        book = new Book(id, name, genre, authors);

        authorDto = new AuthorDto();
        authorDto.setName("John");
        authorDto.setSurname("Doe");
        authorDto.setBooks(new ArrayList<>());
    }

    @Test
    public void testGetByName() {

        when(bookRepository.findBookByName(book.getName())).thenReturn(Optional.of(book));
        BookDto bookDto = bookService.getByNameV1(book.getName());
        verify(bookRepository).findBookByName(book.getName());
        Assertions.assertEquals(convertEntityToDto(book).getName(), bookDto.getName());
        Assertions.assertEquals(convertEntityToDto(book).getGenre(), bookDto.getGenre());
        Assertions.assertEquals(convertEntityToDto(book).getAuthors(), bookDto.getAuthors());
    }

    @Test
    public void testGetByNameFAiled() {
        when(bookRepository.findBookByName(book.getName())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> bookService.getByNameV1(book.getName()));
        verify(bookRepository).findBookByName(book.getName());
    }

    @Test
    public void testCreateBook() {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("Garden");
        bookCreateDto.setGenre("Roman");
        List<AuthorDto> authorDtoList = new ArrayList<>();
        authorDtoList.add(authorDto);
        bookCreateDto.setAuthors(authorDtoList);

        when(bookRepository.findBookByName(bookCreateDto.getName())).thenReturn(Optional.empty());
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        BookDto bookDto = bookService.createBook(bookCreateDto);
        verify(bookRepository).findBookByName(bookCreateDto.getName());
        verify(bookRepository).save(Mockito.any(Book.class));
        Assertions.assertEquals(convertEntityToDto(book).getName(), bookDto.getName());
        Assertions.assertEquals(convertEntityToDto(book).getGenre(), bookDto.getGenre());
        Assertions.assertEquals(convertEntityToDto(book).getAuthors(), bookDto.getAuthors());
    }

    @Test
    public  void testUpdateBook() {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(1L);
        bookUpdateDto.setName("Garden");
        bookUpdateDto.setGenre("Roman");
        List<AuthorDto> authorDtoList = new ArrayList<>();
        authorDtoList.add(authorDto);
        bookUpdateDto.setAuthors(authorDtoList);

        when(bookRepository.findById(bookUpdateDto.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);
        BookDto bookDto = bookService.updateBook(bookUpdateDto);
        verify(bookRepository).findById(bookUpdateDto.getId());
        verify(bookRepository).save(Mockito.any(Book.class));
        Assertions.assertEquals(convertEntityToDto(book).getName(), bookDto.getName());
        Assertions.assertEquals(convertEntityToDto(book).getGenre(), bookDto.getGenre());
        Assertions.assertEquals(convertEntityToDto(book).getAuthors(), bookDto.getAuthors());
    }

    @Test
    public void testUpdateBookFailed() {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(1L);
        bookUpdateDto.setName("Garden");
        bookUpdateDto.setGenre("Roman");
        List<AuthorDto> authorDtoList = new ArrayList<>();
        authorDtoList.add(authorDto);
        bookUpdateDto.setAuthors(authorDtoList);

        when(bookRepository.findById(bookUpdateDto.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> bookService.updateBook(bookUpdateDto));
        verify(bookRepository).findById(bookUpdateDto.getId());
    }

    @Test
    public void testDeleteBook() {
        String delete = "Book removed.";

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(book.getId());
        String result = bookService.deleteBook(book.getId());
        verify(bookRepository).deleteById(book.getId());
        Assertions.assertEquals(delete, result);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);
        List<BookDto> expectedbookDtoList = books.stream()
                .map(this::convertEntityToDto)
                .toList();
        List<BookDto> bookDtoList = bookService.getAllBooks();
        verify(bookRepository).findAll();
        Assertions.assertEquals(expectedbookDtoList, bookDtoList);
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();
    }
}
