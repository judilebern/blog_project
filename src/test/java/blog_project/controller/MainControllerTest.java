package blog_project.controller;

import blog_project.service.RecipeService;
import blog_project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
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

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(roles = "USER")
    @Test
    void deleteRecipe_userNotAdmin_redirectsToLogin() throws Exception {
        mockMvc.perform(delete("/mainPage/recipes/delete/2")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden()) // Expect a 403 Forbidden status
                .andExpect(view().name("error")) // Change to the appropriate error view name
                .andExpect(content().string(containsString("Access is denied"))); // Adjust content check

        verify(recipeService, times(0)).deleteRecipe(any());
    }

}
