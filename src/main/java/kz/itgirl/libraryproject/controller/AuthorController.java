package kz.itgirl.libraryproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import kz.itgirl.libraryproject.dto.AuthorCreateDto;
import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.service.AuthorService;
import kz.itgirl.libraryproject.view.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @JsonView(Views.Internal.class)
    @GetMapping("/author/id/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @JsonView(Views.Internal.class)
    @GetMapping("/author/name/{name}")
    AuthorDto getAuthorByName(@PathVariable("name") String name) {
        return authorService.getAuthorByNameV1(name);
    }

    @JsonView(Views.Internal.class)
    @GetMapping("/author/name/v2/{name}")
    AuthorDto getAuthorByNameV2(@PathVariable("name") String name) {
        return authorService.getAuthorByNameV2(name);}

    @JsonView(Views.Internal.class)
    @GetMapping("author/name/v3/{name}")
    AuthorDto getAuthorByNameV3(@PathVariable("name") String name) { return authorService.getAuthorByNameV3(name); }

    @PostMapping("/author/create")
    AuthorDto createAuthor(@RequestBody AuthorCreateDto authorCreateDto) {
        return authorService.createAuthor(authorCreateDto);
    }
}