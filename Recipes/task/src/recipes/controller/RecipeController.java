package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> addRecipe(@RequestBody @Valid Recipe newRecipe, Principal principal) {
        Recipe savedRecipe = recipeService.saveRecipe(newRecipe, principal.getName());
        return new ResponseEntity<>(Map.of("id", savedRecipe.getRecipeId()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@RequestBody @Valid Recipe newRecipe,
                                          @PathVariable @Max(Long.MAX_VALUE) @Min(0) Long id,
                                          Principal principal) {
        recipeService.updateRecipe(newRecipe, id, principal.getName()).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable @Max(Long.MAX_VALUE) @Min(0) Long id,
                                          Principal principal) {
        recipeService.deleteRecipe(id, principal.getName()).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable @Max(Long.MAX_VALUE) @Min(0) Long id) {
        return recipeService.findByRecipeId(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipesByCategory(@RequestParam Optional<String> category,
                                                @RequestParam Optional<String> name) {
        if (category.isPresent()) {
            if (name.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else {
                return recipeService.searchRecipesByCategory(category.get());
            }
        } else if (name.isPresent()) {
            return recipeService.searchRecipesByName(name.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
