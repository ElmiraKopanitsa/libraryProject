package kz.itgirl.libraryproject.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import kz.itgirl.libraryproject.dto.BookCreateDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.dto.BookUpdateDto;
import kz.itgirl.libraryproject.service.BookService;
import kz.itgirl.libraryproject.view.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class BookRestController {

    private final BookService bookService;

    @JsonView(Views.Internal.class)
    @GetMapping("/book")
    BookDto getBookByName(@RequestParam("name") String name) {
        return bookService.getByNameV1(name);
    }

    @JsonView(Views.Internal.class)
    @GetMapping("/book/v2")
    BookDto getBookByNameV2(@RequestParam("name") String name) {
        return bookService.getByNameV2(name);
    }

    @JsonView(Views.Internal.class)
    @GetMapping("/book/v3")
    BookDto getBookByNameV3(@RequestParam("name") String name) {
        return bookService.getByNameV3(name);
    }

    @PostMapping("/book/create")
    BookDto createBook(@RequestBody @Valid BookCreateDto bookCreateDto) {
        return bookService.createBook(bookCreateDto);
    }

    @PutMapping("/book/update")
    BookDto updateBook(@RequestBody @Valid BookUpdateDto bookUpdateDto) {
        return bookService.updateBook(bookUpdateDto);
    }

    @DeleteMapping("/book/delete/{id}")
    String deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }
}
