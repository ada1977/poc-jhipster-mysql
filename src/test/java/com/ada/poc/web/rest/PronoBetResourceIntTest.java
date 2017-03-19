package com.ada.poc.web.rest;

import com.ada.poc.AdaApp;

import com.ada.poc.domain.PronoBet;
import com.ada.poc.repository.PronoBetRepository;
import com.ada.poc.service.PronoBetService;
import com.ada.poc.service.dto.PronoBetDTO;
import com.ada.poc.service.mapper.PronoBetMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PronoBetResource REST controller.
 *
 * @see PronoBetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdaApp.class)
public class PronoBetResourceIntTest {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE_1 = 1;
    private static final Integer UPDATED_SCORE_1 = 2;

    private static final Integer DEFAULT_SCORE_2 = 1;
    private static final Integer UPDATED_SCORE_2 = 2;

    private static final Integer DEFAULT_RESULT = 1;
    private static final Integer UPDATED_RESULT = 2;

    @Autowired
    private PronoBetRepository pronoBetRepository;

    @Autowired
    private PronoBetMapper pronoBetMapper;

    @Autowired
    private PronoBetService pronoBetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPronoBetMockMvc;

    private PronoBet pronoBet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PronoBetResource pronoBetResource = new PronoBetResource(pronoBetService);
        this.restPronoBetMockMvc = MockMvcBuilders.standaloneSetup(pronoBetResource)
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
    public static PronoBet createEntity(EntityManager em) {
        PronoBet pronoBet = new PronoBet()
            .user(DEFAULT_USER)
            .score1(DEFAULT_SCORE_1)
            .score2(DEFAULT_SCORE_2)
            .result(DEFAULT_RESULT);
        return pronoBet;
    }

    @Before
    public void initTest() {
        pronoBet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPronoBet() throws Exception {
        int databaseSizeBeforeCreate = pronoBetRepository.findAll().size();

        // Create the PronoBet
        PronoBetDTO pronoBetDTO = pronoBetMapper.pronoBetToPronoBetDTO(pronoBet);
        restPronoBetMockMvc.perform(post("/api/prono-bets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoBetDTO)))
            .andExpect(status().isCreated());

        // Validate the PronoBet in the database
        List<PronoBet> pronoBetList = pronoBetRepository.findAll();
        assertThat(pronoBetList).hasSize(databaseSizeBeforeCreate + 1);
        PronoBet testPronoBet = pronoBetList.get(pronoBetList.size() - 1);
        assertThat(testPronoBet.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testPronoBet.getScore1()).isEqualTo(DEFAULT_SCORE_1);
        assertThat(testPronoBet.getScore2()).isEqualTo(DEFAULT_SCORE_2);
        assertThat(testPronoBet.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    public void createPronoBetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pronoBetRepository.findAll().size();

        // Create the PronoBet with an existing ID
        pronoBet.setId(1L);
        PronoBetDTO pronoBetDTO = pronoBetMapper.pronoBetToPronoBetDTO(pronoBet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPronoBetMockMvc.perform(post("/api/prono-bets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoBetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PronoBet> pronoBetList = pronoBetRepository.findAll();
        assertThat(pronoBetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPronoBets() throws Exception {
        // Initialize the database
        pronoBetRepository.saveAndFlush(pronoBet);

        // Get all the pronoBetList
        restPronoBetMockMvc.perform(get("/api/prono-bets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pronoBet.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
            .andExpect(jsonPath("$.[*].score1").value(hasItem(DEFAULT_SCORE_1)))
            .andExpect(jsonPath("$.[*].score2").value(hasItem(DEFAULT_SCORE_2)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)));
    }

    @Test
    @Transactional
    public void getPronoBet() throws Exception {
        // Initialize the database
        pronoBetRepository.saveAndFlush(pronoBet);

        // Get the pronoBet
        restPronoBetMockMvc.perform(get("/api/prono-bets/{id}", pronoBet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pronoBet.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.score1").value(DEFAULT_SCORE_1))
            .andExpect(jsonPath("$.score2").value(DEFAULT_SCORE_2))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT));
    }

    @Test
    @Transactional
    public void getNonExistingPronoBet() throws Exception {
        // Get the pronoBet
        restPronoBetMockMvc.perform(get("/api/prono-bets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePronoBet() throws Exception {
        // Initialize the database
        pronoBetRepository.saveAndFlush(pronoBet);
        int databaseSizeBeforeUpdate = pronoBetRepository.findAll().size();

        // Update the pronoBet
        PronoBet updatedPronoBet = pronoBetRepository.findOne(pronoBet.getId());
        updatedPronoBet
            .user(UPDATED_USER)
            .score1(UPDATED_SCORE_1)
            .score2(UPDATED_SCORE_2)
            .result(UPDATED_RESULT);
        PronoBetDTO pronoBetDTO = pronoBetMapper.pronoBetToPronoBetDTO(updatedPronoBet);

        restPronoBetMockMvc.perform(put("/api/prono-bets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoBetDTO)))
            .andExpect(status().isOk());

        // Validate the PronoBet in the database
        List<PronoBet> pronoBetList = pronoBetRepository.findAll();
        assertThat(pronoBetList).hasSize(databaseSizeBeforeUpdate);
        PronoBet testPronoBet = pronoBetList.get(pronoBetList.size() - 1);
        assertThat(testPronoBet.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testPronoBet.getScore1()).isEqualTo(UPDATED_SCORE_1);
        assertThat(testPronoBet.getScore2()).isEqualTo(UPDATED_SCORE_2);
        assertThat(testPronoBet.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void updateNonExistingPronoBet() throws Exception {
        int databaseSizeBeforeUpdate = pronoBetRepository.findAll().size();

        // Create the PronoBet
        PronoBetDTO pronoBetDTO = pronoBetMapper.pronoBetToPronoBetDTO(pronoBet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPronoBetMockMvc.perform(put("/api/prono-bets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoBetDTO)))
            .andExpect(status().isCreated());

        // Validate the PronoBet in the database
        List<PronoBet> pronoBetList = pronoBetRepository.findAll();
        assertThat(pronoBetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePronoBet() throws Exception {
        // Initialize the database
        pronoBetRepository.saveAndFlush(pronoBet);
        int databaseSizeBeforeDelete = pronoBetRepository.findAll().size();

        // Get the pronoBet
        restPronoBetMockMvc.perform(delete("/api/prono-bets/{id}", pronoBet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PronoBet> pronoBetList = pronoBetRepository.findAll();
        assertThat(pronoBetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PronoBet.class);
    }
}
