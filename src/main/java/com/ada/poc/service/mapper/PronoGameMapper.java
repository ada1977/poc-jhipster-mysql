package com.ada.poc.service.mapper;

import com.ada.poc.domain.*;
import com.ada.poc.service.dto.PronoGameDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PronoGame and its DTO PronoGameDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PronoGameMapper {

    @Mapping(source = "pronoLeague.id", target = "pronoLeagueId")
    PronoGameDTO pronoGameToPronoGameDTO(PronoGame pronoGame);

    List<PronoGameDTO> pronoGamesToPronoGameDTOs(List<PronoGame> pronoGames);

    @Mapping(source = "pronoLeagueId", target = "pronoLeague")
    @Mapping(target = "bets", ignore = true)
    PronoGame pronoGameDTOToPronoGame(PronoGameDTO pronoGameDTO);

    List<PronoGame> pronoGameDTOsToPronoGames(List<PronoGameDTO> pronoGameDTOs);

    default PronoLeague pronoLeagueFromId(Long id) {
        if (id == null) {
            return null;
        }
        PronoLeague pronoLeague = new PronoLeague();
        pronoLeague.setId(id);
        return pronoLeague;
    }
}
