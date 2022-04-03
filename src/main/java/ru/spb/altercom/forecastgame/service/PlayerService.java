package ru.spb.altercom.forecastgame.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spb.altercom.forecastgame.domain.Player;
import ru.spb.altercom.forecastgame.exceptions.PlayerNotFoundException;
import ru.spb.altercom.forecastgame.form.PlayerForm;
import ru.spb.altercom.forecastgame.form.PlayerFormAdapter;
import ru.spb.altercom.forecastgame.repository.PlayerRepository;
import ru.spb.altercom.forecastgame.utils.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class PlayerService {

    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final TransactionManager transactionManager;
    private final PlayerRepository playerRepo;
    private final PlayerFormAdapter playerFormAdapter;

    @Autowired
    public PlayerService(TransactionManager transactionManager,
                         PlayerRepository playerRepo,
                         PlayerFormAdapter playerFormAdapter) {
        this.transactionManager = transactionManager;
        this.playerRepo = playerRepo;
        this.playerFormAdapter = playerFormAdapter;
    }

    public Long add(PlayerForm playerForm) {
        Objects.requireNonNull(playerForm);
        var player = playerFormAdapter.createPlayerFromPlayerForm(playerForm);
        var createdPlayer = transactionManager.doInTransaction(() -> {
            var savedPlayer = playerRepo.save(player);
            logger.info("Player is saved: {}", savedPlayer);
            return savedPlayer;
        });
        return createdPlayer.getId();
    }

    public void edit(PlayerForm playerForm) {
        Objects.requireNonNull(playerForm);
        var player = playerFormAdapter.createPlayerFromPlayerForm(playerForm);
        transactionManager.doInTransaction(() -> {
            playerRepo.save(player);
            logger.info("Player is updated: {}", player);
            return true;
        });
    }

    public List<PlayerForm> findAll() {
        var list = playerRepo.findAll();
        var result = new ArrayList<PlayerForm>();
        list.forEach(
            item -> result.add(playerFormAdapter.createPlayerFormFromPlayer(item)));
        return result;
    }

    public PlayerForm findById(Long id) {
        var player = playerRepo.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));
        return playerFormAdapter.createPlayerFormFromPlayer(player);
    }

    public void delete(Long id) {
        transactionManager.doInTransaction(() -> {
            playerRepo.deleteById(id);
            logger.info("Player with id {} is deleted", id);
            return true;
        });
    }

}
