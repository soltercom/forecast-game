package ru.spb.altercom.forecastgame.form;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.service.TeamService;

import java.text.ParseException;
import java.util.Locale;

@Service
public class TeamFormFormatter implements Formatter<TeamForm> {

    private final TeamService teamService;

    public TeamFormFormatter(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public @NotNull TeamForm parse(@NotNull String id, @NotNull Locale locale) throws ParseException {
        return teamService.findById(Long.parseLong(id));
    }

    @Override
    public @NotNull String print(TeamForm teamForm, @NotNull Locale locale) {
        return teamForm.id().toString();
    }
}
