package ru.spb.altercom.forecastgame.form;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record MatchForm(Long id,
                        @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date,
                        @DateTimeFormat(pattern = "HH:mm") LocalTime time,
                        TeamForm home,
                        TeamForm visitor,
                        String info,
                        int homeScore,
                        int visitorScore) {

    public static MatchForm create() {
        return new MatchForm();
    }

    private MatchForm() {
        this(null, null, null, null, null, "", 0, 0);
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchForm matchForm)) return false;
        return id.equals(matchForm.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
