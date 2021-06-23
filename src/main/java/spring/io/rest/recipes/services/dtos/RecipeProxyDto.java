package spring.io.rest.recipes.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeProxyDto {
    private Long id;
    private String name;
    private UserProxyDto user;
}
