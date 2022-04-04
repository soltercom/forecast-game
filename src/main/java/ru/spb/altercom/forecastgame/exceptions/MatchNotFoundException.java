package ru.spb.altercom.forecastgame.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(Long id) {
        super(String.format("Match with id %d not found.", id));
    }
}
