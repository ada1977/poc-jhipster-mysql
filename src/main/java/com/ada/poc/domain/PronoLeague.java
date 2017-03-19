package com.ada.poc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PronoLeague.
 */
@Entity
@Table(name = "prono_league")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PronoLeague implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "league_name")
    private String leagueName;

    @OneToMany(mappedBy = "pronoLeague")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PronoGame> games = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public PronoLeague leagueName(String leagueName) {
        this.leagueName = leagueName;
        return this;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public Set<PronoGame> getGames() {
        return games;
    }

    public PronoLeague games(Set<PronoGame> pronoGames) {
        this.games = pronoGames;
        return this;
    }

    public PronoLeague addGame(PronoGame pronoGame) {
        this.games.add(pronoGame);
        pronoGame.setPronoLeague(this);
        return this;
    }

    public PronoLeague removeGame(PronoGame pronoGame) {
        this.games.remove(pronoGame);
        pronoGame.setPronoLeague(null);
        return this;
    }

    public void setGames(Set<PronoGame> pronoGames) {
        this.games = pronoGames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PronoLeague pronoLeague = (PronoLeague) o;
        if (pronoLeague.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pronoLeague.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PronoLeague{" +
            "id=" + id +
            ", leagueName='" + leagueName + "'" +
            '}';
    }
}
