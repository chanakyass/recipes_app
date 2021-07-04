package spring.io.rest.recipes.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.io.rest.recipes.services.IngredientService;
import spring.io.rest.recipes.services.dtos.IngredientDto;
import spring.io.rest.recipes.services.responses.ApiMessageResponse;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("${app.uri.prefix}/admin")
@RolesAllowed("ROLE_ADMIN")
public class AdminSpecificController {

    private final IngredientService ingredientService;

    @Autowired
    public AdminSpecificController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("ingredients")
    public ResponseEntity<ApiMessageResponse> addIngredients(List<IngredientDto> ingredientDtoList) {
        ingredientService.addListOfIngredients(ingredientDtoList);
        return ResponseEntity.ok().body(ApiMessageResponse.defaultSuccessResponse());
    }

    @PutMapping("ingredient")
    public ResponseEntity<ApiMessageResponse> updateIngredient(@RequestBody IngredientDto ingredientDto) {
        ingredientService.updateIngredient(ingredientDto);
        return ResponseEntity.ok().body(ApiMessageResponse.defaultSuccessResponse());
    }

    @GetMapping("ingredients")
    public ResponseEntity<List<IngredientDto>> viewIngredients() {
        List<IngredientDto> ingredientDtoList = ingredientService.getIngredients();
        return ResponseEntity.ok().body(ingredientDtoList);
    }


}
