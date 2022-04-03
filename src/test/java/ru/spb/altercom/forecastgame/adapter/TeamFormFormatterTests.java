package ru.spb.altercom.forecastgame.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.form.TeamFormFormatter;
import ru.spb.altercom.forecastgame.service.TeamService;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class TeamFormFormatterTests {

    private final static Long TEAM_ID = 999L;
    private final static TeamForm TEAM_FORM = new TeamForm(TEAM_ID, "TEST");

    @MockBean
    private TeamService teamService;

    @BeforeEach
    void setup() {
        given(teamService.findById(any())).willReturn(TEAM_FORM);
    }

    @Test
    @DisplayName("#parse -> #print")
    void conversion() throws Exception {
        var locale = Locale.getDefault();
        var formatter = new TeamFormFormatter(teamService);
        var teamForm = formatter.parse(TEAM_ID.toString(), locale);
        var result = formatter.print(teamForm, locale);

        assertThat(TEAM_ID.toString()).hasToString(result);
    }

    @Test
    @DisplayName("#print -> #parse")
    void reverseConversion() throws Exception {
        var locale = Locale.getDefault();
        var formatter = new TeamFormFormatter(teamService);
        var result = formatter.parse(formatter.print(TEAM_FORM, locale), locale);

        assertThat(result).isEqualTo(TEAM_FORM);
    }

}
