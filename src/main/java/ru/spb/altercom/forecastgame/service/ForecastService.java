package ru.spb.altercom.forecastgame.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.form.*;
import ru.spb.altercom.forecastgame.repository.ForecastRepository;
import ru.spb.altercom.forecastgame.utils.TransactionManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ForecastService {

    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);

    private final TransactionManager transactionManager;
    private final ForecastRepository forecastRepo;
    private final ForecastFormAdapter forecastFormAdapter;

    @Autowired
    public ForecastService(TransactionManager transactionManager, ForecastRepository forecastRepo, ForecastFormAdapter forecastFormAdapter) {
        this.transactionManager = transactionManager;
        this.forecastRepo = forecastRepo;
        this.forecastFormAdapter = forecastFormAdapter;
    }

    public Map<MatchForm, List<ForecastData>> getData() {
        return forecastRepo.getForecastList().stream()
            .collect(Collectors.groupingBy(this::createMatchForm));
    }

    private MatchForm createMatchForm(ForecastData data) {
        var home = new TeamForm(data.homeId(), data.homeName());
        var visitor = new TeamForm(data.visitorId(), data.visitorName());
        return new MatchForm(data.matchId(),
                LocalDate.from(data.date()), LocalTime.from(data.date()),
                home, visitor,
                data.info(), data.homeScore(), data.homeScore());
    }

    public ForecastForm findByMatchAndPlayer(Long match, Long player) {
        var forecastOpt = forecastRepo
                .findByMatchAndPlayer(match, player);
        if (forecastOpt.isPresent()) {
            return forecastFormAdapter.createForecastFormFromForecast(forecastOpt.get());
        } else {
            return ForecastForm.create(match, player);
        }
    }

    public Long save(ForecastForm forecastForm) {
        Objects.requireNonNull(forecastForm);
        var forecast = forecastFormAdapter.createForecastFromForecastForm(forecastForm);
        var createdForecast = transactionManager.doInTransaction(() -> {
            var savedForecast = forecastRepo.save(forecast);
            logger.info("Match is saved: {}", savedForecast);
            return savedForecast;
        });
        return createdForecast.getId();
    }

}
