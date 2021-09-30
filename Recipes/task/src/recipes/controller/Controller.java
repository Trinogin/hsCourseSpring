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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class Controller {

    private final RecipeService recipeService;

    @Autowired
    public Controller(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> addRecipe(@RequestBody @Valid Recipe newRecipe) {
        Recipe savedRecipe = recipeService.saveRecipe(newRecipe);
        return new ResponseEntity<>(Map.of("id", savedRecipe.getRecipeId()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@RequestBody @Valid Recipe newRecipe,
                                          @PathVariable @Max(Long.MAX_VALUE) @Min(0) Long id) {
        recipeService.updateRecipe(newRecipe, id).orElseThrow(() -> {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable @Max(Long.MAX_VALUE) @Min(0) Long id) {
        recipeService.deleteRecipe(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
