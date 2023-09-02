package blog_project;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/mainPage")
public class MainController {

    private RecipeService recipeService;

    public MainController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String getMainPage(Model model) {
        List<Recipe> recipeList = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipeList);
        return "index";
    }

    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recip", recipe);
        return "recipe";
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("newRecipe", new Recipe());
        return "form";
    }

    @PostMapping("/form")
    public String processForm(@RequestParam("prdimage") MultipartFile file, @ModelAttribute Recipe newRecipe) throws IOException {
        String image = Base64.encodeBase64String(file.getBytes());
        newRecipe.setImage(image);
        recipeService.addRecipe(newRecipe);
        return "redirect:/mainPage/recipes";
    }

    @GetMapping("/recipes")
    public String getRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAllRecipes();
        model.addAttribute("recipesList", recipeList);
        return "recipes";
    }

}
