package br.ada.americanas.moviebattle.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BattleNotFoundExeption extends RuntimeException {
    private static String messageFormat = "No battle with ID %d found.";

    public BattleNotFoundExeption(Long id) {
        super(String.format(messageFormat, id));
    }
}
