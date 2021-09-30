package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.entity.Recipe;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe saveRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> findByRecipeId(Long id) {
        return recipeRepository.findById(id);
    }

    public Optional<Recipe> deleteRecipe(Long id) {
        Optional<Recipe> recipeToDel = recipeRepository.findById(id);
        recipeToDel.ifPresent((it) -> recipeRepository.deleteById(id));
        return recipeToDel;
    }

    public Optional<Recipe> updateRecipe(Recipe newRecipe, Long id) {
        Optional<Recipe> recipeToUpd = recipeRepository.findById(id);
        if (recipeToUpd.isPresent()) {
            newRecipe.setRecipeId(recipeToUpd.get().getRecipeId());
            newRecipe.setDate(LocalDateTime.now());
            return Optional.of(recipeRepository.save(newRecipe));
        } else {
            return Optional.empty();
        }
    }

    public List<Recipe> searchRecipesByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateAsc(category);
    }

    public List<Recipe> searchRecipesByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateAsc(name);
    }
}
