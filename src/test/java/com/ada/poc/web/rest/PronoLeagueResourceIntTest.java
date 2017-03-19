package com.ada.poc.web.rest;

import com.ada.poc.AdaApp;

import com.ada.poc.domain.PronoLeague;
import com.ada.poc.repository.PronoLeagueRepository;
import com.ada.poc.service.PronoLeagueService;
import com.ada.poc.service.dto.PronoLeagueDTO;
import com.ada.poc.service.mapper.PronoLeagueMapper;
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
 * Test class for the PronoLeagueResource REST controller.
 *
 * @see PronoLeagueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdaApp.class)
public class PronoLeagueResourceIntTest {

    private static final String DEFAULT_LEAGUE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LEAGUE_NAME = "BBBBBBBBBB";

    @Autowired
    private PronoLeagueRepository pronoLeagueRepository;

    @Autowired
    private PronoLeagueMapper pronoLeagueMapper;

    @Autowired
    private PronoLeagueService pronoLeagueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPronoLeagueMockMvc;

    private PronoLeague pronoLeague;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PronoLeagueResource pronoLeagueResource = new PronoLeagueResource(pronoLeagueService);
        this.restPronoLeagueMockMvc = MockMvcBuilders.standaloneSetup(pronoLeagueResource)
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
    public static PronoLeague createEntity(EntityManager em) {
        PronoLeague pronoLeague = new PronoLeague()
            .leagueName(DEFAULT_LEAGUE_NAME);
        return pronoLeague;
    }

    @Before
    public void initTest() {
        pronoLeague = createEntity(em);
    }

    @Test
    @Transactional
    public void createPronoLeague() throws Exception {
        int databaseSizeBeforeCreate = pronoLeagueRepository.findAll().size();

        // Create the PronoLeague
        PronoLeagueDTO pronoLeagueDTO = pronoLeagueMapper.pronoLeagueToPronoLeagueDTO(pronoLeague);
        restPronoLeagueMockMvc.perform(post("/api/prono-leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoLeagueDTO)))
            .andExpect(status().isCreated());

        // Validate the PronoLeague in the database
        List<PronoLeague> pronoLeagueList = pronoLeagueRepository.findAll();
        assertThat(pronoLeagueList).hasSize(databaseSizeBeforeCreate + 1);
        PronoLeague testPronoLeague = pronoLeagueList.get(pronoLeagueList.size() - 1);
        assertThat(testPronoLeague.getLeagueName()).isEqualTo(DEFAULT_LEAGUE_NAME);
    }

    @Test
    @Transactional
    public void createPronoLeagueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pronoLeagueRepository.findAll().size();

        // Create the PronoLeague with an existing ID
        pronoLeague.setId(1L);
        PronoLeagueDTO pronoLeagueDTO = pronoLeagueMapper.pronoLeagueToPronoLeagueDTO(pronoLeague);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPronoLeagueMockMvc.perform(post("/api/prono-leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoLeagueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PronoLeague> pronoLeagueList = pronoLeagueRepository.findAll();
        assertThat(pronoLeagueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPronoLeagues() throws Exception {
        // Initialize the database
        pronoLeagueRepository.saveAndFlush(pronoLeague);

        // Get all the pronoLeagueList
        restPronoLeagueMockMvc.perform(get("/api/prono-leagues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pronoLeague.getId().intValue())))
            .andExpect(jsonPath("$.[*].leagueName").value(hasItem(DEFAULT_LEAGUE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPronoLeague() throws Exception {
        // Initialize the database
        pronoLeagueRepository.saveAndFlush(pronoLeague);

        // Get the pronoLeague
        restPronoLeagueMockMvc.perform(get("/api/prono-leagues/{id}", pronoLeague.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pronoLeague.getId().intValue()))
            .andExpect(jsonPath("$.leagueName").value(DEFAULT_LEAGUE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPronoLeague() throws Exception {
        // Get the pronoLeague
        restPronoLeagueMockMvc.perform(get("/api/prono-leagues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePronoLeague() throws Exception {
        // Initialize the database
        pronoLeagueRepository.saveAndFlush(pronoLeague);
        int databaseSizeBeforeUpdate = pronoLeagueRepository.findAll().size();

        // Update the pronoLeague
        PronoLeague updatedPronoLeague = pronoLeagueRepository.findOne(pronoLeague.getId());
        updatedPronoLeague
            .leagueName(UPDATED_LEAGUE_NAME);
        PronoLeagueDTO pronoLeagueDTO = pronoLeagueMapper.pronoLeagueToPronoLeagueDTO(updatedPronoLeague);

        restPronoLeagueMockMvc.perform(put("/api/prono-leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoLeagueDTO)))
            .andExpect(status().isOk());

        // Validate the PronoLeague in the database
        List<PronoLeague> pronoLeagueList = pronoLeagueRepository.findAll();
        assertThat(pronoLeagueList).hasSize(databaseSizeBeforeUpdate);
        PronoLeague testPronoLeague = pronoLeagueList.get(pronoLeagueList.size() - 1);
        assertThat(testPronoLeague.getLeagueName()).isEqualTo(UPDATED_LEAGUE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPronoLeague() throws Exception {
        int databaseSizeBeforeUpdate = pronoLeagueRepository.findAll().size();

        // Create the PronoLeague
        PronoLeagueDTO pronoLeagueDTO = pronoLeagueMapper.pronoLeagueToPronoLeagueDTO(pronoLeague);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPronoLeagueMockMvc.perform(put("/api/prono-leagues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronoLeagueDTO)))
            .andExpect(status().isCreated());

        // Validate the PronoLeague in the database
        List<PronoLeague> pronoLeagueList = pronoLeagueRepository.findAll();
        assertThat(pronoLeagueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePronoLeague() throws Exception {
        // Initialize the database
        pronoLeagueRepository.saveAndFlush(pronoLeague);
        int databaseSizeBeforeDelete = pronoLeagueRepository.findAll().size();

        // Get the pronoLeague
        restPronoLeagueMockMvc.perform(delete("/api/prono-leagues/{id}", pronoLeague.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PronoLeague> pronoLeagueList = pronoLeagueRepository.findAll();
        assertThat(pronoLeagueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PronoLeague.class);
    }
}
