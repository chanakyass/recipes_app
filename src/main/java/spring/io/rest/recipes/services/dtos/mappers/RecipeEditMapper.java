package spring.io.rest.recipes.services.dtos.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import spring.io.rest.recipes.models.entities.Recipe;
import spring.io.rest.recipes.models.entities.RecipeIngredient;
import spring.io.rest.recipes.services.dtos.RecipeDto;
import spring.io.rest.recipes.services.dtos.RecipeIngredientDto;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public abstract class RecipeEditMapper {

    private RecipeIngredientMapper recipeIngredientMapper;

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "recipeIngredients", ignore = true)
    public abstract void updateRecipe(RecipeDto request, @MappingTarget Recipe recipe);

    @AfterMapping
    protected void afterUpdateRecipe(RecipeDto request, @MappingTarget Recipe recipe){
        List<RecipeIngredient> recipeIngredientList = recipe.getRecipeIngredients();
        List<RecipeIngredientDto> updatedRecipeIngredientList = request.getRecipeIngredients();
        int lengthOfList = recipeIngredientList.size();

        for(int i=0; i<lengthOfList; i++){
            RecipeIngredient ingredientBeforeUpdate = recipeIngredientList.get(i);
            RecipeIngredientDto ingredientAfterUpdate = updatedRecipeIngredientList.get(i);
            if(ingredientBeforeUpdate.getRecipe() != null){
                ingredientBeforeUpdate.setRecipe(recipe);
            }
            recipeIngredientMapper.updateRecipeIngredient(ingredientAfterUpdate, ingredientBeforeUpdate);
        }

    }

    public RecipeIngredientMapper getRecipeIngredientMapper() {
        return recipeIngredientMapper;
    }

    @Autowired
    public void setRecipeIngredientMapper(RecipeIngredientMapper recipeIngredientMapper) {
        this.recipeIngredientMapper = recipeIngredientMapper;
    }
}
