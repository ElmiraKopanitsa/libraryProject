package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.dto.GenreDto;
import kz.itgirl.libraryproject.model.Genre;
import kz.itgirl.libraryproject.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreDto getBooksByGenre(Long id) {
        log.info("Try to find genre by id {}", id);
        Genre genre = genreRepository.findById(id).orElseThrow();
        GenreDto genreDto = convertToDto(genre);
        log.info("Find genre: {}", genreDto.toString());
        return genreDto;
    }

    private GenreDto convertToDto(Genre genre) {
        List<BookDto> bookDtoList = genre.getBooks()
                .stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .authors(book.getAuthors()
                                .stream().map(author -> AuthorDto.builder()
                                        .id(author.getId())
                                        .name(author.getName())
                                        .surname(author.getSurname())
                                        .build()
                                )
                                .toList()
                        )
                        .build()
                )
                .toList();
        return GenreDto.builder()
                .books(bookDtoList)
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
