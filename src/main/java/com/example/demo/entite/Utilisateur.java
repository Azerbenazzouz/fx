package com.example.demo.entite;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;
    private int telephone;
    private String role;

    public Utilisateur() {
    }

    public Utilisateur(int id, String nom, String email, String motDePasse, int telephone, String role) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = hashMotDePasse(motDePasse);
        this.telephone = telephone;
        this.role = role;
    }

    public Utilisateur(String nom, String email, String motDePasse, int telephone, String role) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = hashMotDePasse(motDePasse);
        this.telephone = telephone;
        this.role = role;
    }

    public Utilisateur(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = hashMotDePasse(motDePasse);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = hashMotDePasse(motDePasse);
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id+
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", telephone=" + telephone +
                '}';
    }

    public String hashMotDePasse(String motDePasse){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(motDePasse.getBytes());
            byte[] digest = md.digest();

            StringBuilder sb = new StringBuilder();

            for(byte b : digest){
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
        }
        return "";
    }
}
