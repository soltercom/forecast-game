package ru.spb.altercom.forecastgame.form;

import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.domain.Team;

@Service
public class TeamFormAdapter {

    public TeamForm createTeamFormFromTeam(Team team) {
        return new TeamForm(team.getId(), team.getName());
    }

    public Team createTeamFromTeamForm(TeamForm teamForm) {
        return new Team(teamForm.id(), teamForm.name());
    }

}
