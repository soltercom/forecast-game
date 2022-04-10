package ru.spb.altercom.forecastgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ru.spb.altercom.forecastgame.form.LoginForm.getLoginForm;

@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String index() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String login(ModelMap model) {
        model.put("loginForm", getLoginForm());
        return "login";
    }
}
