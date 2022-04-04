package ru.spb.altercom.forecastgame.form;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public record TeamForm(Long id, @NotEmpty String name) {

    public static TeamForm create() {
        return new TeamForm(null, "");
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamForm teamForm)) return false;
        return Objects.equals(id, teamForm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
