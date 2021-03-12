/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo;

/**
 *
 * @author ASUS
 */
public class Seance {
     private int ID_seance;
    private int ID_formation;
    private String lien;
    private String description;
    private String Date_seance;


    public void setID_seance(int ID_seance) {
        this.ID_seance = ID_seance;
    }

    public void setID_formation(int ID_formation) {
        this.ID_formation = ID_formation;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate_seance(String Date_seance) {
        this.Date_seance = Date_seance;
    }

    public int getID_seance() {
        return ID_seance;
    }

    public int getID_formation() {
        return ID_formation;
    }

    public String getLien() {
        return lien;
    }

    public String getDescription() {
        return description;
    }

    public String getDate_seance() {
        return Date_seance;
    }

    public Seance(int ID_seance, int ID_formation, String lien, String description, String Date_seance) {
        this.ID_seance = ID_seance;
        this.ID_formation = ID_formation;
        this.lien = lien;
        this.description = description;
        this.Date_seance = Date_seance;
    }
    
    
}
