package blog_project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    /*
JpaRepository extends ListPagingAndSortingRepository and knows how to handle pageable
 */
    @Override
    Page<Recipe> findAll(Pageable pageable);

}
