package blog_project;

import blog_project.entities.Comment;
import blog_project.repo.CommentRepository;
import blog_project.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(commentRepository);
    }

    @Test
    void testAddCommentToRecipe() {
        Comment comment = new Comment();
        commentService.addCommentToRecipe(comment);

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void testDeleteCommentById() {
        Long id = 1L;
        commentService.deleteCommentById(id);

        verify(commentRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindCommentById() {
        Long id = 1L;
        Comment expectedComment = new Comment();
        when(commentRepository.findById(id)).thenReturn(Optional.of(expectedComment));

        Comment result = commentService.findCommentById(id);

        assertEquals(expectedComment, result);
    }
}
