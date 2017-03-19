package com.ada.poc.service.mapper;

import com.ada.poc.domain.*;
import com.ada.poc.service.dto.PronoBetDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PronoBet and its DTO PronoBetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PronoBetMapper {

    @Mapping(source = "pronoGame.id", target = "pronoGameId")
    PronoBetDTO pronoBetToPronoBetDTO(PronoBet pronoBet);

    List<PronoBetDTO> pronoBetsToPronoBetDTOs(List<PronoBet> pronoBets);

    @Mapping(source = "pronoGameId", target = "pronoGame")
    PronoBet pronoBetDTOToPronoBet(PronoBetDTO pronoBetDTO);

    List<PronoBet> pronoBetDTOsToPronoBets(List<PronoBetDTO> pronoBetDTOs);

    default PronoGame pronoGameFromId(Long id) {
        if (id == null) {
            return null;
        }
        PronoGame pronoGame = new PronoGame();
        pronoGame.setId(id);
        return pronoGame;
    }
}
