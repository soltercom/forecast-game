package ru.spb.altercom.forecastgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.service.TeamService;

import javax.validation.Valid;

@Controller
@RequestMapping("/teams")
public class TeamController {

    private static final String REDIRECT_TEAM_LIST = "redirect:/teams";
    private static final String TEAM_LIST = "/team/list";
    private static final String TEAM_FORM = "/team/form";
    private static final String MODEL_TEAM_FORM = "teamForm";

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", teamService.findAll());
        return TEAM_LIST;
    }

    @GetMapping("/{id}/edit")
    public String initForm(@PathVariable("id") Long id, ModelMap model) {
        model.put(MODEL_TEAM_FORM, teamService.findById(id));
        return TEAM_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processForm(@PathVariable("id") Long id,
                                    @Valid TeamForm teamForm,
                                    BindingResult bindingResult,
                                    ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_TEAM_FORM, teamForm);
            return TEAM_FORM;
        }

        teamService.edit(teamForm);

        return REDIRECT_TEAM_LIST;
    }

    @GetMapping("/new")
    public String initNewForm(ModelMap model) {
        model.put(MODEL_TEAM_FORM, TeamForm.create());
        return TEAM_FORM;
    }

    @PostMapping("/new")
    public String processNewForm(@Valid TeamForm teamForm,
                              BindingResult bindingResult,
                              ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_TEAM_FORM, teamForm);
            return TEAM_FORM;
        }

        teamService.add(teamForm);

        return REDIRECT_TEAM_LIST;
    }

}
