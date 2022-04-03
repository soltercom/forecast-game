package ru.spb.altercom.forecastgame.exceptions;

public class TeamNotFoundException extends RuntimeException {

    private final Long id;

    public TeamNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Team with id %d not found.", id);
    }
}
