package project.carsharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import project.carsharing.dto.user.UserDto;
import project.carsharing.dto.user.UserLoginRequestDto;
import project.carsharing.dto.user.UserLoginResponseDto;
import project.carsharing.dto.user.UserRegistrationDto;
import project.carsharing.model.Role;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("""
                Register new user, returns true
            """)
    void register_shouldReturnUserDto_whenRequestIsValid() throws Exception {
        UserRegistrationDto requestDto = new UserRegistrationDto();
        requestDto.setFirstName("firstName");
        requestDto.setLastName("lastName");
        requestDto.setEmail("email@email.com");
        requestDto.setPassword("password");
        requestDto.setRepeatPassword("password");

        UserDto expected = new UserDto(
                requestDto.getEmail(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                Role.CUSTOMER
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                post("/api/auth/register")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        UserDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), UserDto.class);

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    void login_shouldReturnNotNull_whenRequestIsValid() throws Exception {
        UserLoginRequestDto requestDto = new UserLoginRequestDto();
        requestDto.setEmail("email@email.com");
        requestDto.setPassword("password");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                post("api/auth/login")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        UserLoginResponseDto actual = objectMapper
                .readValue(result.getResponse().getContentAsString(), UserLoginResponseDto.class);

        assertNotNull(actual);
    }

}