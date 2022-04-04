package ru.spb.altercom.forecastgame.validator;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.spb.altercom.forecastgame.form.MatchForm;

public class MatchFormValidator implements Validator {

    private static final String REQUIRED = "required";
    private static final String NEGATIVE = "negative";

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return MatchForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        var matchForm = (MatchForm) target;

        if (matchForm.date() == null) {
            errors.rejectValue("date", REQUIRED, REQUIRED);
        }

        if (matchForm.time() == null) {
            errors.rejectValue("time", REQUIRED, REQUIRED);
        }

        if (matchForm.home() == null) {
            errors.rejectValue("home", REQUIRED, REQUIRED);
        }

        if (matchForm.visitor() == null) {
            errors.rejectValue("visitor", REQUIRED, REQUIRED);
        }

        if (matchForm.homeScore() < 0) {
            errors.rejectValue("homeScore", NEGATIVE, NEGATIVE);
        }

        if (matchForm.visitorScore() < 0) {
            errors.rejectValue("visitorScore", NEGATIVE, NEGATIVE);
        }
    }
}
