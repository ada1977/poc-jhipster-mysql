package com.ada.poc.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PronoLeague entity.
 */
public class PronoLeagueDTO implements Serializable {

    private Long id;

    private String leagueName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PronoLeagueDTO pronoLeagueDTO = (PronoLeagueDTO) o;

        if ( ! Objects.equals(id, pronoLeagueDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PronoLeagueDTO{" +
            "id=" + id +
            ", leagueName='" + leagueName + "'" +
            '}';
    }
}
