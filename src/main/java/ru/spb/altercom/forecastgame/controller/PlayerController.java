package ru.spb.altercom.forecastgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.spb.altercom.forecastgame.form.PlayerForm;
import ru.spb.altercom.forecastgame.service.PlayerService;
import ru.spb.altercom.forecastgame.validator.PlayerFormValidator;

import javax.validation.Valid;

import static ru.spb.altercom.forecastgame.utils.Utility.getFormTitle;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private static final String REDIRECT_PLAYER_LIST = "redirect:/players";
    private static final String PLAYER_LIST = "/player/list";
    private static final String PLAYER_FORM = "/player/form";
    private static final String MODEL_PLAYER_FORM = "playerForm";
    private static final String FORM_TITLE = "formTitle";
    private static final String FORM_TITLE_VALUE = "Player";
    private static final String LIST_FORM_TITLE_VALUE = "Players";

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @InitBinder("playerForm")
    public void playerFormBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new PlayerFormValidator());
    }

    @GetMapping
    public String list(ModelMap model) {
        model.addAttribute("list", playerService.findAll());
        model.put(FORM_TITLE, LIST_FORM_TITLE_VALUE);
        return PLAYER_LIST;
    }

    @GetMapping("/{id}/edit")
    public String initForm(@PathVariable("id") Long id, ModelMap model) {
        model.put(MODEL_PLAYER_FORM, playerService.findById(id));
        model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE, id));
        return PLAYER_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processForm(@PathVariable("id") Long id,
                                    @Valid PlayerForm playerForm,
                                    BindingResult bindingResult,
                                    ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_PLAYER_FORM, playerForm);
            model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE, id));
            return PLAYER_FORM;
        }

        playerService.edit(playerForm);

        return REDIRECT_PLAYER_LIST;
    }

    @GetMapping("/new")
    public String initNewForm(ModelMap model) {
        model.put(MODEL_PLAYER_FORM, PlayerForm.create());
        model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE));
        return PLAYER_FORM;
    }

    @PostMapping("/new")
    public String processNewForm(@Valid PlayerForm playerForm,
                                    BindingResult bindingResult,
                                    ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_PLAYER_FORM, playerForm);
            model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE));
            return PLAYER_FORM;
        }

        playerService.add(playerForm);

        return REDIRECT_PLAYER_LIST;
    }

}
