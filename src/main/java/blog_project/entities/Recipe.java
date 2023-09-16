package blog_project.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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

    private LocalDateTime dateTime;

    private LocalDateTime updatedDateTime;

    @OneToMany(mappedBy = "recipe", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

}
