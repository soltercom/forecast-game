package ru.spb.altercom.forecastgame.form;

import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.domain.Forecast;

@Service
public class ForecastFormAdapter {

    public ForecastForm createForecastFormFromForecast(Forecast forecast) {
        return new ForecastForm(forecast.getId(),
            forecast.getMatch(), forecast.getPlayer(),
            forecast.getHomeScore(), forecast.getVisitorScore());
    }

    public Forecast createForecastFromForecastForm(ForecastForm forecastForm) {
        return new Forecast(forecastForm.id(),
                forecastForm.match(), forecastForm.player(),
                forecastForm.homeScore(), forecastForm.visitorScore());
    }

}
