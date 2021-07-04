package spring.io.rest.recipes.unittests.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.web.context.annotation.ApplicationScope;
import spring.io.rest.recipes.models.entities.Ingredient;
import spring.io.rest.recipes.models.entities.Recipe;
import spring.io.rest.recipes.models.entities.RecipeIngredient;
import spring.io.rest.recipes.services.dtos.IngredientDto;
import spring.io.rest.recipes.services.dtos.RecipeDto;
import spring.io.rest.recipes.services.dtos.RecipeIngredientDto;
import spring.io.rest.recipes.services.dtos.mappers.IngredientMapper;
import spring.io.rest.recipes.services.dtos.mappers.RecipeIngredientMapper;
import spring.io.rest.recipes.unittests.data.AbstractTestDataFactory;
import spring.io.rest.recipes.unittests.data.RecipeTestDataFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeIngredientMapperTest {
    private List<RecipeIngredientDto> recipeIngredientDtoList;
    private List<RecipeIngredient> recipeIngredientList;
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        RecipeTestDataFactory recipeTestDataFactory = AbstractTestDataFactory.getRecipeTestDataFactory();
        recipeIngredientDtoList = recipeTestDataFactory.getRandomRecipeDto(1L).getRecipeIngredients();
        for(RecipeIngredientDto recipeIngredientDto: recipeIngredientDtoList) {
            recipeIngredientDto.setQuantity(234.0);
        }
        recipe = recipeTestDataFactory.getRandomRecipe(1L);
        recipeIngredientList = recipe.getRecipeIngredients();
    }

    @Test
    void updatedRecipeIngredientList() {
        RecipeIngredientMapper recipeIngredientMapper = Mappers.getMapper(RecipeIngredientMapper.class);
        recipeIngredientMapper = Mockito.spy(recipeIngredientMapper);
        doNothing().when(recipeIngredientMapper).updateRecipeIngredient(any(RecipeIngredientDto.class), any(RecipeIngredient.class));
        recipeIngredientMapper.updatedRecipeIngredientList(recipeIngredientDtoList, recipeIngredientList, recipe);
        IntStream.range(0, recipeIngredientDtoList.size())
                .forEach(i -> {
                    assertEquals(recipeIngredientList.get(i).getRecipe(), recipe);
                });
    }
}