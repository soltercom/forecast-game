package ru.spb.altercom.forecastgame.exceptions;

public class PlayerNotFoundException extends RuntimeException {

    private final Long id;

    public PlayerNotFoundException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Player with id %d not found.", id);
    }
}
