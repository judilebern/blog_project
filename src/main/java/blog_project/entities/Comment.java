package blog_project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private LocalDateTime dateTime;

    private LocalDateTime updatedDateTime;

    @ManyToOne
    @JoinColumn(name="recipe_id", nullable=false)
    private Recipe recipe;

    @ManyToOne
    private User createdBy;

}
