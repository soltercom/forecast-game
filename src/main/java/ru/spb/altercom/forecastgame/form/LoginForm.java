package ru.spb.altercom.forecastgame.form;

import javax.validation.constraints.NotEmpty;

public record LoginForm(@NotEmpty String username, String password) {

    public static LoginForm getLoginForm() {
        return new LoginForm("John Doe", "");
    }

}
