package blog_project.repo;

import blog_project.entities.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /*
JpaRepository extends ListPagingAndSortingRepository and knows how to handle pageable
 */
    @Override
    Page<Recipe> findAll(Pageable pageable);

    List<Recipe> findRecipeByTitleContainingIgnoreCase(String title);

}
