package blog_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class CustomStatusException extends RuntimeException {

    public CustomStatusException(String s) {
        super(s);
    }
}
