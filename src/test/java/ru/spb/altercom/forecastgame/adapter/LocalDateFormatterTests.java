package ru.spb.altercom.forecastgame.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spb.altercom.forecastgame.form.LocalDateFormatter;

import java.time.LocalDate;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateFormatterTests {

    @Test
    @DisplayName("#parse")
    void parse() throws Exception {
        var formatter = new LocalDateFormatter();
        var locale = Locale.getDefault();
        var test = "2020-01-01";
        var result = formatter.parse(test, locale);

        assertThat(LocalDate.of(2020, 1, 1)).isEqualTo(result);
    }

    @Test
    @DisplayName("#print")
    void print() {
        var formatter = new LocalDateFormatter();
        var locale = Locale.getDefault();
        var test = LocalDate.of(2020, 1, 1);
        var result = formatter.print(test, locale);

        assertThat(result).isEqualTo("01.01.2020");
    }

}
