package ru.spb.altercom.forecastgame.form;

import java.util.Objects;

public record PlayerForm(Long id, String name, String password, Boolean isAdmin) {

    public static PlayerForm create() {
        return new PlayerForm();
    }

    private PlayerForm() {
        this(null, "", "", false);
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
        if (!(o instanceof PlayerForm)) return false;
        PlayerForm that = (PlayerForm) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
