package com.ada.poc.service.mapper;

import com.ada.poc.domain.*;
import com.ada.poc.service.dto.PronoLeagueDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PronoLeague and its DTO PronoLeagueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PronoLeagueMapper {

    PronoLeagueDTO pronoLeagueToPronoLeagueDTO(PronoLeague pronoLeague);

    List<PronoLeagueDTO> pronoLeaguesToPronoLeagueDTOs(List<PronoLeague> pronoLeagues);

    @Mapping(target = "games", ignore = true)
    PronoLeague pronoLeagueDTOToPronoLeague(PronoLeagueDTO pronoLeagueDTO);

    List<PronoLeague> pronoLeagueDTOsToPronoLeagues(List<PronoLeagueDTO> pronoLeagueDTOs);
}
