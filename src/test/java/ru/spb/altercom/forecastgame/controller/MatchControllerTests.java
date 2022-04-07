package ru.spb.altercom.forecastgame.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.spb.altercom.forecastgame.form.LocalDateFormatter;
import ru.spb.altercom.forecastgame.form.TeamFormFormatter;
import ru.spb.altercom.forecastgame.service.MatchService;
import ru.spb.altercom.forecastgame.service.TeamService;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.spb.altercom.forecastgame.helpers.Stubs.*;

@WebMvcTest(MatchController.class)
@WithMockUser(authorities = "ADMIN")
class MatchControllerTests {

    private static final String REDIRECT_MATCH_LIST = "redirect:/matches";
    private static final String MATCH_LIST = "/match/list";
    private static final String MATCH_FORM = "/match/form";
    private static final Long MATCH_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;
    @MockBean
    private TeamService teamService;
    @MockBean
    private LocalDateFormatter localDateFormatter;
    @MockBean
    private TeamFormFormatter teamFormFormatter;

    @BeforeEach
    void setup() throws Exception {
        var teamForms = List.of(getNewTeamForm(), getNewTeamForm());
        var matchForm = getNewMatchForm(teamForms.get(0), teamForms.get(1));

        given(teamService.findAll()).willReturn(teamForms);
        given(matchService.findById(any())).willReturn(matchForm);

        given(localDateFormatter.parse(anyString(), any())).willReturn(LocalDate.of(2022, 1, 1));
        given(localDateFormatter.print(any(), any())).willReturn("01.01.2022");

        given(teamFormFormatter.parse(any(), any())).willReturn(teamForms.get(0));
        given(teamFormFormatter.print(any(), any())).willReturn(MATCH_ID.toString());
    }

    @Test
    @DisplayName("GET /matches")
    void list() throws Exception {
        mockMvc.perform(get("/matches"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("list", matchService.findAll()))
                .andExpect(model().attribute("formTitle", "Matches"))
                .andExpect(view().name(MATCH_LIST));
    }

    @Test
    @DisplayName("GET /matches/{id}/edit")
    void initForm() throws Exception {
        mockMvc.perform(get("/matches/{id}/edit", MATCH_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("matchForm"))
                .andExpect(model().attribute("matchForm", matchService.findById(MATCH_ID)))
                .andExpect(model().attributeExists("teams"))
                .andExpect(model().attribute("teams", teamService.findAll()))
                .andExpect(model().attribute("formTitle", "Match (" + MATCH_ID + ")"))
                .andExpect(view().name(MATCH_FORM));
    }

    @Test
    @DisplayName("POST /matches/{id}/edit")
    void processFormSuccess() throws Exception {
        mockMvc.perform(post("/matches/{id}/edit", MATCH_ID)
                        .param("id", MATCH_ID.toString())
                        .param("date", "01.01.2002")
                        .param("time", "23:00")
                        .param("home", MATCH_ID.toString())
                        .param("visitor", MATCH_ID.toString())
                        .param("info", "")
                        .param("homeScore", "1")
                        .param("visitorScore", "1")
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_MATCH_LIST));
    }

    @Test
    @DisplayName("POST /matches/{id}/edit with empty match fields")
    void processFormHasErrors() throws Exception {
        mockMvc.perform(post("/matches/{id}/edit", MATCH_ID)
                        .param("id", MATCH_ID.toString())
                        .param("homeScore", "-1")
                        .param("visitorScore", "-1")
                        .with(csrf()))
                .andExpect(model().attributeHasErrors("matchForm"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "date"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "time"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "home"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "visitor"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "homeScore"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "visitorScore"))
                .andExpect(model().attribute("formTitle", "Match (" + MATCH_ID + ")"))
                .andExpect(status().isOk())
                .andExpect(view().name(MATCH_FORM));
    }

    @Test
    @DisplayName("GET /matches/new")
    void initNewForm() throws Exception {
        mockMvc.perform(get("/matches/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("matchForm"))
                .andExpect(model().attributeExists("teams"))
                .andExpect(model().attribute("teams", teamService.findAll()))
                .andExpect(model().attribute("formTitle", "Match (New)"))
                .andExpect(view().name(MATCH_FORM));
    }

    @Test
    @DisplayName("POST /matches/new")
    void processNewFormSuccess() throws Exception {
        mockMvc.perform(post("/matches/new")
                        .param("date", "01.01.2002")
                        .param("time", "23:00")
                        .param("home", MATCH_ID.toString())
                        .param("visitor", MATCH_ID.toString())
                        .param("info", "")
                        .param("homeScore", "1")
                        .param("visitorScore", "1")
                        .with(csrf()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_MATCH_LIST));
    }

    @Test
    @DisplayName("POST /matches/new with empty match fields")
    void processNewFormHasErrors() throws Exception {
        mockMvc.perform(post("/matches/new")
                        .param("homeScore", "-1")
                        .param("visitorScore", "-1")
                        .with(csrf()))
                .andExpect(model().attributeHasErrors("matchForm"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "date"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "time"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "home"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "visitor"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "homeScore"))
                .andExpect(model().attributeHasFieldErrors("matchForm", "visitorScore"))
                .andExpect(model().attribute("formTitle", "Match (New)"))
                .andExpect(status().isOk())
                .andExpect(view().name(MATCH_FORM));
    }

}
