package com.ada.poc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ada.poc.service.PronoLeagueService;
import com.ada.poc.web.rest.util.HeaderUtil;
import com.ada.poc.web.rest.util.PaginationUtil;
import com.ada.poc.service.dto.PronoLeagueDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing PronoLeague.
 */
@RestController
@RequestMapping("/api")
public class PronoLeagueResource {

    private final Logger log = LoggerFactory.getLogger(PronoLeagueResource.class);

    private static final String ENTITY_NAME = "pronoLeague";
        
    private final PronoLeagueService pronoLeagueService;

    public PronoLeagueResource(PronoLeagueService pronoLeagueService) {
        this.pronoLeagueService = pronoLeagueService;
    }

    /**
     * POST  /prono-leagues : Create a new pronoLeague.
     *
     * @param pronoLeagueDTO the pronoLeagueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pronoLeagueDTO, or with status 400 (Bad Request) if the pronoLeague has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prono-leagues")
    @Timed
    public ResponseEntity<PronoLeagueDTO> createPronoLeague(@RequestBody PronoLeagueDTO pronoLeagueDTO) throws URISyntaxException {
        log.debug("REST request to save PronoLeague : {}", pronoLeagueDTO);
        if (pronoLeagueDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pronoLeague cannot already have an ID")).body(null);
        }
        PronoLeagueDTO result = pronoLeagueService.save(pronoLeagueDTO);
        return ResponseEntity.created(new URI("/api/prono-leagues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prono-leagues : Updates an existing pronoLeague.
     *
     * @param pronoLeagueDTO the pronoLeagueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pronoLeagueDTO,
     * or with status 400 (Bad Request) if the pronoLeagueDTO is not valid,
     * or with status 500 (Internal Server Error) if the pronoLeagueDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prono-leagues")
    @Timed
    public ResponseEntity<PronoLeagueDTO> updatePronoLeague(@RequestBody PronoLeagueDTO pronoLeagueDTO) throws URISyntaxException {
        log.debug("REST request to update PronoLeague : {}", pronoLeagueDTO);
        if (pronoLeagueDTO.getId() == null) {
            return createPronoLeague(pronoLeagueDTO);
        }
        PronoLeagueDTO result = pronoLeagueService.save(pronoLeagueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pronoLeagueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prono-leagues : get all the pronoLeagues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pronoLeagues in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/prono-leagues")
    @Timed
    public ResponseEntity<List<PronoLeagueDTO>> getAllPronoLeagues(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PronoLeagues");
        Page<PronoLeagueDTO> page = pronoLeagueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prono-leagues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prono-leagues/:id : get the "id" pronoLeague.
     *
     * @param id the id of the pronoLeagueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pronoLeagueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prono-leagues/{id}")
    @Timed
    public ResponseEntity<PronoLeagueDTO> getPronoLeague(@PathVariable Long id) {
        log.debug("REST request to get PronoLeague : {}", id);
        PronoLeagueDTO pronoLeagueDTO = pronoLeagueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pronoLeagueDTO));
    }

    /**
     * DELETE  /prono-leagues/:id : delete the "id" pronoLeague.
     *
     * @param id the id of the pronoLeagueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prono-leagues/{id}")
    @Timed
    public ResponseEntity<Void> deletePronoLeague(@PathVariable Long id) {
        log.debug("REST request to delete PronoLeague : {}", id);
        pronoLeagueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
