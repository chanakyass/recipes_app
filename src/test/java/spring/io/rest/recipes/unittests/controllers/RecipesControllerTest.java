package spring.io.rest.recipes.unittests.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.io.rest.recipes.controllers.RecipesController;
import spring.io.rest.recipes.services.RecipeCRUDServices;
import spring.io.rest.recipes.services.dtos.entities.RecipeDto;
import spring.io.rest.recipes.services.dtos.entities.responses.ApiMessageResponse;
import spring.io.rest.recipes.unittests.data.AbstractTestDataFactory;
import spring.io.rest.recipes.unittests.data.RecipeTestDataFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipesControllerTest {

    @Mock
    private RecipeCRUDServices recipeCRUDServices;

    @InjectMocks
    private RecipesController recipesController;

    private RecipeDto recipeDto;
    private List<RecipeDto> recipeDtoList;

    @BeforeEach
    void setUp() {
        RecipeTestDataFactory recipeTestDataFactory = AbstractTestDataFactory.getRecipeTestDataFactory();
        recipeDto = recipeTestDataFactory.getRandomRecipeDto(1L);
        recipeDtoList = recipeTestDataFactory.getRandomRecipeDtoList();

    }

    @Test
    void addRecipe() {
        when(recipeCRUDServices.addRecipe(any(RecipeDto.class))).thenReturn(recipeDto.getId());
        ResponseEntity<ApiMessageResponse> responseEntity = recipesController.addRecipe(recipeDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getGeneratedId(), recipeDto.getId());
    }

    @Test
    void updateRecipe() {
        doNothing().when(recipeCRUDServices).modifyRecipe(any(RecipeDto.class));
        ResponseEntity<ApiMessageResponse> responseEntity = recipesController.updateRecipe(recipeDto);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteRecipe() {
        doNothing().when(recipeCRUDServices).deleteRecipe(anyLong());
        ResponseEntity<ApiMessageResponse> responseEntity = recipesController.deleteRecipe(recipeDto.getId());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getRecipes() {
        when(recipeCRUDServices.getAllRecipes()).thenReturn(recipeDtoList);
        ResponseEntity<List<RecipeDto>> responseEntity = recipesController.getRecipes();
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), recipeDtoList);
    }

    @Test
    void getRecipe() {
        when(recipeCRUDServices.getRecipeWithId(anyLong())).thenReturn(recipeDto);
        ResponseEntity<RecipeDto> responseEntity = recipesController.getRecipe(recipeDto.getId());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), recipeDto);
    }
}