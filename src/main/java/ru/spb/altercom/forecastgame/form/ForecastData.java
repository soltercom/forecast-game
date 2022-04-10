package ru.spb.altercom.forecastgame.form;

import java.time.LocalDateTime;

public record ForecastData(Long matchId, LocalDateTime date,
                           int homeScore, int visitorScore, String info,
                           Long homeId, String homeName,
                           Long visitorId, String visitorName,
                           Long playerId, String playerName,
                           Long forecastId, int forecastHomeScore, int forecastVisitorScore) {

    public boolean isNew() {
        return forecastId == null;
    }

}
