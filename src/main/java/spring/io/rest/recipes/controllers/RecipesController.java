package spring.io.rest.recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.io.rest.recipes.models.ApiMessageResponse;
import spring.io.rest.recipes.services.RecipeCRUDServices;
import spring.io.rest.recipes.services.dtos.RecipeDto;

import java.util.List;

@RestController
@RequestMapping("${app.uri.prefix}")
public class RecipesController {

    private final RecipeCRUDServices recipeCRUDServices;

    @Autowired
    public RecipesController(RecipeCRUDServices recipeCRUDServices) {
        this.recipeCRUDServices = recipeCRUDServices;
    }

    @PostMapping("recipe")
    public ResponseEntity<ApiMessageResponse> addRecipe(@RequestBody RecipeDto recipeDto){
        recipeCRUDServices.addRecipe(recipeDto);
        return ResponseEntity.ok(ApiMessageResponse.defaultSuccessResponse());
    }

    @PutMapping("recipe")
    public ResponseEntity<ApiMessageResponse> updateRecipe(@RequestBody RecipeDto recipeDto){
        recipeCRUDServices.modifyRecipe(recipeDto);
        return ResponseEntity.ok(ApiMessageResponse.defaultSuccessResponse());
    }

    @GetMapping("recipes")
    public List<RecipeDto> getRecipes(){
        return recipeCRUDServices.getAllRecipes();
    }
}
