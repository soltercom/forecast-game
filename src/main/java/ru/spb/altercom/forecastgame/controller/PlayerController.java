package ru.spb.altercom.forecastgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spb.altercom.forecastgame.form.PlayerForm;
import ru.spb.altercom.forecastgame.service.PlayerService;

import javax.validation.Valid;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private static final String REDIRECT_PLAYER_LIST = "redirect:/players";
    private static final String PLAYER_LIST = "/player/list";
    private static final String PLAYER_FORM = "/player/form";

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", playerService.findAll());
        return PLAYER_LIST;
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(@PathVariable("id") Long id, ModelMap model) {
        model.put("playerForm", playerService.findById(id));
        return PLAYER_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateForm(@PathVariable("id") Long id,
                                    @Valid PlayerForm playerForm,
                                    BindingResult bindingResult,
                                    ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put("playerForm", playerForm);
            return PLAYER_FORM;
        }

        playerService.edit(playerForm);

        return REDIRECT_PLAYER_LIST;
    }

}
