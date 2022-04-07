package ru.spb.altercom.forecastgame.helpers;

import com.github.javafaker.Faker;
import ru.spb.altercom.forecastgame.domain.Player;
import ru.spb.altercom.forecastgame.form.MatchForm;
import ru.spb.altercom.forecastgame.form.PlayerForm;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.service.MatchService;
import ru.spb.altercom.forecastgame.service.PlayerService;
import ru.spb.altercom.forecastgame.service.TeamService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Stubs {

    private static final Faker faker = new Faker();

    public static Player getPlayer() {
        return new Player(1L, "Player", "Password", false);
    }

    public static PlayerForm getNewPlayerForm() {
        return new PlayerForm(null, faker.name().fullName(), "11111", false);
    }

    public static TeamForm getNewTeamForm() {
        return new TeamForm(null, faker.country().name());
    }

    public static MatchForm getNewMatchForm(TeamForm home, TeamForm visitor) {
        return new MatchForm(null,
                LocalDate.now(),
                LocalTime.now(),
                home, visitor, "", 0, 0);
    }

    public static TeamForm getTeamForm(TeamService teamService) {
        return teamService.findById(teamService.add(getNewTeamForm()));
    }

    public static PlayerForm getPlayerForm(PlayerService playerService) {
        return playerService.findById(playerService.add(getNewPlayerForm()));
    }

    public static MatchForm getMatchForm(MatchService matchService, TeamForm home, TeamForm visitor) {
        var match = getNewMatchForm(home, visitor);
        return matchService.findById(matchService.add(match));
    }

    public static MatchForm getMatchForm(MatchService matchService, TeamService teamService) {
        var match = getNewMatchForm(getTeamForm(teamService), getTeamForm(teamService));
        return matchService.findById(matchService.add(match));
    }


    public static List<PlayerForm> getListOfPlayerForms(PlayerService playerService) {
        return IntStream.range(0, 3)
                .boxed()
                .map(i -> getNewPlayerForm())
                .map(playerService::add)
                .map(playerService::findById)
                .collect(Collectors.toList());
    }

    public static List<TeamForm> getListOfTeamForms(TeamService teamService) {
        return IntStream.range(0, 3)
                .boxed()
                .map(i -> getNewTeamForm())
                .map(teamService::add)
                .map(teamService::findById)
                .collect(Collectors.toList());
    }


}
