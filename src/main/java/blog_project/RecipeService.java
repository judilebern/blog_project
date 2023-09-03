package blog_project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    private RecipeRepository recipeRepository;
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

/*    public Page<Recipe> getAllRecipes() {

    }*/

    public Page<Recipe> findPaginated(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).get();
    }

    public void addRecipe(Recipe newRecipe) {
            recipeRepository.save(newRecipe);
    }

}
