package ru.spb.altercom.forecastgame.form;

public record ForecastForm(Long id, Long match, Long player, int homeScore, int visitorScore) {

    public static ForecastForm create(Long match, Long player) {
        return new ForecastForm(match, player);
    }

    private ForecastForm(Long match, Long player) {
        this(null, match, player, 0, 0);
    }

}
