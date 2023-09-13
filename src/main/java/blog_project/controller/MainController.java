package blog_project.controller;

import blog_project.entities.Category;
import blog_project.entities.Comment;
import blog_project.entities.Recipe;
import blog_project.service.CommentService;
import blog_project.service.RecipeService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/mainPage")
public class MainController {

    private RecipeService recipeService;
    private final CommentService commentService;


    public MainController(RecipeService recipeService, CommentService commentService) {
        this.recipeService = recipeService;
        this.commentService = commentService;
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
        model.addAttribute("comment", new Comment());
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
        newRecipe.setDateTime(LocalDateTime.now());
        recipeService.addRecipe(newRecipe);
        return "redirect:/mainPage/recipes";
    }

    @PostMapping("/{id}")
    public String addCommentToRecipe(@PathVariable Long id, Comment comment) {
        Recipe recipe = recipeService.getRecipeById(id);
        comment.setRecipe(recipe);
        comment.setDateTime(LocalDateTime.now());
        commentService.addCommentToRecipe(comment);
        return "redirect:/mainPage/" + id;
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

    @GetMapping("/recipes/delete/{id}")
    public String deleteRecipe(@PathVariable("id") long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/mainPage/recipes";
    }

    @GetMapping("recipes/showUpdateForm")
    public ModelAndView showUpdateRecipeForm(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("edit_form");
        Recipe recipe = recipeService.getRecipeById(id);
        mav.addObject("recipeee", recipe);
        return mav;
    }

    @PostMapping("/recipes/saveUpdatedRecipe")
    public String saveUpdatedRecipe(@RequestParam("prdimage") MultipartFile file, @ModelAttribute Recipe recipe) throws IOException {
        String image = Base64.encodeBase64String(file.getBytes());
        recipe.setImage(image);
        recipe.setUpdatedDateTime(LocalDateTime.now());
        recipeService.saveRecipe(recipe);
        return "redirect:/mainPage/recipes";
    }

    @GetMapping("/recipes/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") long id) {
        Comment comment = commentService.findCommentById(id);
        commentService.deleteCommentById(id);
        return "redirect:/mainPage/"+ comment.getRecipe().getId();
    }

    @GetMapping("/recipes/commentEdit")
    public ModelAndView showCommentForm(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("recipe-comment-edit");
        Comment comment = commentService.findCommentById(id);
        mav.addObject("recipeee", comment);
        return mav;
    }

    @PostMapping("/recipes/saveComment")
    public String saveComment(@ModelAttribute Comment comment) {
        comment.setUpdatedDateTime(LocalDateTime.now());
        commentService.addCommentToRecipe(comment);
        return "redirect:/mainPage/" + comment.getRecipe().getId();
    }

    @GetMapping("/filterByTitle")
    public String filterByTitle(@RequestParam String keyword, Model model) {
        List<Recipe> recipes = recipeService.filterByName(keyword);
        model.addAttribute("recipes", recipes);
        return "index";
    }

    @GetMapping("/filterByCategory")
    public String filterByCategory(@RequestParam("categoryOpt") Category category, Model model) {
        List<Recipe> recipes = recipeService.filterByCategory(category);
        model.addAttribute("categorySelected", category.name());
        model.addAttribute("recipes", recipes);
        return "index";
    }
}
