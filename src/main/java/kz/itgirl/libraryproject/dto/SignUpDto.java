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
public class SignUpDto {

    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Surname required")
    private String surname;
    @NotBlank(message = "Login required")
    @Size(min = 5, max = 20)
    private String login;
    @NotBlank(message = "Password required")
    @Size(min = 5, max = 20)
    private String password;
}
