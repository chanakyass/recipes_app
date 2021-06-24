package spring.io.rest.recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.io.rest.recipes.models.entities.Ingredient;
import spring.io.rest.recipes.models.entities.Recipe;
import spring.io.rest.recipes.models.entities.RecipeIngredient;
import spring.io.rest.recipes.repositories.IngredientRepository;
import spring.io.rest.recipes.repositories.RecipeRepository;
import spring.io.rest.recipes.services.dtos.IngredientDto;
import spring.io.rest.recipes.services.dtos.RecipeDto;
import spring.io.rest.recipes.services.dtos.RecipeIngredientDto;
import spring.io.rest.recipes.services.dtos.mappers.IngredientMapper;
import spring.io.rest.recipes.services.dtos.mappers.RecipeEditMapper;
import spring.io.rest.recipes.services.dtos.mappers.RecipeIngredientMapper;
import spring.io.rest.recipes.services.dtos.mappers.RecipeMapper;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeCRUDServices {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeEditMapper recipeEditMapper;
    private final IngredientMapper ingredientMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;

    @Autowired
    public RecipeCRUDServices(RecipeRepository recipeRepository, IngredientRepository ingredientRepository,
                              RecipeMapper recipeMapper, RecipeEditMapper recipeEditMapper,
                              IngredientMapper ingredientMapper, RecipeIngredientMapper recipeIngredientMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
        this.recipeEditMapper = recipeEditMapper;
        this.ingredientMapper = ingredientMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    @Transactional
    public void addRecipe(RecipeDto recipeDto) {
        Optional.ofNullable(recipeDto).orElseThrow(()-> new IllegalStateException("Wrong format"));
        addUnavailableIngredients(recipeDto);
        Recipe recipe = recipeMapper.toRecipe(recipeDto);
        recipeRepository.save(recipe);
    }

    @Transactional
    public void modifyRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(recipeDto.getId()).orElseThrow(() -> new IllegalStateException("No such recipe"));
        addUnavailableIngredients(recipeDto);
        addOrRemoveIngredientsFromList(recipeDto, recipe);
        recipeEditMapper.updateRecipe(recipeDto, recipe);
    }

    @Transactional
    public void addOrRemoveIngredientsFromList(RecipeDto recipeDto, Recipe recipe) {
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

    @Transactional
    public void addUnavailableIngredients(RecipeDto recipeDto) {
        List<Ingredient> unavailableIngredients = new ArrayList<>();
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        for(RecipeIngredientDto recipeIngredientDto: recipeDto.getRecipeIngredients()) {
            if(recipeIngredientDto.getIngredient().getId() == null || ingredientRepository.findById(recipeIngredientDto.getIngredient().getId()).isEmpty()){
                unavailableIngredients.add(ingredientMapper.toIngredient(recipeIngredientDto.getIngredient()));
                ingredientDtoList.add(recipeIngredientDto.getIngredient());
            }
        }

        ingredientRepository.saveAll(unavailableIngredients);
        for(int i=0; i<unavailableIngredients.size(); i++) {
            ingredientDtoList.get(i).setId(unavailableIngredients.get(i).getId());
        }
    }

    public List<RecipeDto> getAllRecipes(){
        List<Recipe> recipesList =  recipeRepository.findAll();
        return recipeMapper.toRecipeDtoList(recipesList);
    }



}
