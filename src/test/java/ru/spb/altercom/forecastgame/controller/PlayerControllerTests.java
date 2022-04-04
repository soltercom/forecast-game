package ru.spb.altercom.forecastgame.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.spb.altercom.forecastgame.service.PlayerService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.spb.altercom.forecastgame.helpers.Stubs.getNewPlayerForm;

@WebMvcTest(PlayerController.class)
class PlayerControllerTests {

    private static final String REDIRECT_PLAYER_LIST = "redirect:/players";
    private static final String PLAYER_LIST = "/player/list";
    private static final String PLAYER_FORM = "/player/form";
    private static final Long PLAYER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @BeforeEach
    void setup() {
        var playerForms = List.of(getNewPlayerForm(), getNewPlayerForm(), getNewPlayerForm());
        given(playerService.findAll()).willReturn(playerForms);
        given(playerService.findById(PLAYER_ID)).willReturn(playerForms.get(0));
    }

    @Test
    @DisplayName("GET /players")
    void list() throws Exception {
        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("list"))
                .andExpect(model().attribute("list", playerService.findAll()))
                .andExpect(view().name(PLAYER_LIST));
    }

    @Test
    @DisplayName("GET /players/{id}/edit")
    void initForm() throws Exception {
        mockMvc.perform(get("/players/{id}/edit", PLAYER_ID))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("playerForm"))
                .andExpect(model().attribute("playerForm", playerService.findById(PLAYER_ID)))
                .andExpect(view().name(PLAYER_FORM));
    }

    @Test
    @DisplayName("POST /players/{id}/edit")
    void processFormSuccess() throws Exception {
        mockMvc.perform(post("/players/{id}/edit", PLAYER_ID)
                        .param("id", PLAYER_ID.toString())
                        .param("name", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name(REDIRECT_PLAYER_LIST));
    }

    @Test
    @DisplayName("POST /players/{id}/edit with empty player name")
    void processFormHasErrors() throws Exception {
        mockMvc.perform(post("/players/{id}/edit", PLAYER_ID)
                        .param("id", PLAYER_ID.toString())
                        .param("name", ""))
                .andExpect(model().attributeHasErrors("playerForm"))
                .andExpect(model().attributeHasFieldErrors("playerForm", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name(PLAYER_FORM));
    }

    @Test
    @DisplayName("GET /players/new")
    void initNewForm() throws Exception {
        mockMvc.perform(get("/players/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("playerForm"))
                .andExpect(view().name(PLAYER_FORM));
    }

    @Test
    @DisplayName("POST /players/new")
    void processNewFormSuccess() throws Exception {
        mockMvc.perform(post("/players/new")
                        .param("name", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors())
                .andExpect(view().name(REDIRECT_PLAYER_LIST));
    }

    @Test
    @DisplayName("POST /players/new with empty player name")
    void processNewFormHasErrors() throws Exception {
        mockMvc.perform(post("/players/new")
                        .param("name", ""))
                .andExpect(model().attributeHasErrors("playerForm"))
                .andExpect(model().attributeHasFieldErrors("playerForm", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name(PLAYER_FORM));
    }
}
