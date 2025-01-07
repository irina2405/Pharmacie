package com.projet.pharmacie.util;

import java.util.Vector;

public class Stat {
    private Vector suivantX;
    private Vector suivantY;

    // Constructeurs
    public Stat() {
        this.suivantX = new Vector<>();
        this.suivantY = new Vector<>();
    }

    public Stat(Vector suivantX, Vector suivantY) {
        this.suivantX = suivantX;
        this.suivantY = suivantY;
    }

    // Getters et Setters
    public Vector getSuivantX() {
        return suivantX;
    }

    public void setSuivantX(Vector suivantX) {
        this.suivantX = suivantX;
    }

    public Vector getSuivantY() {
        return suivantY;
    }

    public void setSuivantY(Vector suivantY) {
        this.suivantY = suivantY;
    }
}
