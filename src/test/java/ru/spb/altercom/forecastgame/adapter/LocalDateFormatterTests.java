package ru.spb.altercom.forecastgame.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.spb.altercom.forecastgame.form.LocalDateFormatter;

import java.time.LocalDate;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateFormatterTests {

    @Test
    @DisplayName("#parse -> #print")
    void conversion() throws Exception {
        var formatter = new LocalDateFormatter();
        var locale = Locale.getDefault();
        var test = "01.01.2020";
        var result = formatter.print(formatter.parse(test, locale), locale);

        assertThat(test).isEqualTo(result);
    }

    @Test
    @DisplayName("#print -> #parse")
    void reverseConversion() throws Exception {
        var formatter = new LocalDateFormatter();
        var locale = Locale.getDefault();
        var test = LocalDate.of(2020, 1, 1);
        var result = formatter.parse(formatter.print(test, locale), locale);

        assertThat(test).isEqualTo(result);
    }

}
