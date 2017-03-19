package com.ada.poc.service;

import com.ada.poc.service.dto.PronoGameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PronoGame.
 */
public interface PronoGameService {

    /**
     * Save a pronoGame.
     *
     * @param pronoGameDTO the entity to save
     * @return the persisted entity
     */
    PronoGameDTO save(PronoGameDTO pronoGameDTO);

    /**
     *  Get all the pronoGames.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PronoGameDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pronoGame.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PronoGameDTO findOne(Long id);

    /**
     *  Delete the "id" pronoGame.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
