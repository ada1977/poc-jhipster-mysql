package com.ada.poc.web.rest;

import com.ada.poc.AdaApp;

import com.ada.poc.domain.PronoGame;
import com.ada.poc.repository.PronoGameRepository;
import com.ada.poc.service.PronoGameService;
import com.ada.poc.service.dto.PronoGameDTO;
import com.ada.poc.service.mapper.PronoGameMapper;
import com.ada.poc.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ada.poc.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PronoGameResource REST controller.
 *
 * @see PronoGameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdaApp.class)
public class PronoGameResourceIntTest {

    private static final String DEFAULT_GAME_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GAME_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TEAM_1 = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_2 = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_2 = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE_1 = 1;
    private static final Integer UPDATED_SCORE_1 = 2;

    private static final Integer DEFAULT_SCORE_2 = 1;
    private static final Integer UPDATED_SCORE_2 = 2;

    @Autowired
    private PronoGameRepository pronoGameRepository;

    @Autowired
    private PronoGameMapper pronoGameMapper;

    @Autowired
    private PronoGameService pronoGameService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPronoGameMockMvc;

    private PronoGame pronoGame;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PronoGameResource pronoGameResource = new PronoGameResource(pronoGameService);
        this.restPronoGameMockMvc = MockMvcBuilders.standaloneSetup(pronoGameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PronoGame createEntity(EntityManager em) {
        PronoGame pronoGame = new PronoGame()
            .gameNumber(DEFAULT_GAME_NUMBER)
            .date(DEFAULT_DATE)
            .team1(DEFAULT_TEAM_1)
            .team2(DEFAULT_TEAM_2)
            .score1(DEFAULT_SCORE_1)
            .score2(DEFAULT_SCORE_2);
        return pronoGame;
    }

    @Before
    public void initTest() {
        pronoGame = createEntity(em);
    }

    @Test
    @Transactional
    public void createPronoGame() throws Exception {
        int databaseSizeBeforeCreate = pronoGameRepository.findAll().size();

        // Create the PronoGame
        PronoGameDTO pronoGameDTO = pronoGameMapper.pronoGameToPronoGameDTO(pronoGame);
        restPronoGameMockMvc.perform(post("/api/prono-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoGameDTO)))
            .andExpect(status().isCreated());

        // Validate the PronoGame in the database
        List<PronoGame> pronoGameList = pronoGameRepository.findAll();
        assertThat(pronoGameList).hasSize(databaseSizeBeforeCreate + 1);
        PronoGame testPronoGame = pronoGameList.get(pronoGameList.size() - 1);
        assertThat(testPronoGame.getGameNumber()).isEqualTo(DEFAULT_GAME_NUMBER);
        assertThat(testPronoGame.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPronoGame.getTeam1()).isEqualTo(DEFAULT_TEAM_1);
        assertThat(testPronoGame.getTeam2()).isEqualTo(DEFAULT_TEAM_2);
        assertThat(testPronoGame.getScore1()).isEqualTo(DEFAULT_SCORE_1);
        assertThat(testPronoGame.getScore2()).isEqualTo(DEFAULT_SCORE_2);
    }

    @Test
    @Transactional
    public void createPronoGameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pronoGameRepository.findAll().size();

        // Create the PronoGame with an existing ID
        pronoGame.setId(1L);
        PronoGameDTO pronoGameDTO = pronoGameMapper.pronoGameToPronoGameDTO(pronoGame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPronoGameMockMvc.perform(post("/api/prono-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoGameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PronoGame> pronoGameList = pronoGameRepository.findAll();
        assertThat(pronoGameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPronoGames() throws Exception {
        // Initialize the database
        pronoGameRepository.saveAndFlush(pronoGame);

        // Get all the pronoGameList
        restPronoGameMockMvc.perform(get("/api/prono-games?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pronoGame.getId().intValue())))
            .andExpect(jsonPath("$.[*].gameNumber").value(hasItem(DEFAULT_GAME_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].team1").value(hasItem(DEFAULT_TEAM_1.toString())))
            .andExpect(jsonPath("$.[*].team2").value(hasItem(DEFAULT_TEAM_2.toString())))
            .andExpect(jsonPath("$.[*].score1").value(hasItem(DEFAULT_SCORE_1)))
            .andExpect(jsonPath("$.[*].score2").value(hasItem(DEFAULT_SCORE_2)));
    }

    @Test
    @Transactional
    public void getPronoGame() throws Exception {
        // Initialize the database
        pronoGameRepository.saveAndFlush(pronoGame);

        // Get the pronoGame
        restPronoGameMockMvc.perform(get("/api/prono-games/{id}", pronoGame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pronoGame.getId().intValue()))
            .andExpect(jsonPath("$.gameNumber").value(DEFAULT_GAME_NUMBER.toString()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.team1").value(DEFAULT_TEAM_1.toString()))
            .andExpect(jsonPath("$.team2").value(DEFAULT_TEAM_2.toString()))
            .andExpect(jsonPath("$.score1").value(DEFAULT_SCORE_1))
            .andExpect(jsonPath("$.score2").value(DEFAULT_SCORE_2));
    }

    @Test
    @Transactional
    public void getNonExistingPronoGame() throws Exception {
        // Get the pronoGame
        restPronoGameMockMvc.perform(get("/api/prono-games/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePronoGame() throws Exception {
        // Initialize the database
        pronoGameRepository.saveAndFlush(pronoGame);
        int databaseSizeBeforeUpdate = pronoGameRepository.findAll().size();

        // Update the pronoGame
        PronoGame updatedPronoGame = pronoGameRepository.findOne(pronoGame.getId());
        updatedPronoGame
            .gameNumber(UPDATED_GAME_NUMBER)
            .date(UPDATED_DATE)
            .team1(UPDATED_TEAM_1)
            .team2(UPDATED_TEAM_2)
            .score1(UPDATED_SCORE_1)
            .score2(UPDATED_SCORE_2);
        PronoGameDTO pronoGameDTO = pronoGameMapper.pronoGameToPronoGameDTO(updatedPronoGame);

        restPronoGameMockMvc.perform(put("/api/prono-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoGameDTO)))
            .andExpect(status().isOk());

        // Validate the PronoGame in the database
        List<PronoGame> pronoGameList = pronoGameRepository.findAll();
        assertThat(pronoGameList).hasSize(databaseSizeBeforeUpdate);
        PronoGame testPronoGame = pronoGameList.get(pronoGameList.size() - 1);
        assertThat(testPronoGame.getGameNumber()).isEqualTo(UPDATED_GAME_NUMBER);
        assertThat(testPronoGame.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPronoGame.getTeam1()).isEqualTo(UPDATED_TEAM_1);
        assertThat(testPronoGame.getTeam2()).isEqualTo(UPDATED_TEAM_2);
        assertThat(testPronoGame.getScore1()).isEqualTo(UPDATED_SCORE_1);
        assertThat(testPronoGame.getScore2()).isEqualTo(UPDATED_SCORE_2);
    }

    @Test
    @Transactional
    public void updateNonExistingPronoGame() throws Exception {
        int databaseSizeBeforeUpdate = pronoGameRepository.findAll().size();

        // Create the PronoGame
        PronoGameDTO pronoGameDTO = pronoGameMapper.pronoGameToPronoGameDTO(pronoGame);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPronoGameMockMvc.perform(put("/api/prono-games")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoGameDTO)))
            .andExpect(status().isCreated());

        // Validate the PronoGame in the database
        List<PronoGame> pronoGameList = pronoGameRepository.findAll();
        assertThat(pronoGameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePronoGame() throws Exception {
        // Initialize the database
        pronoGameRepository.saveAndFlush(pronoGame);
        int databaseSizeBeforeDelete = pronoGameRepository.findAll().size();

        // Get the pronoGame
        restPronoGameMockMvc.perform(delete("/api/prono-games/{id}", pronoGame.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PronoGame> pronoGameList = pronoGameRepository.findAll();
        assertThat(pronoGameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PronoGame.class);
    }
}
