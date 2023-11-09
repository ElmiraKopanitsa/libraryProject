package kz.itgirl.libraryproject.service;

import kz.itgirl.libraryproject.dto.AuthorCreateDto;
import kz.itgirl.libraryproject.dto.AuthorUpdateDto;
import kz.itgirl.libraryproject.model.Author;
import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.dto.BookDto;
import kz.itgirl.libraryproject.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV1(String name) {
        Author author = authorRepository.findAuthorByName(name).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV2(String name) {
        Author author = authorRepository.findAuthorByNameSql(name).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDto getAuthorByNameV3(String name) {
        Specification<Author> specification = Specification.where((Specification<Author>)
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name));
        Author author = authorRepository.findOne(specification).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
            Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
            return convertEntityToDto(author);
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();
        author.setName(authorUpdateDto.getName());
        author.setSurname(authorUpdateDto.getSurname());
        Author saveAuthor = authorRepository.save(author);
        return convertEntityToDto(saveAuthor);
    }

    @Override
    public String deleteAuthor(Long id) {
        authorRepository.deleteById(id);
        return "Author removed.";
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