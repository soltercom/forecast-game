package ru.spb.altercom.forecastgame.validator;


import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.spb.altercom.forecastgame.form.TeamForm;

public class TeamFormValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return TeamForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        var teamForm = (TeamForm) target;

        if (!StringUtils.hasText(teamForm.name())) {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }
    }
}
