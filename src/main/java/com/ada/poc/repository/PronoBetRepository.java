package com.ada.poc.repository;

import com.ada.poc.domain.PronoBet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PronoBet entity.
 */
@SuppressWarnings("unused")
public interface PronoBetRepository extends JpaRepository<PronoBet,Long> {

}
