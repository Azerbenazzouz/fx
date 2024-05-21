package com.example.demo.entite;

public class Medicament implements Comparable<Medicament> {
    private int codeMed;
    private String nomMed;
    private float prixMed;
    private int qte;
    private String typeMed;

    public Medicament() {
    }

    public Medicament(String nomMed , Integer qte , Float prixMed) {
        this(nomMed,qte);
        this.prixMed = prixMed;
    }

    public Medicament(String nomMed , Integer qte , Float prixMed,int codeMed) {
        this(nomMed,qte);
        this.prixMed = prixMed;
        this.codeMed = codeMed;
    }

    public Medicament(String nomMed , Integer qte) {
        this.nomMed = nomMed;
        // this.prixMed = prixMed;
        this.qte = qte;
    }
    public Medicament(int codeMed, String nomMed, Float prixMed, Integer qte, String typeMed) {
        this.codeMed = codeMed;
        this.nomMed = nomMed;
        this.prixMed = prixMed;
        this.qte = qte;
        this.typeMed = typeMed;
    }

    // Getters
    public int getCodeMed() {
        return codeMed;
    }

    public String getNomMed() {
        return nomMed;
    }

    public float getPrixMed() {
        return prixMed;
    }

    public int getQte() {
        return qte;
    }

    public String getTypeMed() {
        return typeMed;
    }

    // Setters
    public void setCodeMed(int codeMed) {
        this.codeMed = codeMed;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public void setPrixMed(float prixMed) {
        this.prixMed = prixMed;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public void setTypeMed(String typeMed) {
        this.typeMed = typeMed;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "codeMed=" + codeMed +
                ", nomMed=" + nomMed +
                ", prixMed=" + prixMed +
                ", qte=" + qte +
                ", typeMed=" + typeMed +
                '}';
    }

    @Override
    public int compareTo(Medicament o) {
        if (this.codeMed == o.codeMed) {
            return 0;
        } else if (this.codeMed > o.codeMed) {
            return 1;
        } else {
            return -1;
        }
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicament)) return false;
        Medicament medicament = (Medicament) o;
        return getCodeMed() == medicament.getCodeMed();
    }

    // hashCode method
    @Override
    public int hashCode() {
        return getCodeMed();
    }

}
