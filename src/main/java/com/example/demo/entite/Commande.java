package com.example.demo.entite;

import java.util.Date;
import java.util.List;

public class Commande {
    private Date date;
    private Patient patient;
    private List<Medicament> medicaments;
    private float prixTotal;
    private String nomPatient;

    public Commande() {}

    public Commande(Patient patient, List<Medicament> medicaments) {
        this.patient = patient;
        this.medicaments = medicaments;
        this.date = new Date();
        this.nomPatient = patient.getNom();
    }

    public Commande(Date date, Patient patient, List<Medicament> medicaments) {
        this.date = date;
        this.patient = patient;
        this.medicaments = medicaments;
        this.nomPatient = patient.getNom();
    }

    // Getters
    public Date getDate() {
        return date;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<Medicament> getMedicaments() {
        return medicaments;
    }

    public float getPrixTotal() {
        return prixTotal;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    // Setters
    public void setDate(Date date) {
        this.date = date;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        this.nomPatient = patient.getNom();
    }

    public void setMedicaments(List<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    public void setPrixTotal(float prixTotal) {
        this.prixTotal = prixTotal;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    @Override

    public String toString() {
        return "Commande{" +
                "date=" + date +
                ", patient=" + patient +
                ", medicaments=" + medicaments +
                '}';
    }

    public void addMedicament(Medicament medicament) {
        this.medicaments.add(medicament);
    }

    public void removeMedicament(Medicament medicament) {
        this.medicaments.remove(medicament);
    }

    public void removeMedicament(int index) {
        this.medicaments.remove(index);
    }

    public void clearMedicaments() {
        this.medicaments.clear();
    }

    public boolean containsMedicament(Medicament medicament) {
        return this.medicaments.contains(medicament);
    }

    public boolean containsMedicament(int index) {
        return index >= 0 && index < this.medicaments.size();
    }

    public Medicament getMedicament(int index) {
        return this.medicaments.get(index);
    }

    public int size() {
        return this.medicaments.size();
    }

    public boolean isEmpty() {
        return this.medicaments.isEmpty();
    }

}
