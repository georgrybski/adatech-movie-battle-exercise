package br.ada.americanas.moviebattle.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlayerNotFoundException extends RuntimeException {

    private static String messageFormat = "No player with ID %d found.";
    public PlayerNotFoundException(Long id) {
        super(String.format(messageFormat, id));
    }
}
