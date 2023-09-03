package blog_project;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/mainPage")
public class MainController {

    private RecipeService recipeService;

    public MainController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String getMainPage(Model model,
                              @PageableDefault(sort = { "title"}, direction = Sort.Direction.DESC, size = 20)
                              Pageable pageable) {
        Page<Recipe> recipeList = recipeService.findPaginated(pageable);
        model.addAttribute("recipes", recipeList);
        int totalPages = recipeList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
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
    public String getRecipes(Model model,
                             @PageableDefault(sort = { "title"}, direction = Sort.Direction.DESC, size = 2)
                             Pageable pageable) {
        Page<Recipe> recipePage = recipeService.findPaginated(pageable);
        model.addAttribute("recipesList", recipePage);

        int totalPages = recipePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "recipes";
    }

}
