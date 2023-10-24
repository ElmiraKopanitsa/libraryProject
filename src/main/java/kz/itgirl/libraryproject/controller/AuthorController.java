package kz.itgirl.libraryproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.service.AuthorService;
import kz.itgirl.libraryproject.view.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @JsonView(Views.Internal.class)
    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }
}