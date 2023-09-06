package blog_project.config;

import blog_project.controller.CustomStatusException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ModelAndView handleMyException(CustomStatusException exception, Model model) {
        model.addAttribute("errorId", UUID.randomUUID().toString());
        model.addAttribute("message", exception.getMessage());
        return new ModelAndView("customError", model.asMap());
    }

    @ExceptionHandler
    public ModelAndView handleWhateverException(Exception exception, Model model) {
        model.addAttribute("errorId", UUID.randomUUID().toString());
        model.addAttribute("message", exception.getMessage());
        return new ModelAndView("customError", model.asMap());
    }
}
