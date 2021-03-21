package pl.kamfonik.bytheway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-tests.properties")
class PlaceRestControllerTestWithFullContextWithoutServer {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    public void shouldFindPartynice() throws Exception {
        mockMvc
                .perform(
                        post("/place/find")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content("query=partynice")
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", containsStringIgnoringCase("partynice")));
    }

    @WithAnonymousUser
    @Test
    public void shouldRedirectToLogin() throws Exception {
        mockMvc
                .perform(
                        post("/place/find")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content("query=partynice")
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}