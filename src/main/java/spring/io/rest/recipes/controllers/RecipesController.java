package spring.io.rest.recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.io.rest.recipes.services.RecipeCRUDServices;
import spring.io.rest.recipes.services.dtos.entities.RecipeDto;
import spring.io.rest.recipes.services.dtos.entities.responses.ApiMessageResponse;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("${app.uri.prefix}")
@RolesAllowed("ROLE_USER")
public class RecipesController {

    private final RecipeCRUDServices recipeCRUDServices;

    @Autowired
    public RecipesController(RecipeCRUDServices recipeCRUDServices) {
        this.recipeCRUDServices = recipeCRUDServices;
    }

    @PostMapping("recipe")
    public ResponseEntity<ApiMessageResponse> addRecipe(@RequestBody RecipeDto recipeDto){
        Long generatedId = recipeCRUDServices.addRecipe(recipeDto);
        return ResponseEntity.ok(ApiMessageResponse.defaultCreationSuccessResponse(generatedId));
    }

    @PutMapping("recipe")
    public ResponseEntity<ApiMessageResponse> updateRecipe(@RequestBody RecipeDto recipeDto){
        recipeCRUDServices.modifyRecipe(recipeDto);
        return ResponseEntity.ok(ApiMessageResponse.defaultSuccessResponse());
    }

    @DeleteMapping("recipe/{recipeId}")
    public ResponseEntity<ApiMessageResponse> deleteRecipe(@PathVariable("recipeId") Long recipeId){
        recipeCRUDServices.deleteRecipe(recipeId);
        return ResponseEntity.ok(ApiMessageResponse.defaultSuccessResponse());
    }

    @GetMapping("recipes")
    public ResponseEntity<List<RecipeDto>> getRecipes(){
        List<RecipeDto> recipeDtoList = recipeCRUDServices.getAllRecipes();
        return ResponseEntity.ok().body(recipeDtoList);
    }

    @GetMapping("recipe/{recipeId}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable("recipeId") Long recipeId){
        return ResponseEntity.ok().body(recipeCRUDServices.getRecipeWithId(recipeId));

    }

}
