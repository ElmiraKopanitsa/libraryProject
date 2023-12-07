package kz.itgirl.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kz.itgirl.libraryproject.dto.AuthorCreateDto;
import kz.itgirl.libraryproject.dto.AuthorDto;
import kz.itgirl.libraryproject.dto.AuthorUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
public class AuthorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private AuthorDto authorDto;

    @BeforeEach
    public void setUp() {
        authorDto = AuthorDto.builder()
                .id(1L)
                .name("Александр")
                .surname("Пушкин")
                .build();
    }

    @Test
    public void testGetAuthorById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/author/id/{id}", authorDto.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testGetAuthorByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/author/name/{name}", authorDto.getName()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }

    @Test
    public void testCreateAuthor() throws Exception {
        AuthorCreateDto authorCreateDto = new AuthorCreateDto();
        authorCreateDto.setName("John");
        authorCreateDto.setSurname("Doe");

        String requestBody = new ObjectMapper().writeValueAsString(authorCreateDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorCreateDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorCreateDto.getSurname()));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto();
        authorUpdateDto.setId(8L);
        authorUpdateDto.setName("John");
        authorUpdateDto.setSurname("Doe");

        String requestBody = new ObjectMapper().writeValueAsString(authorUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/author/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorUpdateDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorUpdateDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorUpdateDto.getSurname()));
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/author/delete/{id}", authorDto.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Author removed."));
    }

}
