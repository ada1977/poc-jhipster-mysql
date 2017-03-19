package com.ada.poc.service.impl;

import com.ada.poc.service.PronoBetService;
import com.ada.poc.domain.PronoBet;
import com.ada.poc.repository.PronoBetRepository;
import com.ada.poc.service.dto.PronoBetDTO;
import com.ada.poc.service.mapper.PronoBetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PronoBet.
 */
@Service
@Transactional
public class PronoBetServiceImpl implements PronoBetService{

    private final Logger log = LoggerFactory.getLogger(PronoBetServiceImpl.class);
    
    private final PronoBetRepository pronoBetRepository;

    private final PronoBetMapper pronoBetMapper;

    public PronoBetServiceImpl(PronoBetRepository pronoBetRepository, PronoBetMapper pronoBetMapper) {
        this.pronoBetRepository = pronoBetRepository;
        this.pronoBetMapper = pronoBetMapper;
    }

    /**
     * Save a pronoBet.
     *
     * @param pronoBetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PronoBetDTO save(PronoBetDTO pronoBetDTO) {
        log.debug("Request to save PronoBet : {}", pronoBetDTO);
        PronoBet pronoBet = pronoBetMapper.pronoBetDTOToPronoBet(pronoBetDTO);
        pronoBet = pronoBetRepository.save(pronoBet);
        PronoBetDTO result = pronoBetMapper.pronoBetToPronoBetDTO(pronoBet);
        return result;
    }

    /**
     *  Get all the pronoBets.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PronoBetDTO> findAll() {
        log.debug("Request to get all PronoBets");
        List<PronoBetDTO> result = pronoBetRepository.findAll().stream()
            .map(pronoBetMapper::pronoBetToPronoBetDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one pronoBet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PronoBetDTO findOne(Long id) {
        log.debug("Request to get PronoBet : {}", id);
        PronoBet pronoBet = pronoBetRepository.findOne(id);
        PronoBetDTO pronoBetDTO = pronoBetMapper.pronoBetToPronoBetDTO(pronoBet);
        return pronoBetDTO;
    }

    /**
     *  Delete the  pronoBet by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PronoBet : {}", id);
        pronoBetRepository.delete(id);
    }
}
