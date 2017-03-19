package com.ada.poc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ada.poc.service.PronoGameService;
import com.ada.poc.web.rest.util.HeaderUtil;
import com.ada.poc.web.rest.util.PaginationUtil;
import com.ada.poc.service.dto.PronoGameDTO;
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
 * REST controller for managing PronoGame.
 */
@RestController
@RequestMapping("/api")
public class PronoGameResource {

    private final Logger log = LoggerFactory.getLogger(PronoGameResource.class);

    private static final String ENTITY_NAME = "pronoGame";
        
    private final PronoGameService pronoGameService;

    public PronoGameResource(PronoGameService pronoGameService) {
        this.pronoGameService = pronoGameService;
    }

    /**
     * POST  /prono-games : Create a new pronoGame.
     *
     * @param pronoGameDTO the pronoGameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pronoGameDTO, or with status 400 (Bad Request) if the pronoGame has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prono-games")
    @Timed
    public ResponseEntity<PronoGameDTO> createPronoGame(@RequestBody PronoGameDTO pronoGameDTO) throws URISyntaxException {
        log.debug("REST request to save PronoGame : {}", pronoGameDTO);
        if (pronoGameDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pronoGame cannot already have an ID")).body(null);
        }
        PronoGameDTO result = pronoGameService.save(pronoGameDTO);
        return ResponseEntity.created(new URI("/api/prono-games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prono-games : Updates an existing pronoGame.
     *
     * @param pronoGameDTO the pronoGameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pronoGameDTO,
     * or with status 400 (Bad Request) if the pronoGameDTO is not valid,
     * or with status 500 (Internal Server Error) if the pronoGameDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prono-games")
    @Timed
    public ResponseEntity<PronoGameDTO> updatePronoGame(@RequestBody PronoGameDTO pronoGameDTO) throws URISyntaxException {
        log.debug("REST request to update PronoGame : {}", pronoGameDTO);
        if (pronoGameDTO.getId() == null) {
            return createPronoGame(pronoGameDTO);
        }
        PronoGameDTO result = pronoGameService.save(pronoGameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pronoGameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prono-games : get all the pronoGames.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pronoGames in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/prono-games")
    @Timed
    public ResponseEntity<List<PronoGameDTO>> getAllPronoGames(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PronoGames");
        Page<PronoGameDTO> page = pronoGameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prono-games");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prono-games/:id : get the "id" pronoGame.
     *
     * @param id the id of the pronoGameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pronoGameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/prono-games/{id}")
    @Timed
    public ResponseEntity<PronoGameDTO> getPronoGame(@PathVariable Long id) {
        log.debug("REST request to get PronoGame : {}", id);
        PronoGameDTO pronoGameDTO = pronoGameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pronoGameDTO));
    }

    /**
     * DELETE  /prono-games/:id : delete the "id" pronoGame.
     *
     * @param id the id of the pronoGameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prono-games/{id}")
    @Timed
    public ResponseEntity<Void> deletePronoGame(@PathVariable Long id) {
        log.debug("REST request to delete PronoGame : {}", id);
        pronoGameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
