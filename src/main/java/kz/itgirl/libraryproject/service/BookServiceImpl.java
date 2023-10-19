package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.model.Book;
import kz.itgirl.libraryproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    final BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return convertToDto(bookList);
    }

    private List<BookDto> convertToDto(List<Book> bookList) {
        return bookList.stream()
                .map(book -> BookDto.builder()
                        .genre(book.getGenre().getName())
                        .name(book.getName())
                        .id(book.getId())
                        .build())
                .toList();

    }
}
