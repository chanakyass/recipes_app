package spring.io.rest.recipes.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.io.rest.recipes.enums.UnitOfMeasurement;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto {
    private Long id;
    private IngredientDto ingredient;
    private Double quantity;
    private UnitOfMeasurement uom;
}
