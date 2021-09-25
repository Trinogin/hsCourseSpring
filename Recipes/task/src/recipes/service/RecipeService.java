package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.entity.Recipe;
import recipes.repository.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe saveRecipe(Recipe recipe) {
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

}
