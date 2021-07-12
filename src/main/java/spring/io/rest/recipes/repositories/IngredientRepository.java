package spring.io.rest.recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.io.rest.recipes.models.entities.Ingredient;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("select ingredient from Ingredient ingredient where upper(ingredient.name) like upper(concat(:name,'%'))")
    List<Ingredient> findIngredientsStartingWith(String name);

    Optional<Ingredient> findByName(String name);

    List<Ingredient> findIngredientsByNameIn(List<String> names);
}
