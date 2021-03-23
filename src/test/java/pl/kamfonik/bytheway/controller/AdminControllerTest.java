package pl.kamfonik.bytheway.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kamfonik.bytheway.service.interfaces.CategoryService;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @Mock
    private CategoryService categoryService;

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void shouldInitializeCategories(){
        //given

        //when
        adminController.initializeCategories();

        //then
        verify(categoryService,times(1)).initialize();
    }

    @Test
    void shouldRedirectFromInitializeToApp(){
        //given
        String expected = "redirect:/app";

        //when
        String actual = adminController.initializeCategories();

        //then
        assertEquals(expected,actual);
    }

    @Test
    void shouldClearCategories(){
        //given

        //when
        adminController.clearPlaces();

        //then
        verify(placeService,times(1)).clear();
    }

    @Test
    void shouldRedirectFromClearToApp(){
        //given
        String expected = "redirect:/app";

        //when
        String actual = adminController.clearPlaces();

        //then
        assertEquals(expected,actual);
    }
}