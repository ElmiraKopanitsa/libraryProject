package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.BookCreateDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.dto.BookUpdateDto;
import kz.itgirl.libraryproject.model.Author;
import kz.itgirl.libraryproject.model.Book;
import kz.itgirl.libraryproject.model.Genre;
import kz.itgirl.libraryproject.repository.AuthorRepository;
import kz.itgirl.libraryproject.repository.BookRepository;
import kz.itgirl.libraryproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookDto getByNameV1(String name) {
        log.info("Try find book by name {}", name);
        Book book = bookRepository.findBookByName(name).orElseThrow();
        BookDto bookDto = convertEntityToDto(book);
        log.info("Find book {}", bookDto.toString());
        return bookDto;
    }

    @Override
    public BookDto getByNameV2(String name) {
        log.info("Try find book by name {}", name);
        Book book = bookRepository.findBookByNameSql(name).orElseThrow();
        BookDto bookDto = convertEntityToDto(book);
        log.info("Find book {}", bookDto.toString());
        return bookDto;
    }

    @Override
    public BookDto getByNameV3(String name) {
        log.info("Try find book by name {}", name);
        Specification<Book> specification = Specification.where((Specification<Book>)
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name));
        Book book = bookRepository.findOne(specification).orElseThrow();
        BookDto bookDto = convertEntityToDto(book);
        log.info("Find book {}", bookDto.toString());
        return bookDto;
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        log.info("Try create book: {}", bookCreateDto.toString());
        Book existingBook = bookRepository.findBookByName(
                        bookCreateDto.getName()).orElse(null);
        if(existingBook == null) {
            Book book = bookRepository.save(convertDtoTooEntity(bookCreateDto));
            BookDto bookDto = convertEntityToDto(book);
            log.info("Create book: {}", bookDto.toString());
            return bookDto;
        }
        BookDto bookDto = convertEntityToDto(existingBook);
        log.info("Find book: {}", bookDto.toString());
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookUpdateDto bookUpdateDto) {
        log.info("Try update book: {}", bookUpdateDto.toString());
        Genre genre = getGenre(bookUpdateDto);
        Set<Author> authorSet = getAuthors(bookUpdateDto);
        Book book = bookRepository.findById(bookUpdateDto.getId()).orElseThrow();
        book.setName(bookUpdateDto.getName());
        book.setGenre(genre);
        book.setAuthors(authorSet);
        Book newBook = bookRepository.save(book);
        BookDto bookDto = convertEntityToDto(newBook);
        log.info("Update book: {}", bookDto.toString());
        return bookDto;
    }

    @Override
    public String deleteBook(Long id) {
        bookRepository.deleteById(id);
        return "Book removed.";
    }

    public Book convertDtoTooEntity(BookCreateDto bookCreateDto) {
        Genre genre = getGenre(bookCreateDto);
        Set<Author> authorSet = getAuthors(bookCreateDto);

        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .authors(authorSet)
                .build();
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();
    }

    private Genre getGenre(BookCreateDto bookCreateDto) {
      return genreRepository.findByName(bookCreateDto.getGenre())
                .orElseGet(() -> {
                    Genre newGenre = Genre.builder().name(bookCreateDto.getGenre()).build();
                    genreRepository.save(newGenre); // Сохраняем новый жанр в базе данных
                    return newGenre;
                });
    }

    private Genre getGenre(BookUpdateDto bookUpdateDto) {
        return genreRepository.findByName(bookUpdateDto.getGenre())
                .orElseGet(() -> {
                    Genre newGenre = Genre.builder().name(bookUpdateDto.getGenre()).build();
                    genreRepository.save(newGenre); // Сохраняем новый жанр в базе данных
                    return newGenre;
                });
    }

    private Set<Author> getAuthors(BookCreateDto bookCreateDto) {
        return bookCreateDto.getAuthors().stream()
                .map(authorDto -> {
                    Author existingAuthor = authorRepository.findByNameAndSurname(
                                    authorDto.getName(), authorDto.getSurname())
                            .orElse(null);

                    if (existingAuthor == null) {
                        Author newAuthor = Author.builder()
                                .name(authorDto.getName())
                                .surname(authorDto.getSurname())
                                .build();
                        authorRepository.save(newAuthor); // Сохраняем нового автора в базе данных
                        return newAuthor;
                    } else {
                        return existingAuthor;
                    }
                })
                .collect(Collectors.toSet());
    }

    public Set<Author> getAuthors(BookUpdateDto bookUpdateDto) {
        return bookUpdateDto.getAuthors().stream()
                .map(authorDto -> {
                    Author existingAuthor = authorRepository.findByNameAndSurname(
                                    authorDto.getName(), authorDto.getSurname())
                            .orElse(null);

                    if (existingAuthor == null) {
                        Author newAuthor = Author.builder()
                                .name(authorDto.getName())
                                .surname(authorDto.getSurname())
                                .build();
                        authorRepository.save(newAuthor); // Сохраняем нового автора в базе данных
                        return newAuthor;
                    } else {
                        return existingAuthor;
                    }
                })
                .collect(Collectors.toSet());
    }
}
