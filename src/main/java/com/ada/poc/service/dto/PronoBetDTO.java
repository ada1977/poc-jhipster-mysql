package com.ada.poc.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PronoBet entity.
 */
public class PronoBetDTO implements Serializable {

    private Long id;

    private String user;

    private Integer score1;

    private Integer score2;

    private Integer result;

    private Long pronoGameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Long getPronoGameId() {
        return pronoGameId;
    }

    public void setPronoGameId(Long pronoGameId) {
        this.pronoGameId = pronoGameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PronoBetDTO pronoBetDTO = (PronoBetDTO) o;

        if ( ! Objects.equals(id, pronoBetDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PronoBetDTO{" +
            "id=" + id +
            ", user='" + user + "'" +
            ", score1='" + score1 + "'" +
            ", score2='" + score2 + "'" +
            ", result='" + result + "'" +
            '}';
    }
}
