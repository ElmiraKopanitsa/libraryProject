package kz.itgirl.libraryproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookUpdateDto {

    private Long id;
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Genre required")
    private String genre;
    private List<AuthorDto> authors;
}
