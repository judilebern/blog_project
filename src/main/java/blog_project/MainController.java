package blog_project;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

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

/*    @GetMapping("/form")
    public ModelAndView displayUploadForm() {
        return new ModelAndView("form", "newRecipe", new Recipe());
    }

*//*    @PostMapping(value = "/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@ModelAttribute Recipe newRecipe, @RequestParam("file") MultipartFile file) {
        recipeService.addRecipe(newRecipe, file);
        return "redirect:/mainPage/form";
    }*//*

    @PostMapping(value = "/form", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@ModelAttribute Recipe newRecipe, @RequestParam("image") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                // Check if a file was provided
                newRecipe.setImage(file.getBytes());
            }

            // Save the Recipe object
            recipeService.addRecipe(newRecipe);

            return "redirect:/mainPage/form";
        } catch (IOException e) {
            // Handle the IOException, such as logging or displaying an error message
            e.printStackTrace();
            return "error"; // You can redirect to an error page or handle it differently
        }
    }*/

    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("newRecipe", new Recipe());
        return "form";
    }

    @PostMapping("/form")
    public String processForm(@RequestParam("productImage") MultipartFile file, @ModelAttribute Recipe newRecipe) throws IOException {
        productImageService.uploadImage(file);
        recipeService.addRecipe(newRecipe);
        return "redirect:/mainPage/form";
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
        byte[] image = productImageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }

/*    @GetMapping("/form")
    public String getForm(Model model) {
        model.addAttribute("newRecipe", new Recipe());
        return "form";
    }
       // th:action="@{/mainPage/form}"
PATIKRINT AR NEBUVO SITAS GERAS NES PRIE POST TRUKSTA INFO
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
    }*/

    @GetMapping("/test")
    public String getRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipeList);
        System.out.println(recipeList);
        return "test";
    }

}
