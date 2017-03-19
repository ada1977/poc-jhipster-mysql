package com.ada.poc.service.impl;

import com.ada.poc.service.PronoGameService;
import com.ada.poc.domain.PronoGame;
import com.ada.poc.repository.PronoGameRepository;
import com.ada.poc.service.dto.PronoGameDTO;
import com.ada.poc.service.mapper.PronoGameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PronoGame.
 */
@Service
@Transactional
public class PronoGameServiceImpl implements PronoGameService{

    private final Logger log = LoggerFactory.getLogger(PronoGameServiceImpl.class);
    
    private final PronoGameRepository pronoGameRepository;

    private final PronoGameMapper pronoGameMapper;

    public PronoGameServiceImpl(PronoGameRepository pronoGameRepository, PronoGameMapper pronoGameMapper) {
        this.pronoGameRepository = pronoGameRepository;
        this.pronoGameMapper = pronoGameMapper;
    }

    /**
     * Save a pronoGame.
     *
     * @param pronoGameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PronoGameDTO save(PronoGameDTO pronoGameDTO) {
        log.debug("Request to save PronoGame : {}", pronoGameDTO);
        PronoGame pronoGame = pronoGameMapper.pronoGameDTOToPronoGame(pronoGameDTO);
        pronoGame = pronoGameRepository.save(pronoGame);
        PronoGameDTO result = pronoGameMapper.pronoGameToPronoGameDTO(pronoGame);
        return result;
    }

    /**
     *  Get all the pronoGames.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PronoGameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PronoGames");
        Page<PronoGame> result = pronoGameRepository.findAll(pageable);
        return result.map(pronoGame -> pronoGameMapper.pronoGameToPronoGameDTO(pronoGame));
    }

    /**
     *  Get one pronoGame by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PronoGameDTO findOne(Long id) {
        log.debug("Request to get PronoGame : {}", id);
        PronoGame pronoGame = pronoGameRepository.findOne(id);
        PronoGameDTO pronoGameDTO = pronoGameMapper.pronoGameToPronoGameDTO(pronoGame);
        return pronoGameDTO;
    }

    /**
     *  Delete the  pronoGame by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PronoGame : {}", id);
        pronoGameRepository.delete(id);
    }
}
