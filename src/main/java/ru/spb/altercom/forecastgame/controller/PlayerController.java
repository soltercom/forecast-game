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
    private static final String MODEL_PLAYER_FORM = "playerForm";

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
    public String initForm(@PathVariable("id") Long id, ModelMap model) {
        model.put(MODEL_PLAYER_FORM, playerService.findById(id));
        return PLAYER_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processForm(@PathVariable("id") Long id,
                                    @Valid PlayerForm playerForm,
                                    BindingResult bindingResult,
                                    ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_PLAYER_FORM, playerForm);
            return PLAYER_FORM;
        }

        playerService.edit(playerForm);

        return REDIRECT_PLAYER_LIST;
    }

    @GetMapping("/new")
    public String initNewForm(ModelMap model) {
        model.put(MODEL_PLAYER_FORM, PlayerForm.create());
        return PLAYER_FORM;
    }

    @PostMapping("/new")
    public String processNewForm(@Valid PlayerForm playerForm,
                                    BindingResult bindingResult,
                                    ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_PLAYER_FORM, playerForm);
            return PLAYER_FORM;
        }

        playerService.add(playerForm);

        return REDIRECT_PLAYER_LIST;
    }

}
