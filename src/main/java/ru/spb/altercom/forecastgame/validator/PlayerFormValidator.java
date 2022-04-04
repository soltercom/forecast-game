package ru.spb.altercom.forecastgame.validator;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.spb.altercom.forecastgame.form.PlayerForm;

public class PlayerFormValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return PlayerForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        var playerForm = (PlayerForm) target;

        if (!StringUtils.hasText(playerForm.name())) {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }
    }
}
