package com.example.demo.entite;

import java.util.Date;

public class PatientMedicament {
    private int qte;
    private Date date;
    private Medicament medicament;
    private Patient patient;

    public PatientMedicament() {}

    public PatientMedicament(Medicament medicament, Patient patient,int qte) {
        this.medicament = medicament;
        this.patient = patient;
        this.qte = qte;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Date getDate() {
        return date;
    }

    public void setDate() {
        this.date = new Date();
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "PatientMedicament{" +
                "qte=" + qte +
                ", date=" + date +
                ", medicament=" + medicament +
                ", patient=" + patient +
                '}';
    }
}
