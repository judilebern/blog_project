package blog_project.repo;

import blog_project.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface CommentRepository extends JpaRepository<Comment, Long> {
}
