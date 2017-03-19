package com.ada.poc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PronoGame.
 */
@Entity
@Table(name = "prono_game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PronoGame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_number")
    private String gameNumber;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "team_1")
    private String team1;

    @Column(name = "team_2")
    private String team2;

    @Column(name = "score_1")
    private Integer score1;

    @Column(name = "score_2")
    private Integer score2;

    @ManyToOne
    private PronoLeague pronoLeague;

    @OneToMany(mappedBy = "pronoGame")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PronoBet> bets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameNumber() {
        return gameNumber;
    }

    public PronoGame gameNumber(String gameNumber) {
        this.gameNumber = gameNumber;
        return this;
    }

    public void setGameNumber(String gameNumber) {
        this.gameNumber = gameNumber;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public PronoGame date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getTeam1() {
        return team1;
    }

    public PronoGame team1(String team1) {
        this.team1 = team1;
        return this;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public PronoGame team2(String team2) {
        this.team2 = team2;
        return this;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Integer getScore1() {
        return score1;
    }

    public PronoGame score1(Integer score1) {
        this.score1 = score1;
        return this;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public PronoGame score2(Integer score2) {
        this.score2 = score2;
        return this;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public PronoLeague getPronoLeague() {
        return pronoLeague;
    }

    public PronoGame pronoLeague(PronoLeague pronoLeague) {
        this.pronoLeague = pronoLeague;
        return this;
    }

    public void setPronoLeague(PronoLeague pronoLeague) {
        this.pronoLeague = pronoLeague;
    }

    public Set<PronoBet> getBets() {
        return bets;
    }

    public PronoGame bets(Set<PronoBet> pronoBets) {
        this.bets = pronoBets;
        return this;
    }

    public PronoGame addBet(PronoBet pronoBet) {
        this.bets.add(pronoBet);
        pronoBet.setPronoGame(this);
        return this;
    }

    public PronoGame removeBet(PronoBet pronoBet) {
        this.bets.remove(pronoBet);
        pronoBet.setPronoGame(null);
        return this;
    }

    public void setBets(Set<PronoBet> pronoBets) {
        this.bets = pronoBets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PronoGame pronoGame = (PronoGame) o;
        if (pronoGame.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pronoGame.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PronoGame{" +
            "id=" + id +
            ", gameNumber='" + gameNumber + "'" +
            ", date='" + date + "'" +
            ", team1='" + team1 + "'" +
            ", team2='" + team2 + "'" +
            ", score1='" + score1 + "'" +
            ", score2='" + score2 + "'" +
            '}';
    }
}
