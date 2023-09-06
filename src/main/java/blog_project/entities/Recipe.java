package blog_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{Recipe.Title.NotEmpty}")
    @Column(columnDefinition = "TEXT")
    private String title;

    @NotBlank(message = "{Recipe.Description.NotEmpty}")
    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @NotBlank(message = "{Recipe.Ingredients.NotEmpty}")
    @Column(columnDefinition = "TEXT")
    private String ingriedients;

    @Column(columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotBlank(message = "{Recipe.Text.NotEmpty}")
    @Column(columnDefinition = "TEXT")
    private String recipeText;

    @NotBlank(message = "{Recipe.Image.NotEmpty}")
    @Column(columnDefinition = "TEXT")
    private String image;

    @OneToMany(mappedBy = "recipe", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}
