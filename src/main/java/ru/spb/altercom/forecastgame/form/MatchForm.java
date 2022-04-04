package ru.spb.altercom.forecastgame.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public record MatchForm(Long id,
                        @NotNull @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date,
                        @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime time,
                        @NotNull TeamForm home,
                        @NotNull TeamForm visitor,
                        String info,
                        @Min(0) int homeScore,
                        @Min(0) int visitorScore) {

    public static MatchForm create() {
        return new MatchForm(null, LocalDate.now(), LocalTime.now(),
                new TeamForm(null, ""), new TeamForm(null, ""),
                "", 0, 0);
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
