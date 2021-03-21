package pl.kamfonik.bytheway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pl.kamfonik.bytheway.converter.Entity2DtoConverter;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.service.implementations.SpringDataUserDetailsService;
import pl.kamfonik.bytheway.service.interfaces.CategoryService;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceRestController.class)
@TestPropertySource(locations = "classpath:application-tests.properties")
class PlaceRestControllerTestWithWebContextWithoutServer {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService placeService;
    @MockBean
    private Entity2DtoConverter<Place, PlaceDto> placeEntity2DtoConverter;
    @MockBean
    private Entity2DtoConverter<List<Place>, List<PlaceDto>> placeListEntity2DtoConverter;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private SpringDataUserDetailsService springDataUserDetailsService;

    @Test
    public void shouldFindPartynice() throws Exception {
        Place place = new Place();
        place.setName("Tor wyścigów konnych Partynice");
        PlaceDto placeDto = new PlaceDto();
        placeDto.setName("Tor wyścigów konnych Partynice");

        when(placeService.findPlaceByQuery("partynice")).thenReturn(Optional.of(place));
        when(placeEntity2DtoConverter.convert(place)).thenReturn(placeDto);

        mockMvc
                .perform(
                        post("/place/find")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content("query=partynice")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("partynice")));
    }
}