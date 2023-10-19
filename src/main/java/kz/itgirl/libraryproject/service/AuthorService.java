package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.AuthorDto;

public interface AuthorService {

    AuthorDto getAuthorById(Long id);
}
