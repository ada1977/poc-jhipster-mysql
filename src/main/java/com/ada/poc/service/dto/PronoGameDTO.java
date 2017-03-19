package com.ada.poc.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PronoGame entity.
 */
public class PronoGameDTO implements Serializable {

    private Long id;

    private String gameNumber;

    private ZonedDateTime date;

    private String team1;

    private String team2;

    private Integer score1;

    private Integer score2;

    private Long pronoLeagueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(String gameNumber) {
        this.gameNumber = gameNumber;
    }
    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }
    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }
    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }
    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Long getPronoLeagueId() {
        return pronoLeagueId;
    }

    public void setPronoLeagueId(Long pronoLeagueId) {
        this.pronoLeagueId = pronoLeagueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PronoGameDTO pronoGameDTO = (PronoGameDTO) o;

        if ( ! Objects.equals(id, pronoGameDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PronoGameDTO{" +
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
