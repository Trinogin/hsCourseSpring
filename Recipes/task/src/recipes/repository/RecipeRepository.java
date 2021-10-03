package recipes.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entity.Recipe;
import recipes.entity.Usr;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateAsc(String category);

    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateAsc(String name);

    List<Recipe> findAllByUsr(Usr usr);

}
