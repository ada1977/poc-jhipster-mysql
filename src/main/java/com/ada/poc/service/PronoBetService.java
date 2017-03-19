package com.ada.poc.service;

import com.ada.poc.service.dto.PronoBetDTO;
import java.util.List;

/**
 * Service Interface for managing PronoBet.
 */
public interface PronoBetService {

    /**
     * Save a pronoBet.
     *
     * @param pronoBetDTO the entity to save
     * @return the persisted entity
     */
    PronoBetDTO save(PronoBetDTO pronoBetDTO);

    /**
     *  Get all the pronoBets.
     *  
     *  @return the list of entities
     */
    List<PronoBetDTO> findAll();

    /**
     *  Get the "id" pronoBet.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PronoBetDTO findOne(Long id);

    /**
     *  Delete the "id" pronoBet.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
