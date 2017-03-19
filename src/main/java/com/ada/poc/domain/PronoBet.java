package com.ada.poc.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PronoBet.
 */
@Entity
@Table(name = "prono_bet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PronoBet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String user;

    @Column(name = "score_1")
    private Integer score1;

    @Column(name = "score_2")
    private Integer score2;

    @Column(name = "result")
    private Integer result;

    @ManyToOne
    private PronoGame pronoGame;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public PronoBet user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getScore1() {
        return score1;
    }

    public PronoBet score1(Integer score1) {
        this.score1 = score1;
        return this;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public PronoBet score2(Integer score2) {
        this.score2 = score2;
        return this;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Integer getResult() {
        return result;
    }

    public PronoBet result(Integer result) {
        this.result = result;
        return this;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public PronoGame getPronoGame() {
        return pronoGame;
    }

    public PronoBet pronoGame(PronoGame pronoGame) {
        this.pronoGame = pronoGame;
        return this;
    }

    public void setPronoGame(PronoGame pronoGame) {
        this.pronoGame = pronoGame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PronoBet pronoBet = (PronoBet) o;
        if (pronoBet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pronoBet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PronoBet{" +
            "id=" + id +
            ", user='" + user + "'" +
            ", score1='" + score1 + "'" +
            ", score2='" + score2 + "'" +
            ", result='" + result + "'" +
            '}';
    }
}
