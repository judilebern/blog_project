package blog_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/mainPage")
public class MainController {

    private final RecipeService recipeService;

    public MainController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String getMainPage() {
        return "index";
    }

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("newRecipe", new Recipe());
        return "form";
    }

    @PostMapping
    public String processForm(@ModelAttribute Recipe newRecipe, @RequestParam("image") MultipartFile imageFile, Model model) {
        if (!imageFile.isEmpty()) {
            try {
                newRecipe.setImage(imageFile.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        recipeService.addRecipe(newRecipe);
        return "redirect:/mainPage/form";
    }

    @GetMapping("/test")
    public String getRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipeList);
        System.out.println(recipeList);
        return "test";
    }

}
