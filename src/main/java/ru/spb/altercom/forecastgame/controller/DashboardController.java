package ru.spb.altercom.forecastgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spb.altercom.forecastgame.domain.Player;
import ru.spb.altercom.forecastgame.form.ForecastForm;
import ru.spb.altercom.forecastgame.service.ForecastService;

import javax.validation.Valid;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private static final String DASHBOARD_LIST = "/dashboard/list";
    private static final String REDIRECT_DASHBOARD_LIST = "redirect:/dashboard";

    private final ForecastService forecastService;

    @Autowired
    public DashboardController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @ModelAttribute("currentPlayerId")
    Long getCurrentPlayerId(@AuthenticationPrincipal Player currentPlayer) {
        return currentPlayer == null ? null : currentPlayer.getId();
    }

    @GetMapping
    public String index(ModelMap model) {
        model.put("data", forecastService.getData().entrySet());
        return DASHBOARD_LIST;
    }

    @PostMapping("/edit/{matchId}")
    public String edit(@PathVariable Long matchId, ModelMap model) {
        model.put("data", forecastService.getData().entrySet());
        model.put("editedMatch", matchId);
        model.put("forecastForm", forecastService.findByMatchAndPlayer(matchId, (Long) model.get("currentPlayerId")));
        return DASHBOARD_LIST;
    }

    @PostMapping("/save")
    public String save(@Valid ForecastForm forecastForm,
                       BindingResult bindingResult,
                       ModelMap model) {
        forecastService.save(forecastForm);
        return REDIRECT_DASHBOARD_LIST;
    }

}
