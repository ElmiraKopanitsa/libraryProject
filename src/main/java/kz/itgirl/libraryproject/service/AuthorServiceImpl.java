package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.AuthorCreateDto;
import kz.itgirl.libraryproject.dto.AuthorUpdateDto;
import kz.itgirl.libraryproject.model.Author;
import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Author author = authorRepository.findById(id).orElseThrow();
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Find author: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        log.info("Try to find author by name {}", name);
        Author author = authorRepository.findAuthorByName(name).orElseThrow();
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Find author: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        log.info("Try to find author by name {}", name);
        Author author = authorRepository.findAuthorByNameSql(name).orElseThrow();
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Find author: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto getAuthorByNameV3(String name) {
        log.info("Try to find author by name {}", name);
        Specification<Author> specification = Specification.where((Specification<Author>)
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name));
        Author author = authorRepository.findOne(specification).orElseThrow();
        AuthorDto authorDto = convertEntityToDto(author);
        log.info("Find author: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
            log.info("Try create author: {}", authorCreateDto.toString());
            Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
            AuthorDto authorDto = convertEntityToDto(author);
            log.info("Create author: {}", authorDto.toString());
            return authorDto;
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Try update author: {}", authorUpdateDto.toString());
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author saveAuthor = authorRepository.save(author);
        AuthorDto authorDto = convertEntityToDto(saveAuthor);
        log.info("Update author: {}", authorDto.toString());
        return authorDto;
    }

    @Override
    public String deleteAuthor(Long id) {
        authorRepository.deleteById(id);
        return "Author removed.";
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authorList = authorRepository.findAll();
        return authorList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return  Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
    }
}