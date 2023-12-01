package kz.itgirl.libraryproject.dto;

import jakarta.validation.constraints.*;
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
public class BookDto {

    private Long id;
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Genre required")
    @JsonView(Views.Internal.class)
    private String genre;
    @JsonView(Views.Private.class)
    private List<AuthorDto> authors;
}