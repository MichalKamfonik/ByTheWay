package pl.kamfonik.bytheway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kamfonik.bytheway.service.interfaces.CategoryService;
import pl.kamfonik.bytheway.service.interfaces.PlaceService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CategoryService categoryService;
    private final PlaceService placeService;

    @GetMapping("/initialize-categories")
    public String initializeCategories() {
        categoryService.initialize();
        return "redirect:/app";
    }

    @GetMapping("/clear-places")
    public String clearPlaces() {
        placeService.clear();
        return "redirect:/app";
    }
}
