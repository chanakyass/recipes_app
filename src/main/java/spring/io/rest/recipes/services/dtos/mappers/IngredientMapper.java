package spring.io.rest.recipes.services.dtos.mappers;

import org.mapstruct.Mapper;
import spring.io.rest.recipes.models.entities.Ingredient;
import spring.io.rest.recipes.services.dtos.IngredientDto;

@Mapper(componentModel = "spring")
public abstract class IngredientMapper {
    public abstract Ingredient toIngredient(IngredientDto ingredientDto);
    public abstract IngredientDto toIngredientDto(Ingredient ingredient);
}
