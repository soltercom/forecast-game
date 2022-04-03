package ru.spb.altercom.forecastgame.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.domain.Match;
import ru.spb.altercom.forecastgame.exceptions.MatchNotFoundException;
import ru.spb.altercom.forecastgame.form.MatchForm;
import ru.spb.altercom.forecastgame.form.MatchFormAdapter;
import ru.spb.altercom.forecastgame.repository.MatchRepository;
import ru.spb.altercom.forecastgame.utils.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MatchService {

    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);

    private final TransactionManager transactionManager;
    private final MatchRepository matchRepo;
    private final TeamService teamService;
    private final MatchFormAdapter matchFormAdapter;

    public MatchService(TransactionManager transactionManager,
                        MatchRepository matchRepo,
                        TeamService teamService,
                        MatchFormAdapter matchFormAdapter) {
        this.transactionManager = transactionManager;
        this.matchRepo = matchRepo;
        this.teamService = teamService;
        this.matchFormAdapter = matchFormAdapter;
    }

    public Long add(MatchForm matchForm) {
        Objects.requireNonNull(matchForm);
        var match = matchFormAdapter.createMatchFromMatchForm(matchForm);
        var createdMatch = transactionManager.doInTransaction(() -> {
            var savedMatch = matchRepo.save(match);
            logger.info("Match is saved: {}", savedMatch);
            return savedMatch;
        });
        return createdMatch.getId();
    }

    public void edit(MatchForm matchForm) {
        Objects.requireNonNull(matchForm);
        var match = matchFormAdapter.createMatchFromMatchForm(matchForm);
        transactionManager.doInTransaction(() -> {
            matchRepo.save(match);
            logger.info("Match is updated: {}", match);
            return true;
        });
    }

    public void delete(Long id) {
        transactionManager.doInTransaction(() -> {
            matchRepo.deleteById(id);
            logger.info("Match with id {} is deleted", id);
            return true;
        });
    }

    public List<MatchForm> findAll() {
        var list = matchRepo.findAll();
        var result = new ArrayList<MatchForm>();
        list.forEach(
            item -> result.add(populateMatch(item)));
        return result;
    }

    public MatchForm findById(Long id) {
        var match = matchRepo.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));
        return populateMatch(match);
    }

    private MatchForm populateMatch(Match match) {
        var home = teamService.findById(match.getHome());
        var visitor = teamService.findById(match.getVisitor());
        return matchFormAdapter.createMatchFormFromMatch(match, home, visitor);
    }

}
