package kz.itgirl.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import kz.itgirl.libraryproject.dto.*;
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
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private BookDto bookDto;
    private AuthorDto authorDto;
    private List<AuthorDto> authorDtoList;

    @BeforeEach
    public void setUp() {
       authorDto = AuthorDto.builder()
                .id(2L)
                .name("Николай")
                .surname("Гоголь")
                .build();
       authorDtoList = new ArrayList<>();
       authorDtoList.add(authorDto);

       bookDto = BookDto.builder()
                .id(3L)
                .name("Нос")
                .genre("Рассказ")
                .authors(authorDtoList)
                .build();
    }

    @Test
    public void testGetBookByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                        .param("name", bookDto.getName()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testCreateBook() throws Exception {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setName("Java");
        bookCreateDto.setGenre("Comedy");
        bookCreateDto.setAuthors(authorDtoList);

        String requestBody = new ObjectMapper().writeValueAsString(bookCreateDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookCreateDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookCreateDto.getGenre()));
    }

    @Test
    public void testUpdateBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(3L);
        bookUpdateDto.setName("Нос");
        bookUpdateDto.setGenre("Рассказ");
        bookUpdateDto.setAuthors(authorDtoList);

        String requestBody = new ObjectMapper().writeValueAsString(bookUpdateDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/book/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookUpdateDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookUpdateDto.getGenre()));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/{id}", bookDto.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book removed."));
    }
}
