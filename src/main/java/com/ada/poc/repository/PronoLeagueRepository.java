package com.ada.poc.repository;

import com.ada.poc.domain.PronoLeague;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PronoLeague entity.
 */
@SuppressWarnings("unused")
public interface PronoLeagueRepository extends JpaRepository<PronoLeague,Long> {

}
