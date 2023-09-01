package blog_project;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private ProductImageService productImageService;

    public MainController(RecipeService recipeService, ProductImageService productImageService) {
        this.recipeService = recipeService;
        this.productImageService = productImageService;
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

    @PostMapping("/form")
    public String processForm(@RequestParam("prdimage") MultipartFile file, @ModelAttribute Recipe newRecipe) throws IOException {
        ProductImage image = productImageService.uploadImage(file);
        newRecipe.setProductImage(image);
        recipeService.addRecipe(newRecipe);
        return "redirect:/mainPage/form";
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
        byte[] image = productImageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

    @GetMapping("/test")
    public String getRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipeList);
        System.out.println(recipeList);
        return "test";
    }

}
