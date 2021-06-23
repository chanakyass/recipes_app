package spring.io.rest.recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.io.rest.recipes.models.entities.Ingredient;
import spring.io.rest.recipes.models.entities.Recipe;
import spring.io.rest.recipes.models.entities.RecipeIngredient;
import spring.io.rest.recipes.repositories.IngredientRepository;
import spring.io.rest.recipes.repositories.RecipeRepository;
import spring.io.rest.recipes.services.dtos.RecipeDto;
import spring.io.rest.recipes.services.dtos.mappers.RecipeEditMapper;
import spring.io.rest.recipes.services.dtos.mappers.RecipeMapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeCRUDServices {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeEditMapper recipeEditMapper;

    @Autowired
    public RecipeCRUDServices(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                              RecipeMapper recipeMapper, RecipeEditMapper recipeEditMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
        this.recipeEditMapper = recipeEditMapper;
    }

    @Transactional
    public void addRecipe(RecipeDto recipeDto){
        Optional.ofNullable(recipeDto).orElseThrow(()-> new IllegalStateException("Wrong format"));
        Recipe recipe = recipeMapper.toRecipe(recipeDto);

        List<Ingredient> unavailableIngredients = new ArrayList<>();

        for(RecipeIngredient recipeIngredient: recipe.getRecipeIngredients()){
            if(recipeIngredient.getId() == null || ingredientRepository.findById(recipeIngredient.getId()).isEmpty()){
                unavailableIngredients.add(recipeIngredient.getIngredient());
            }
        }

        ingredientRepository.saveAll(unavailableIngredients);

        recipeRepository.save(recipe);
    }

    @Transactional
    public void modifyRecipe(RecipeDto recipeDto){
        Recipe recipe = recipeRepository.findById(recipeDto.getId()).orElseThrow(() -> new IllegalStateException("No such recipe"));
        recipeEditMapper.updateRecipe(recipeDto, recipe);
    }

    public List<RecipeDto> getAllRecipes(){
        List<Recipe> recipesList =  recipeRepository.findAll();
        return recipeMapper.toRecipeDtoList(recipesList);
    }



}
