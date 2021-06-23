package spring.io.rest.recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.io.rest.recipes.models.entities.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
