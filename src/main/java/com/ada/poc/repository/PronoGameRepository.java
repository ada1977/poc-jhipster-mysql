package com.ada.poc.repository;

import com.ada.poc.domain.PronoGame;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PronoGame entity.
 */
@SuppressWarnings("unused")
public interface PronoGameRepository extends JpaRepository<PronoGame,Long> {

}
