package com.ada.poc.service;

import com.ada.poc.service.dto.PronoLeagueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PronoLeague.
 */
public interface PronoLeagueService {

    /**
     * Save a pronoLeague.
     *
     * @param pronoLeagueDTO the entity to save
     * @return the persisted entity
     */
    PronoLeagueDTO save(PronoLeagueDTO pronoLeagueDTO);

    /**
     *  Get all the pronoLeagues.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PronoLeagueDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pronoLeague.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PronoLeagueDTO findOne(Long id);

    /**
     *  Delete the "id" pronoLeague.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
