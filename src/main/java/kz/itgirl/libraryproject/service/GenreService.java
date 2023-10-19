package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.GenreDto;

public interface GenreService {

    GenreDto getBooksByGenre(Long id);
}
