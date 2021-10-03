package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.entity.Recipe;
import recipes.entity.Usr;
import recipes.repository.RecipeRepository;
import recipes.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Recipe saveRecipe(Recipe recipe, String userEmail) {
        Usr usr = userRepository.findByEmail(userEmail).orElseThrow(
                () -> new UsernameNotFoundException("No user with login " + userEmail + " for register recipe."));
        recipe.setUsr(usr);
        recipe.setDate(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> findByRecipeId(Long id) {
        return recipeRepository.findById(id);
    }

    public Optional<Recipe> deleteRecipe(Long id, String userEmail) {
        Optional<Recipe> recipeToDel = recipeRepository.findById(id);
        Optional<Usr> usr = userRepository.findByEmail(userEmail);
        usr.orElseThrow(
                () -> new UsernameNotFoundException("No user with login " + userEmail + " for delete recipe."));
        if (recipeToDel.isPresent()) {
            if (recipeToDel.get().getUsr().getUsrId() != usr.get().getUsrId()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            recipeRepository.deleteById(id);
        }
        return recipeToDel;
    }

    public Optional<Recipe> updateRecipe(Recipe newRecipe, Long id, String userEmail) {
        Optional<Recipe> recipeToUpd = recipeRepository.findById(id);
        Optional<Usr> usr = userRepository.findByEmail(userEmail);
        usr.orElseThrow(
                () -> new UsernameNotFoundException("No user with login " + userEmail + " for update recipe."));
        if (recipeToUpd.isPresent()) {
            if (recipeToUpd.get().getUsr().getUsrId() != usr.get().getUsrId()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            newRecipe.setRecipeId(recipeToUpd.get().getRecipeId());
            newRecipe.setDate(LocalDateTime.now());
            newRecipe.setUsr(usr.get());
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
