package kz.itgirl.libraryproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import kz.itgirl.libraryproject.dto.GenreDto;
import kz.itgirl.libraryproject.service.GenreService;
import kz.itgirl.libraryproject.view.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @JsonView(Views.Private.class)
    @GetMapping("/genre/{id}")
    GenreDto getBooksByGenre (@PathVariable("id") Long id) {
        return genreService.getBooksByGenre(id);
    }

}
