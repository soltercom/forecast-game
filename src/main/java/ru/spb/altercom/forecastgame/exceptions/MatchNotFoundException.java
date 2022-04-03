package ru.spb.altercom.forecastgame.exceptions;

public class MatchNotFoundException extends RuntimeException {

    private final Long id;

    public MatchNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Match with id %d not found.", id);
    }
}
