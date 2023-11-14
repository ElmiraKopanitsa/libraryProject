package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.BookCreateDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.dto.BookUpdateDto;
import java.util.List;

public interface BookService {

    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    BookDto getByNameV3(String name);

    BookDto createBook(BookCreateDto bookCreateDto);

    BookDto updateBook(BookUpdateDto bookUpdateDto);

    String deleteBook(Long id);

    List<BookDto> getAllBooks();
}
