package spring.io.rest.recipes.services.dtos.mappers;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import spring.io.rest.recipes.models.entities.Recipe;
import spring.io.rest.recipes.models.entities.RecipeIngredient;
import spring.io.rest.recipes.services.dtos.RecipeDto;
import spring.io.rest.recipes.services.dtos.RecipeIngredientDto;

import java.util.ArrayList;
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
        List<RecipeIngredient> removedIngredients = new ArrayList<>();

        recipeIngredientList.sort((obj1, obj2) -> {
            if(obj1.getId() < obj2.getId())
                return -1;
            else if(obj1.getId().equals(obj2.getId()))
                return 0;
            return 1;
        });

        updatedRecipeIngredientList.sort((obj1, obj2) -> {
            if(obj1.getId() < obj2.getId())
                return -1;
            else if(obj1.getId().equals(obj2.getId()))
                return 0;
            return 1;
        });

        int lengthOfIngredients = recipeIngredientList.size();
        int lengthOfUpdatedIngredients = updatedRecipeIngredientList.size();

        if(lengthOfIngredients > lengthOfUpdatedIngredients) {
            for(int i=0, j=0; i<lengthOfIngredients;){
                RecipeIngredient ingredientBeforeUpdate = recipeIngredientList.get(i);
                RecipeIngredientDto ingredientAfterUpdate = updatedRecipeIngredientList.get(j);
                if(ingredientBeforeUpdate.getId().equals(ingredientAfterUpdate.getId())){
                    recipeIngredientMapper.updateRecipeIngredient(ingredientAfterUpdate, ingredientBeforeUpdate);
                    i++;
                    j++;
                }
                else{
                    removedIngredients.add(ingredientBeforeUpdate);
                    i++;
                }
            }
            recipeIngredientList.removeAll(removedIngredients);
        }
        else if(lengthOfIngredients < lengthOfUpdatedIngredients){
            int i = 0;
            for(; i<lengthOfIngredients;i++){
                RecipeIngredient ingredientBeforeUpdate = recipeIngredientList.get(i);
                RecipeIngredientDto ingredientAfterUpdate = updatedRecipeIngredientList.get(i);
                recipeIngredientMapper.updateRecipeIngredient(ingredientAfterUpdate, ingredientBeforeUpdate);
            }

            for(;i<lengthOfUpdatedIngredients; i++){
                RecipeIngredientDto ingredientAfterUpdate = updatedRecipeIngredientList.get(i);
                recipeIngredientList.add(recipeIngredientMapper.toRecipeIngredient(ingredientAfterUpdate));
            }
        }
        else{
            for(int i=0; i<lengthOfIngredients; i++){
                RecipeIngredient ingredientBeforeUpdate = recipeIngredientList.get(i);
                RecipeIngredientDto ingredientAfterUpdate = updatedRecipeIngredientList.get(i);
                recipeIngredientMapper.updateRecipeIngredient(ingredientAfterUpdate, ingredientBeforeUpdate);
            }
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
