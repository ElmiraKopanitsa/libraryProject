package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.BookDto;
import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();
}
