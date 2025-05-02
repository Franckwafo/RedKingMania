package org.calma.redkingmania;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String pseudo;
    private int niveau;
    private int nbErable;
    private int nbCristaux;
    private int nbBois;

    private String sex;

    public User(String username, String pseudo, int niveau, int nbErable, int nbCristaux, int nbBois, String sex) {
        this.username = username;
        this.pseudo = pseudo;
        this.niveau = niveau;
        this.nbErable = nbErable;
        this.nbCristaux = nbCristaux;
        this.nbBois = nbBois;
        this.sex = sex;
    }

    // === Getters et Setters ===

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getNbErable() {
        return nbErable;
    }

    public void setNbErable(int nbErable) {
        this.nbErable = nbErable;
    }

    public int getNbCristaux() {
        return nbCristaux;
    }

    public void setNbCristaux(int nbCristaux) {
        this.nbCristaux = nbCristaux;
    }

    public int getNbBois() {
        return nbBois;
    }

    public void setNbBois(int nbBois) {
        this.nbBois = nbBois;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", niveau=" + niveau +
                ", nbErable=" + nbErable +
                ", nbCristaux=" + nbCristaux +
                ", nbBois=" + nbBois +
                '}';
    }
}
