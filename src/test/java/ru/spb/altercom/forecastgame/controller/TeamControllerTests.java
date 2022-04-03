package ru.spb.altercom.forecastgame.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spb.altercom.forecastgame.form.TeamForm;
import ru.spb.altercom.forecastgame.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.spb.altercom.forecastgame.helpers.Stubs.getListOfTeamForms;
import static ru.spb.altercom.forecastgame.helpers.Stubs.getNewTeamForm;

@WebMvcTest(TeamController.class)
class TeamControllerTests {

    private static final String REDIRECT_TEAM_LIST = "redirect:/teams";
    private static final String TEAM_LIST = "/team/list";
    private static final String TEAM_FORM = "/team/form";
    private static final Long TEAM_ID = 1001L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @BeforeEach
    void setup() {
        var teamForms = List.of(getNewTeamForm(), getNewTeamForm(), getNewTeamForm());
        given(teamService.findAll()).willReturn(teamForms);
        given(teamService.findById(TEAM_ID)).willReturn(teamForms.get(0));
    }

    @Test
    @DisplayName("GET /teams")
    void list() throws Exception {
        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("list", teamService.findAll()))
                .andExpect(view().name(TEAM_LIST));
    }

    @Test
    @DisplayName("GET /teams/{id}/edit")
    void initForm() throws Exception {
        mockMvc.perform(get("/teams/{id}/edit", TEAM_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("teamForm"))
            .andExpect(model().attribute("teamForm", teamService.findById(TEAM_ID)))
            .andExpect(view().name(TEAM_FORM));
    }

    @Test
    @DisplayName("POST /teams/{id}/edit")
    void processFormSuccess() throws Exception {
        mockMvc.perform(post("/teams/{id}/edit", TEAM_ID)
            .param("id", TEAM_ID.toString())
            .param("name", "Test"))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().hasNoErrors())
            .andExpect(view().name(REDIRECT_TEAM_LIST));
    }

    @Test
    @DisplayName("POST /teams/{id}/edit with empty team name")
    void processFormHasErrors() throws Exception {
        mockMvc.perform(post("/teams/{id}/edit", TEAM_ID)
                        .param("id", TEAM_ID.toString())
                        .param("name", ""))
                .andExpect(model().attributeHasErrors("teamForm"))
                .andExpect(model().attributeHasFieldErrors("teamForm", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name(TEAM_FORM));
    }

}
