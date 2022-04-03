package ru.spb.altercom.forecastgame.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.domain.Team;
import ru.spb.altercom.forecastgame.exceptions.TeamNotFoundException;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.form.TeamFormAdapter;
import ru.spb.altercom.forecastgame.repository.TeamRepository;
import ru.spb.altercom.forecastgame.utils.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    private final TransactionManager transactionManager;
    private final TeamRepository teamRepo;
    private final TeamFormAdapter teamFormAdapter;

    @Autowired
    public TeamService(TransactionManager transactionManager,
                       TeamRepository teamRepo,
                       TeamFormAdapter teamFormAdapter) {
        this.transactionManager = transactionManager;
        this.teamRepo = teamRepo;
        this.teamFormAdapter = teamFormAdapter;
    }

    public Long add(TeamForm teamForm) {
        Objects.requireNonNull(teamForm);
        var team = teamFormAdapter.createTeamFromTeamForm(teamForm);
        var createdTeam = transactionManager.doInTransaction(() -> {
            var savedTeam = teamRepo.save(team);
            logger.info("Team is saved: {}", savedTeam);
            return savedTeam;
        });
        return createdTeam.getId();
    }

    public void edit(TeamForm teamForm) {
        Objects.requireNonNull(teamForm);
        var team = teamFormAdapter.createTeamFromTeamForm(teamForm);
        transactionManager.doInTransaction(() -> {
            teamRepo.save(team);
            logger.info("Team is updated: {}", team);
            return true;
        });
    }

    public void delete(Long id) {
        transactionManager.doInTransaction(() -> {
            teamRepo.deleteById(id);
            logger.info("Team with id {} is deleted", id);
            return true;
        });
    }

    public List<TeamForm> findAll() {
        var list = teamRepo.findAll();
        var result = new ArrayList<TeamForm>();
        list.forEach(
            item -> result.add(teamFormAdapter.createTeamFormFromTeam(item)));
        return result;
    }

    public TeamForm findById(Long id) {
        var team = teamRepo.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
        return teamFormAdapter.createTeamFormFromTeam(team);
    }

}
