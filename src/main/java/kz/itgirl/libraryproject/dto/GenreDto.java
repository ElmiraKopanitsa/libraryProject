package kz.itgirl.libraryproject.dto;

import com.fasterxml.jackson.annotation.JsonView;
import kz.itgirl.libraryproject.view.Views;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonView(Views.Private.class)
public class GenreDto {

    private Long id;

    private String name;

    private List<BookDto> books;
}
