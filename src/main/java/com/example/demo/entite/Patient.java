package com.example.demo.entite;


public class Patient {
    private int code;
    private String nom;
    private String email;
    private String tel;

    public Patient(){}
    public Patient(Integer code,String nom ){
        this.code = code;
        this.nom = nom;
    }
    public Patient(int code, String nom, String email, String tel) throws IllegalArgumentException {
        if(nom == null){
            throw new IllegalArgumentException("Le nom ne peut pas être null");
        }
        if(email == null){
            throw new IllegalArgumentException("L'email ne peut pas être null");
        }
        if(tel == null){
            throw new IllegalArgumentException("Le téléphone ne peut pas être null");
        }
        this.code = code;
        this.nom = nom;
        this.email = email;
        this.tel = tel;
    }

    // Getters
    public int getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    // Setters

    public void setCode(int code) {
        this.code = code;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "code='" + code + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

}
