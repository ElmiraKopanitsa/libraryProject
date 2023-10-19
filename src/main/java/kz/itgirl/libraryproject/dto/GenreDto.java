package kz.itgirl.libraryproject.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GenreDto {

    private Long id;

    private String name;

    private List<BookDto> books;
}
