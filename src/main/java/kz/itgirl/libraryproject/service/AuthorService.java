package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.AuthorCreateDto;
import kz.itgirl.libraryproject.dto.AuthorDto;

public interface AuthorService {

    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);

    AuthorDto getAuthorByNameV2(String name);

    AuthorDto getAuthorByNameV3(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);
}
