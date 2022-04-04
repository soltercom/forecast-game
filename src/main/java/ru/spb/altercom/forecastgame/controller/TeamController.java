package ru.spb.altercom.forecastgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.service.TeamService;
import ru.spb.altercom.forecastgame.validator.TeamFormValidator;

import javax.validation.Valid;

import static ru.spb.altercom.forecastgame.utils.Utility.getFormTitle;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private static final String REDIRECT_TEAM_LIST = "redirect:/teams";
    private static final String TEAM_LIST = "/team/list";
    private static final String TEAM_FORM = "/team/form";
    private static final String MODEL_TEAM_FORM = "teamForm";
    private static final String FORM_TITLE = "formTitle";
    private static final String FORM_TITLE_VALUE = "Team";
    private static final String LIST_FORM_TITLE_VALUE = "Teams";

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @InitBinder("teamForm")
    public void teamFormBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new TeamFormValidator());
    }

    @GetMapping
    public String list(ModelMap model) {
        model.put("list", teamService.findAll());
        model.put(FORM_TITLE, LIST_FORM_TITLE_VALUE);
        return TEAM_LIST;
    }

    @GetMapping("/{id}/edit")
    public String initForm(@PathVariable("id") Long id, ModelMap model) {
        model.put(MODEL_TEAM_FORM, teamService.findById(id));
        model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE, id));
        return TEAM_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processForm(@PathVariable("id") Long id,
                                @Valid TeamForm teamForm,
                                BindingResult bindingResult,
                                ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_TEAM_FORM, teamForm);
            model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE, id));
            return TEAM_FORM;
        }

        teamService.edit(teamForm);

        return REDIRECT_TEAM_LIST;
    }

    @GetMapping("/new")
    public String initNewForm(ModelMap model) {
        model.put(MODEL_TEAM_FORM, TeamForm.create());
        model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE));
        return TEAM_FORM;
    }

    @PostMapping("/new")
    public String processNewForm(@Valid TeamForm teamForm,
                              BindingResult bindingResult,
                              ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_TEAM_FORM, teamForm);
            model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE));
            return TEAM_FORM;
        }

        teamService.add(teamForm);

        return REDIRECT_TEAM_LIST;
    }

}
