package spring.io.rest.recipes.services.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.io.rest.recipes.models.entities.Ingredient;
import spring.io.rest.recipes.models.entities.Recipe;
import spring.io.rest.recipes.models.entities.RecipeIngredient;
import spring.io.rest.recipes.repositories.IngredientRepository;
import spring.io.rest.recipes.services.dtos.IngredientDto;
import spring.io.rest.recipes.services.dtos.RecipeDto;
import spring.io.rest.recipes.services.dtos.RecipeIngredientDto;
import spring.io.rest.recipes.services.dtos.mappers.IngredientMapper;
import spring.io.rest.recipes.services.dtos.mappers.RecipeIngredientMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RecipeServiceUtil {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;

    @Autowired
    public RecipeServiceUtil(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper, RecipeIngredientMapper recipeIngredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    public void saveUnavailableIngredients(RecipeDto recipeDto) {
        List<Ingredient> unavailableIngredients = new ArrayList<>();
        List<IngredientDto> unavailableIngredientDtos = new ArrayList<>();

        recipeDto.getRecipeIngredients().stream().filter(recipeIngredientDto -> recipeIngredientDto.getIngredient().getId() == null
                || ingredientRepository.findById(recipeIngredientDto.getIngredient().getId()).isEmpty())
                .forEach(recipeIngredientDto -> {
                    if(recipeIngredientDto.getIngredient().getId() != null) {
                        recipeIngredientDto.getIngredient().setId(null);
                    }
                    unavailableIngredients.add(ingredientMapper.toIngredient(recipeIngredientDto.getIngredient()));
                    unavailableIngredientDtos.add(recipeIngredientDto.getIngredient());
                });

        ingredientRepository.saveAll(unavailableIngredients);

        IntStream.range(0, unavailableIngredients.size())
                .forEach(i -> unavailableIngredientDtos.get(i).setId(unavailableIngredients.get(i).getId()));
    }

    public void addOrRemoveRecipeIngredients(RecipeDto recipeDto, Recipe recipe) {
        List<RecipeIngredient> recipeIngredientList = recipe.getRecipeIngredients();
        List<RecipeIngredientDto> updatedRecipeIngredientList = recipeDto.getRecipeIngredients();

        List<RecipeIngredient> extraIngredients = updatedRecipeIngredientList.stream()
                .filter(recipeIngredientDto -> recipeIngredientDto.getId() == null)
                .map(recipeIngredientDto -> {
                    RecipeIngredient recipeIngredient = recipeIngredientMapper.toRecipeIngredient(recipeIngredientDto);
                    recipeIngredient.setRecipe(recipe);
                    return recipeIngredient;
                }).collect(Collectors.toList());

        Set<Long> updatedIngredientsSet = updatedRecipeIngredientList.stream()
                .map(RecipeIngredientDto::getId)
                .filter(Objects::nonNull).collect(Collectors.toSet());

        List<RecipeIngredient> removedIngredients = recipeIngredientList.stream()
                .filter(recipeIngredient -> !updatedIngredientsSet.contains(recipeIngredient.getId()))
                .collect(Collectors.toList());

        recipeIngredientList.removeAll(removedIngredients);
        recipeIngredientList.addAll(extraIngredients);
    }

}
