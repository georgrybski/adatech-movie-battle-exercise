package br.ada.americanas.moviebattle.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends RuntimeException {
    private static String messageFormat = "No movie with ID %d found.";

    public MovieNotFoundException(Long id) {
        super(String.format(messageFormat, id));
    }
}
