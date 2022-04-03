package ru.spb.altercom.forecastgame.form;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public record PlayerForm(Long id, @NotEmpty String name) {

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
        if (!(o instanceof PlayerForm that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
