package com.ada.poc.service.impl;

import com.ada.poc.service.PronoLeagueService;
import com.ada.poc.domain.PronoLeague;
import com.ada.poc.repository.PronoLeagueRepository;
import com.ada.poc.service.dto.PronoLeagueDTO;
import com.ada.poc.service.mapper.PronoLeagueMapper;
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
 * Service Implementation for managing PronoLeague.
 */
@Service
@Transactional
public class PronoLeagueServiceImpl implements PronoLeagueService{

    private final Logger log = LoggerFactory.getLogger(PronoLeagueServiceImpl.class);
    
    private final PronoLeagueRepository pronoLeagueRepository;

    private final PronoLeagueMapper pronoLeagueMapper;

    public PronoLeagueServiceImpl(PronoLeagueRepository pronoLeagueRepository, PronoLeagueMapper pronoLeagueMapper) {
        this.pronoLeagueRepository = pronoLeagueRepository;
        this.pronoLeagueMapper = pronoLeagueMapper;
    }

    /**
     * Save a pronoLeague.
     *
     * @param pronoLeagueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PronoLeagueDTO save(PronoLeagueDTO pronoLeagueDTO) {
        log.debug("Request to save PronoLeague : {}", pronoLeagueDTO);
        PronoLeague pronoLeague = pronoLeagueMapper.pronoLeagueDTOToPronoLeague(pronoLeagueDTO);
        pronoLeague = pronoLeagueRepository.save(pronoLeague);
        PronoLeagueDTO result = pronoLeagueMapper.pronoLeagueToPronoLeagueDTO(pronoLeague);
        return result;
    }

    /**
     *  Get all the pronoLeagues.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PronoLeagueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PronoLeagues");
        Page<PronoLeague> result = pronoLeagueRepository.findAll(pageable);
        return result.map(pronoLeague -> pronoLeagueMapper.pronoLeagueToPronoLeagueDTO(pronoLeague));
    }

    /**
     *  Get one pronoLeague by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PronoLeagueDTO findOne(Long id) {
        log.debug("Request to get PronoLeague : {}", id);
        PronoLeague pronoLeague = pronoLeagueRepository.findOne(id);
        PronoLeagueDTO pronoLeagueDTO = pronoLeagueMapper.pronoLeagueToPronoLeagueDTO(pronoLeague);
        return pronoLeagueDTO;
    }

    /**
     *  Delete the  pronoLeague by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PronoLeague : {}", id);
        pronoLeagueRepository.delete(id);
    }
}
