package kz.itgirl.libraryproject.dto;

import com.fasterxml.jackson.annotation.JsonView;
import kz.itgirl.libraryproject.view.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonView(Views.Public.class)
public class AuthorDto {

    private Long id;

    private String name;

    private String surname;
    @JsonView(Views.Internal.class)
    private List<BookDto> books;
}