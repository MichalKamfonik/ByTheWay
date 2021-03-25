package pl.kamfonik.bytheway.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import pl.kamfonik.bytheway.converter.Entity2DtoConverter;
import pl.kamfonik.bytheway.dto.rest.PlaceDto;
import pl.kamfonik.bytheway.entity.Place;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceRestControllerTest {
    @Mock
    private PlaceService placeService;
    @Mock
    private Entity2DtoConverter<Place, PlaceDto> placeEntity2DtoConverter;
    @Mock
    private Entity2DtoConverter<List<Place>, List<PlaceDto>> placeListEntity2DtoConverter;

    @InjectMocks
    private PlaceRestController placeRestController;

    // findPlace tests
    @Test
    void shouldReturnStatusOK(){
        //given
        String query = "test query";
        Place place = new Place();
        PlaceDto placeDto = new PlaceDto();
        when(placeService.findPlaceByQuery(query)).thenReturn(Optional.of(place));
        when(placeEntity2DtoConverter.convert(place)).thenReturn(placeDto);

        ResponseEntity<PlaceDto> expected = ResponseEntity.ok(placeDto);

        //when
        ResponseEntity<PlaceDto> actual = placeRestController.findPlace(query);

        //then
        assertEquals(expected,actual);
    }
}