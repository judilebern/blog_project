package blog_project.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MyErrorResolver implements ErrorViewResolver {
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        Map<String, Object> infoThatIsSafeToDisplay = new HashMap<>();
        infoThatIsSafeToDisplay.put("errorId", UUID.randomUUID().toString());

        ModelAndView modelAndView;
        switch (status) {
            case NOT_FOUND:
                modelAndView = new ModelAndView("error/404", infoThatIsSafeToDisplay);
                break;
            default:
                modelAndView = new ModelAndView("error", infoThatIsSafeToDisplay);
                break;
        }
        return modelAndView;
    }
}
