package kz.itgirl.libraryproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorCreateDto {

    @Size(min=3, max=10)
    @NotBlank(message="Name required")
    private String name;
    @NotBlank(message = "Surname required")
    private String surname;
}
