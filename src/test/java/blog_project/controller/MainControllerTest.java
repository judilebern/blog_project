package blog_project.controller;

import blog_project.entities.Comment;
import blog_project.entities.Recipe;
import blog_project.entities.Role;
import blog_project.entities.User;
import blog_project.service.CommentService;
import blog_project.service.RecipeService;
import blog_project.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// test slice
@WebMvcTest(MainController.class)
public class MainControllerTest {


    @MockBean
    private RecipeService recipeService;

    @MockBean
    private UserService userService;

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTopic_topicExists_returnsTopicPage_roleAdmin() throws Exception {
        //given
        long recipeId = 1L;
        Recipe givenRecipeFoundInDb = new Recipe();
        givenRecipeFoundInDb.setId(recipeId);
        givenRecipeFoundInDb.setTitle("Some very special recipe");
        givenRecipeFoundInDb.setRecipeText("Thats a lot of text");
        givenRecipeFoundInDb.setShortDescription("Short discription goes here");


        when(recipeService.getRecipeById(recipeId)).thenReturn(givenRecipeFoundInDb);

        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));

        //when-then
        mockMvc.perform(get("/mainPage/%d".formatted(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipe"))
                .andExpect(model().attribute("isAdmin", true))
                .andExpect(model().attribute("recip", givenRecipeFoundInDb))
                .andExpect(model().attribute("comment", new Comment()));
    }

    @Test
    void getTopic_topicExists_returnsTopicPage_roleUser() throws Exception {
        //given
        long recipeId = 1L;
        Recipe givenRecipeFoundInDb = new Recipe();
        givenRecipeFoundInDb.setId(recipeId);
        givenRecipeFoundInDb.setTitle("Some very special recipe");
        givenRecipeFoundInDb.setRecipeText("Thats a lot of text");
        givenRecipeFoundInDb.setShortDescription("Short discription goes here");


        when(recipeService.getRecipeById(recipeId)).thenReturn(givenRecipeFoundInDb);

        User user = new User();
        user.setRoles(Set.of(Role.USER));

        //when-then
        mockMvc.perform(get("/mainPage/%d".formatted(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipe"))
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("recip", givenRecipeFoundInDb))
                .andExpect(model().attribute("comment", new Comment()));
    }

    @Test
    void getTopic_topicExists_returnsTopicPage_anonymousUser() throws Exception {
        //given
        long recipeId = 1L;
        Recipe givenRecipeFoundInDb = new Recipe();
        givenRecipeFoundInDb.setId(recipeId);
        givenRecipeFoundInDb.setTitle("Some very special recipe");
        givenRecipeFoundInDb.setRecipeText("Thats a lot of text");
        givenRecipeFoundInDb.setShortDescription("Short discription goes here");


        when(recipeService.getRecipeById(recipeId)).thenReturn(givenRecipeFoundInDb);


        //when-then
        mockMvc.perform(get("/mainPage/%d".formatted(recipeId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipe"))
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("recip", givenRecipeFoundInDb))
                .andExpect(model().attribute("comment", new Comment()));
    }

    @Test
    void getTopic_topicNotExists_StatusNotFound() throws Exception {
        //given
        long recipeId = 1L;


        when(recipeService.getRecipeById(recipeId)).thenReturn(null);


        //when-then
        mockMvc.perform(get("/mainPage/%d".formatted(recipeId)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void getForm_returnForm_admin() throws Exception {
        //given

        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));

        //when-then
        mockMvc.perform(get("/mainPage/form").with(user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attribute("newRecipe", new Recipe()));
    }


    @Test
    void getForm_returnNotFound_user() throws Exception {
        //given

        User user = new User();
        user.setRoles(Set.of(Role.USER));

        //when-then
        mockMvc.perform(get("/mainPage/form").with(user(user)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getForm_returnNotFound_anonymous() throws Exception {
        //given

        User user = new User();

        //when-then
        mockMvc.perform(get("/mainPage/form").with(user(user)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void addCommentToRecipe_Admin() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setRoles(Set.of(Role.ADMIN));

        long recipeId = 1L;
        Recipe givenRecipeFoundInDb = new Recipe();
        givenRecipeFoundInDb.setId(recipeId);


        Comment comment = new Comment();
        comment.setText("TEXT");

        when(recipeService.getRecipeById(recipeId)).thenReturn(givenRecipeFoundInDb);


        //when-then
        mockMvc.perform(post("/mainPage/%d".formatted(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/mainPage/%d".formatted(recipeId)));

    }

    @Test
    void deleteRecipe_Admin() throws Exception {

        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));

        long recipeId = 1L;

        //when-then
        mockMvc.perform(get("/mainPage/recipes/delete/%d".formatted(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/mainPage/recipes"));

        verify(recipeService).deleteRecipe(recipeId);
    }

    @Test
    void deleteRecipe_User() throws Exception {

        User user = new User();
        user.setRoles(Set.of(Role.USER));

        long recipeId = 1L;

        //when-then
        mockMvc.perform(get("/mainPage/recipes/delete/%d".formatted(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(recipeService, times(0)).deleteRecipe(recipeId);
    }

    @Test
    void deleteRecipe_Anonymous() throws Exception {

        User user = new User();

        long recipeId = 1L;

        //when-then
        mockMvc.perform(get("/mainPage/recipes/delete/%d".formatted(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(recipeService, times(0)).deleteRecipe(recipeId);
    }

    @Test
    void getForm_returnUpdateForm_admin() throws Exception {
        //given
        long recipeId = 1L;
        Recipe givenRecipeFoundInDb = new Recipe();
        givenRecipeFoundInDb.setId(recipeId);

        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));

        when(recipeService.getRecipeById(recipeId)).thenReturn(givenRecipeFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/showUpdateForm").param("id", String.valueOf(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit_form"))
                .andExpect(model().attribute("recipeee", givenRecipeFoundInDb));
    }

    @Test
    void getForm_returnUpdateForm_user() throws Exception {
        //given
        long recipeId = 1L;
        Recipe givenRecipeFoundInDb = new Recipe();
        givenRecipeFoundInDb.setId(recipeId);

        User user = new User();
        user.setRoles(Set.of(Role.USER));

        when(recipeService.getRecipeById(recipeId)).thenReturn(givenRecipeFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/showUpdateForm").param("id", String.valueOf(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getForm_returnUpdateForm_anomymous() throws Exception {
        //given
        long recipeId = 1L;
        Recipe givenRecipeFoundInDb = new Recipe();
        givenRecipeFoundInDb.setId(recipeId);

        User user = new User();

        when(recipeService.getRecipeById(recipeId)).thenReturn(givenRecipeFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/showUpdateForm").param("id", String.valueOf(recipeId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteComment_Admin() throws Exception {

        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));
        user.setId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        long commentId = 1L;
        Comment givenCommentFoundInDb = new Comment();
        givenCommentFoundInDb.setCreatedBy(user);
        givenCommentFoundInDb.setId(commentId);
        givenCommentFoundInDb.setRecipe(recipe);

        when(commentService.findCommentById(commentId)).thenReturn(givenCommentFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/deleteComment/%d".formatted(commentId)).with(user(user)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/mainPage/%d".formatted(recipe.getId())));

        verify(commentService).deleteCommentById(commentId);
    }

    @Test
    void deleteComment_USER() throws Exception {

        User user = new User();
        user.setRoles(Set.of(Role.USER));
        user.setId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        long commentId = 1L;
        Comment givenCommentFoundInDb = new Comment();
        givenCommentFoundInDb.setCreatedBy(user);
        givenCommentFoundInDb.setId(commentId);
        givenCommentFoundInDb.setRecipe(recipe);

        when(commentService.findCommentById(commentId)).thenReturn(givenCommentFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/deleteComment/%d".formatted(commentId)).with(user(user)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/mainPage/%d".formatted(recipe.getId())));

        verify(commentService).deleteCommentById(commentId);
    }

    @Test
    void deleteComment_Anonymous() throws Exception {

        User user = new User();
        user.setRoles(Set.of(Role.USER));
        user.setId(1L);

        User user2 = new User();

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        long commentId = 1L;
        Comment givenCommentFoundInDb = new Comment();
        givenCommentFoundInDb.setCreatedBy(user);
        givenCommentFoundInDb.setId(commentId);
        givenCommentFoundInDb.setRecipe(recipe);

        when(commentService.findCommentById(commentId)).thenReturn(givenCommentFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/deleteComment/%d".formatted(commentId)).with(user(user2)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(commentService, times(0)).deleteCommentById(commentId);
    }

    @Test
    void getForm_returnUpdateCommentForm_admin() throws Exception {
        //given
        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));
        user.setId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        long commentId = 1L;
        Comment givenCommentFoundInDb = new Comment();
        givenCommentFoundInDb.setCreatedBy(user);
        givenCommentFoundInDb.setId(commentId);
        givenCommentFoundInDb.setRecipe(recipe);

        when(commentService.findCommentById(commentId)).thenReturn(givenCommentFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/commentEdit").param("id", String.valueOf(commentId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipe-comment-edit"))
                .andExpect(model().attribute("recipeee", givenCommentFoundInDb));
    }

    @Test
    void getForm_returnUpdateCommentForm_user() throws Exception {
        //given
        User user = new User();
        user.setRoles(Set.of(Role.USER));
        user.setId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        long commentId = 1L;
        Comment givenCommentFoundInDb = new Comment();
        givenCommentFoundInDb.setCreatedBy(user);
        givenCommentFoundInDb.setId(commentId);
        givenCommentFoundInDb.setRecipe(recipe);

        when(commentService.findCommentById(commentId)).thenReturn(givenCommentFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/commentEdit").param("id", String.valueOf(commentId)).with(user(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("recipe-comment-edit"))
                .andExpect(model().attribute("recipeee", givenCommentFoundInDb));
    }

    @Test
    void getForm_returnUpdateCommentForm_anonymous() throws Exception {
        //given
        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));
        user.setId(1L);

        User user1 = new User();

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        long commentId = 1L;
        Comment givenCommentFoundInDb = new Comment();
        givenCommentFoundInDb.setCreatedBy(user);
        givenCommentFoundInDb.setId(commentId);
        givenCommentFoundInDb.setRecipe(recipe);

        when(commentService.findCommentById(commentId)).thenReturn(givenCommentFoundInDb);

        //when-then
        mockMvc.perform(get("/mainPage/recipes/commentEdit").param("id", String.valueOf(commentId)).with(user(user1)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getForm_returnsaveCommentForm_admin() throws Exception {
        //given

        User user = new User();
        user.setRoles(Set.of(Role.ADMIN));
        user.setId(1L);

        User user1 = new User();

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Comment commentFromFE = new Comment();
        commentFromFE.setId(1L);
        commentFromFE.setText("TEXT");

        long commentId = 1L;
        Comment givenCommentFoundInDb = new Comment();
        givenCommentFoundInDb.setCreatedBy(user);
        givenCommentFoundInDb.setId(commentId);
        givenCommentFoundInDb.setRecipe(recipe);

        when(commentService.findCommentById(commentId)).thenReturn(givenCommentFoundInDb);

        //when-then
        mockMvc.perform(post("/mainPage/recipes/saveComment").param("id", String.valueOf(commentFromFE.getId())).param("text", commentFromFE.getText()).with(user(user)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/mainPage/%d".formatted(givenCommentFoundInDb.getRecipe().getId())));


        verify(commentService).addCommentToRecipe(givenCommentFoundInDb);
    }
}
