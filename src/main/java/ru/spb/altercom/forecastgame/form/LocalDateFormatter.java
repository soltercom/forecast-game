package ru.spb.altercom.forecastgame.form;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public @NotNull LocalDate parse(@NotNull String text, @NotNull Locale locale) throws ParseException {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public @NotNull String print(LocalDate date, @NotNull Locale locale) {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
