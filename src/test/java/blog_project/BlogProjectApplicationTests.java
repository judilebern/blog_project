package blog_project;

import blog_project.entities.Category;
import blog_project.entities.Recipe;
import blog_project.repo.RecipeRepository;
import blog_project.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class BlogProjectApplicationTests {

    @Mock
    private RecipeRepository recipeRepository;

    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeService = new RecipeService(recipeRepository);
    }

    @Test
    void testGetRecipeById() {
        Long id = 1L;
        Recipe expectedRecipe = new Recipe();
        when(recipeRepository.findById(id)).thenReturn(Optional.of(expectedRecipe));

        Recipe result = recipeService.getRecipeById(id);

        assertEquals(expectedRecipe, result);
    }

    @Test
    void testAddRecipe() {
        Recipe newRecipe = new Recipe();
        recipeService.addRecipe(newRecipe);

        verify(recipeRepository, times(1)).save(newRecipe);
    }

    @Test
    void testDeleteRecipe() {
        Long id = 1L;
        recipeService.deleteRecipe(id);

        verify(recipeRepository, times(1)).deleteById(id);
    }

    @Test
    void testSaveRecipe() {
        Recipe recipe = new Recipe();
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        Recipe result = recipeService.saveRecipe(recipe);

        assertEquals(recipe, result);
    }

    @Test
    void testFilterByName() {
        String name = "Test Recipe";
        List<Recipe> expectedRecipes = new ArrayList<>();
        when(recipeRepository.findRecipeByTitleContainingIgnoreCase(name)).thenReturn(expectedRecipes);

        List<Recipe> result = recipeService.filterByName(name);

        assertEquals(expectedRecipes, result);
    }

    @Test
    void testFilterByCategory() {
        Category category = Category.DESSERT;
        List<Recipe> expectedRecipes = new ArrayList<>();
        when(recipeRepository.findRecipeByCategory(category)).thenReturn(expectedRecipes);

        List<Recipe> result = recipeService.filterByCategory(category);

        assertEquals(expectedRecipes, result);
    }


}
