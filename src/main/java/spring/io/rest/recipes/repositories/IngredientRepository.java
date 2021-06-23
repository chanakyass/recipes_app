package spring.io.rest.recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.io.rest.recipes.models.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
