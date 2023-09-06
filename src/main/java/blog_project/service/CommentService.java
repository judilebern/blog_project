package blog_project.service;

import blog_project.entities.Comment;
import blog_project.repo.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addCommentToRecipe(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).get();
    }

}
