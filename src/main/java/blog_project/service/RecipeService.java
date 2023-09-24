package blog_project.service;

import blog_project.entities.Category;
import blog_project.entities.Recipe;
import blog_project.repo.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Page<Recipe> findPaginated(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).get();
    }

    public void addRecipe(Recipe newRecipe) {
            recipeRepository.save(newRecipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> filterByName(String name) {
        return recipeRepository.findRecipeByTitleContainingIgnoreCase(name);
    }

    public List<Recipe> filterByCategory(Category category) {
        return recipeRepository.findRecipeByCategory(category);
    }
}
