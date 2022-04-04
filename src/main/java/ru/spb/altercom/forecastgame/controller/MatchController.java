package ru.spb.altercom.forecastgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.spb.altercom.forecastgame.form.LocalDateFormatter;
import ru.spb.altercom.forecastgame.form.MatchForm;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.form.TeamFormFormatter;
import ru.spb.altercom.forecastgame.service.MatchService;
import ru.spb.altercom.forecastgame.service.TeamService;
import ru.spb.altercom.forecastgame.validator.MatchFormValidator;

import javax.validation.Valid;
import java.util.List;

import static ru.spb.altercom.forecastgame.utils.Utility.getFormTitle;

@Controller
@RequestMapping("/matches")
public class MatchController {

    private static final String REDIRECT_MATCH_LIST = "redirect:/matches";
    private static final String MATCH_LIST = "/match/list";
    private static final String MATCH_FORM = "/match/form";
    private static final String MODEL_MATCH_FORM = "matchForm";
    private static final String FORM_TITLE = "formTitle";
    private static final String FORM_TITLE_VALUE = "Match";
    private static final String LIST_FORM_TITLE_VALUE = "Matches";

    private final MatchService matchService;
    private final TeamService teamService;
    private final LocalDateFormatter localDateFormatter;
    private final TeamFormFormatter teamFormFormatter;

    @Autowired
    public MatchController(MatchService matchService,
                           TeamService teamService,
                           LocalDateFormatter localDateFormatter,
                           TeamFormFormatter teamFormFormatter) {
        this.matchService = matchService;
        this.teamService = teamService;
        this.localDateFormatter = localDateFormatter;
        this.teamFormFormatter = teamFormFormatter;
    }

    @InitBinder("matchForm")
    public void matchFormBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new MatchFormValidator());
    }

    @ModelAttribute("teams")
    public List<TeamForm> getTeams() {
        return teamService.findAll();
    }

    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        binder.addCustomFormatter(localDateFormatter);
        binder.addCustomFormatter(teamFormFormatter);
    }

    @GetMapping
    public String list(ModelMap model) {
        model.put("list", matchService.findAll());
        model.put(FORM_TITLE, LIST_FORM_TITLE_VALUE);
        return MATCH_LIST;
    }

    @GetMapping("/{id}/edit")
    public String initForm(@PathVariable("id") Long id, ModelMap model) {
        model.put(MODEL_MATCH_FORM, matchService.findById(id));
        model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE, id));
        return MATCH_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processForm(@PathVariable("id") Long id,
                                    @Valid MatchForm matchForm,
                                    BindingResult bindingResult,
                                    ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_MATCH_FORM, matchForm);
            model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE, id));
            return MATCH_FORM;
        }

        matchService.edit(matchForm);

        return REDIRECT_MATCH_LIST;
    }

    @GetMapping("/new")
    public String initNewForm(ModelMap model) {
        model.put(MODEL_MATCH_FORM, MatchForm.create());
        model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE));
        return MATCH_FORM;
    }

    @PostMapping("/new")
    public String processNewForm(@Valid MatchForm matchForm,
                              BindingResult bindingResult,
                              ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.put(MODEL_MATCH_FORM, matchForm);
            model.put(FORM_TITLE, getFormTitle(FORM_TITLE_VALUE));
            return MATCH_FORM;
        }

        matchService.add(matchForm);

        return REDIRECT_MATCH_LIST;
    }

}
