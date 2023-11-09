package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.BookCreateDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.model.Author;
import kz.itgirl.libraryproject.model.Book;
import kz.itgirl.libraryproject.model.Genre;
import kz.itgirl.libraryproject.repository.AuthorRepository;
import kz.itgirl.libraryproject.repository.BookRepository;
import kz.itgirl.libraryproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Override
    public BookDto getByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto getByNameV2(String name) {
        Book book = bookRepository.findBookByNameSql(name).orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto getByNameV3(String name) {
        Specification<Book> specification = Specification.where((Specification<Book>)
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name));
        Book book = bookRepository.findOne(specification).orElseThrow();
        return convertEntityToDto(book);
    }

    @Override
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Book book = bookRepository.save(convertDtoTooEntity(bookCreateDto));
        return convertEntityToDto(book);
    }

    public Book convertDtoTooEntity(BookCreateDto bookCreateDto) {
        Genre genre = genreRepository.findByName(bookCreateDto.getGenre())
                .orElseGet(() -> {
                    Genre newGenre = Genre.builder().name(bookCreateDto.getGenre()).build();
                    genreRepository.save(newGenre); // Сохраняем новый жанр в базе данных
                    return newGenre;
                });

        Set<Author> authorSet = bookCreateDto.getAuthors().stream()
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

        return Book.builder()
                .name(bookCreateDto.getName())
                .genre(genre)
                .authors(authorSet)
                .build();
    }


    private BookDto convertEntityToDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .genre(book.getGenre().getName())
                .build();
    }
}
