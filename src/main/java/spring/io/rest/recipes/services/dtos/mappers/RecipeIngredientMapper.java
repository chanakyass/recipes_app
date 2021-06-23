package spring.io.rest.recipes.services.dtos.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import spring.io.rest.recipes.models.entities.RecipeIngredient;
import spring.io.rest.recipes.services.dtos.RecipeIngredientDto;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public abstract class RecipeIngredientMapper {
    public abstract RecipeIngredient toRecipeIngredient(RecipeIngredientDto recipeIngredientDto);
    public abstract RecipeIngredientDto toRecipeIngredientDto(RecipeIngredient recipeIngredient);
    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    public abstract void updateRecipeIngredient(RecipeIngredientDto request,
                                                @MappingTarget RecipeIngredient recipeIngredient);
}
