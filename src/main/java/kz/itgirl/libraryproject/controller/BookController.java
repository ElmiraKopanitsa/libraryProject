package kz.itgirl.libraryproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.service.BookService;
import kz.itgirl.libraryproject.view.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

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
    @GetMapping("book/v3")
    BookDto getBookByNameV3(@RequestParam("name") String name) {
        return bookService.getByNameV3(name);
    }
}
