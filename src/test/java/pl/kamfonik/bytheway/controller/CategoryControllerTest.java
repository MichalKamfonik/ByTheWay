package pl.kamfonik.bytheway.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import pl.kamfonik.bytheway.entity.Category;
import pl.kamfonik.bytheway.entity.CurrentUser;
import pl.kamfonik.bytheway.entity.User;
import pl.kamfonik.bytheway.service.interfaces.CategoryService;
import pl.kamfonik.bytheway.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserService userService;
    @Mock
    private BindingResult result;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void shouldAddToModel(){
        //given
        Model model = new ConcurrentModel();
        User user = new User();
        CurrentUser currentUser = new CurrentUser("a","a", new ArrayList<>(),user);
        List<Category> categories = List.of(new Category(),new Category());
        when(categoryService.findAllCategories()).thenReturn(categories);

        //when
        categoryController.manageCategoriesForm(model,currentUser);

        //then
        assertEquals(user,model.getAttribute("user"));
        assertEquals(categories,model.getAttribute("categories"));
    }

    @Test
    void shouldReturnUserForm(){
        //given
        Model model = new ConcurrentModel();
        CurrentUser currentUser = new CurrentUser("a","a", new ArrayList<>(),new User());
        String expected = "app/categories";

        //when
        String actual = categoryController.manageCategoriesForm(model,currentUser);

        //then
        assertEquals(expected,actual);
    }

    @Test
    void shouldAddCategoriesAndRedirectToFormWhenBindingErrors(){
        //given
        User user = new User();
        Model model = new ConcurrentModel();
        CurrentUser currentUser = new CurrentUser("a","a", new ArrayList<>(),new User());
        List<Category> categories = List.of(new Category(),new Category());

        when(categoryService.findAllCategories()).thenReturn(categories);
        when(result.hasErrors()).thenReturn(true);

        String expected = "app/categories";

        //when
        String actual = categoryController.manageCategories(user, result, model, currentUser);

        //then
        assertEquals(categories,model.getAttribute("categories"));
        assertEquals(actual,expected);
    }

    @Test
    void shouldUpdateUserCategoriesAndRedirectToAppWhenNoErrors(){
        //given
        List<Category> categories = List.of(new Category(),new Category());
        User user = new User();
        user.setFavoriteCategories(categories);

        Model model = new ConcurrentModel();

        User loggedUser = new User();
        CurrentUser currentUser = new CurrentUser("a","a", new ArrayList<>(),loggedUser);

        when(result.hasErrors()).thenReturn(false);

        String expected = "redirect:/app";

        //when
        String actual = categoryController.manageCategories(user, result, model, currentUser);

        //then
        verify(userService,times(1)).updateUser(loggedUser);
        assertEquals(currentUser.getUser().getFavoriteCategories(),user.getFavoriteCategories());
        assertEquals(expected,actual);
    }
}