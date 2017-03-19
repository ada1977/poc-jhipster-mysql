package com.ada.poc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ada.poc.service.PronoBetService;
import com.ada.poc.web.rest.util.HeaderUtil;
import com.ada.poc.service.dto.PronoBetDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing PronoBet.
 */
@RestController
@RequestMapping("/api")
public class PronoBetResource {

    private final Logger log = LoggerFactory.getLogger(PronoBetResource.class);

    private static final String ENTITY_NAME = "pronoBet";
        
    private final PronoBetService pronoBetService;

    public PronoBetResource(PronoBetService pronoBetService) {
        this.pronoBetService = pronoBetService;
    }

    /**
     * POST  /prono-bets : Create a new pronoBet.
     *
     * @param pronoBetDTO the pronoBetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pronoBetDTO, or with status 400 (Bad Request) if the pronoBet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prono-bets")
    @Timed
    public ResponseEntity<PronoBetDTO> createPronoBet(@RequestBody PronoBetDTO pronoBetDTO) throws URISyntaxException {
        log.debug("REST request to save PronoBet : {}", pronoBetDTO);
        if (pronoBetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pronoBet cannot already have an ID")).body(null);
        }
        PronoBetDTO result = pronoBetService.save(pronoBetDTO);
        return ResponseEntity.created(new URI("/api/prono-bets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prono-bets : Updates an existing pronoBet.
     *
     * @param pronoBetDTO the pronoBetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pronoBetDTO,
     * or with status 400 (Bad Request) if the pronoBetDTO is not valid,
     * or with status 500 (Internal Server Error) if the pronoBetDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prono-bets")
    @Timed
    public ResponseEntity<PronoBetDTO> updatePronoBet(@RequestBody PronoBetDTO pronoBetDTO) throws URISyntaxException {
        log.debug("REST request to update PronoBet : {}", pronoBetDTO);
        if (pronoBetDTO.getId() == null) {
            return createPronoBet(pronoBetDTO);
        }
        PronoBetDTO result = pronoBetService.save(pronoBetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pronoBetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prono-bets : get all the pronoBets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pronoBets in body
     */
    @GetMapping("/prono-bets")
    @Timed
    public List<PronoBetDTO> getAllPronoBets() {
        log.debug("REST request to get all PronoBets");
        return pronoBetService.findAll();
    }

    /**
     * GET  /prono-bets/:id : get the "id" pronoBet.
     *
     * @param id the id of the pronoBetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pronoBetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prono-bets/{id}")
    @Timed
    public ResponseEntity<PronoBetDTO> getPronoBet(@PathVariable Long id) {
        log.debug("REST request to get PronoBet : {}", id);
        PronoBetDTO pronoBetDTO = pronoBetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pronoBetDTO));
    }

    /**
     * DELETE  /prono-bets/:id : delete the "id" pronoBet.
     *
     * @param id the id of the pronoBetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prono-bets/{id}")
    @Timed
    public ResponseEntity<Void> deletePronoBet(@PathVariable Long id) {
        log.debug("REST request to delete PronoBet : {}", id);
        pronoBetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
