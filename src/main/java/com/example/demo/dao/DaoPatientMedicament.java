package com.example.demo.dao;

import com.example.demo.entite.Commande;
import com.example.demo.entite.Medicament;
import com.example.demo.entite.PatientMedicament;
import com.example.demo.utils.DbConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    SQL Query to create the patient_medicament table:
    CREATE TABLE patient_medicament (
        patient_id INT,  
        medicament_id INT,
        qte INT,
        date DATETIME ,
        FOREIGN KEY (patient_id) REFERENCES patient(code),
        FOREIGN KEY (medicament_id) REFERENCES medicament(codeMed),
        PRIMARY KEY (patient_id, medicament_id, date)
    );
 
 */
public class DaoPatientMedicament implements IDao<PatientMedicament> {
    private final Connection cn = DbConnection.seConnecter();

    @Override
    public boolean add(PatientMedicament patientMedicament) {
        DaoMedicament daoMedicament = new DaoMedicament();
        DaoPatient daoPatient = new DaoPatient();
        System.out.println(patientMedicament);
        try {
            String sql = "INSERT INTO patient_medicament (patient_id, medicament_id, qte, date) VALUES (?, ?, ?, ?)";
            PreparedStatement st = cn.prepareStatement(sql);
            if(daoMedicament.findById(patientMedicament.getMedicament().getCodeMed()) != null && daoPatient.findById(patientMedicament.getPatient().getCode()) != null){
                st.setInt(1, patientMedicament.getPatient().getCode());
                st.setInt(2, patientMedicament.getMedicament().getCodeMed());
                st.setInt(3, patientMedicament.getQte());
                Date date = new Date(patientMedicament.getDate().getTime());
                String dateStr = date.toString() + " " + patientMedicament.getDate().getHours() + ":" + patientMedicament.getDate().getMinutes() + ":" + patientMedicament.getDate().getSeconds();
                st.setString(4, dateStr);
                return st.executeUpdate() == 1 && daoMedicament.minusQte(patientMedicament.getMedicament().getCodeMed(), patientMedicament.getQte());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(PatientMedicament patientMedicament) {
        DaoMedicament daoMedicament = new DaoMedicament();
        DaoPatient daoPatient = new DaoPatient();
        try {
            String sql = "UPDATE patient_medicament SET qte = ? WHERE patient_id = ? AND medicament_id = ? AND date = ?";
            PreparedStatement st = cn.prepareStatement(sql);
            if(daoMedicament.findById(patientMedicament.getMedicament().getCodeMed()) != null && daoPatient.findById(patientMedicament.getPatient().getCode()) != null){
                st.setInt(1, patientMedicament.getQte());
                st.setInt(2, patientMedicament.getPatient().getCode());
                st.setInt(3, patientMedicament.getMedicament().getCodeMed());
                st.setDate(4, new Date(patientMedicament.getDate().getTime()));
                return st.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public boolean delete(int patient_id, int medicament_id, Date date) {
        try {
            String sql = "DELETE FROM patient_medicament WHERE patient_id = ? AND medicament_id = ? AND date = ?";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, patient_id);
            st.setInt(2, medicament_id);
            st.setDate(3, date);
            return st.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public PatientMedicament findById(int id) {
        return null;
    }
    
    public PatientMedicament findById(int patient_id, int medicament_id, Date date) {
        try {
            String sql = "SELECT * FROM patient_medicament WHERE patient_id = ? AND medicament_id = ? AND date = ?";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, patient_id);
            st.setInt(2, medicament_id);
            st.setDate(3, date);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                DaoMedicament daoMedicament = new DaoMedicament();
                DaoPatient daoPatient = new DaoPatient();
                PatientMedicament patientMedicament = new PatientMedicament();
                patientMedicament.setPatient(daoPatient.findById(rs.getInt("patient_id")));
                patientMedicament.setMedicament(daoMedicament.findById(rs.getInt("medicament_id")));
                patientMedicament.setQte(rs.getInt("qte"));
                patientMedicament.setDate(rs.getDate("date"));
                return patientMedicament;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<PatientMedicament> findAll() {
        DaoMedicament daoMedicament = new DaoMedicament();
        DaoPatient daoPatient = new DaoPatient();
        List<PatientMedicament> list = new ArrayList<>();
        PatientMedicament patientMedicament;
        
        try {
            String sql = "SELECT * FROM patient_medicament";
            PreparedStatement st = cn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                patientMedicament = new PatientMedicament();
                patientMedicament.setPatient(daoPatient.findById(rs.getInt("patient_id")));
                patientMedicament.setMedicament(daoMedicament.findById(rs.getInt("medicament_id")));
                patientMedicament.setQte(rs.getInt("qte"));
                patientMedicament.setDate(rs.getDate("date"));
                list.add(patientMedicament);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<PatientMedicament> findByName(String nom) {
        return null;
    }

    public List<PatientMedicament> findByPatient(int patient_id) {
        DaoMedicament daoMedicament = new DaoMedicament();
        DaoPatient daoPatient = new DaoPatient();
        List<PatientMedicament> list = new ArrayList<>();
        PatientMedicament patientMedicament;
        
        try {
            String sql = "SELECT * FROM patient_medicament WHERE patient_id = ? ORDER BY date DESC";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, patient_id);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                patientMedicament = new PatientMedicament();
                patientMedicament.setPatient(daoPatient.findById(rs.getInt("patient_id")));
                patientMedicament.setMedicament(daoMedicament.findById(rs.getInt("medicament_id")));
                patientMedicament.setQte(rs.getInt("qte"));
                patientMedicament.setDate(rs.getDate("date"));
                list.add(patientMedicament);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Commande> findAllCommande(){
        /*
            SELECT * 
            FROM `patient_medicament` 
            GROUP BY `patient_id` , `date`
            ORDER BY `date` DESC;
        */
        /*
            SELECT *
            FROM `patient_medicament`
            WHERE `patient_id` = 1
            AND `date` LIKE '2024-05-17 19:44:50';
        */
        DaoPatient daoPatient = new DaoPatient();
        DaoMedicament daoMedicament = new DaoMedicament();
        String req1 = "SELECT * FROM `patient_medicament` GROUP BY `patient_id` , `date` ORDER BY `date` DESC";
        String req2 = "SELECT * FROM `patient_medicament` WHERE `patient_id` = ? AND `date` LIKE ?";
        Commande commande;
        try {
            PreparedStatement st = cn.prepareStatement(req1);
            ResultSet rs = st.executeQuery();
            List<Commande> listCommandes = new ArrayList<>();
            while(rs.next()){
                commande = new Commande();
                commande.setPatient(daoPatient.findById(rs.getInt("patient_id")));
                commande.setDate(rs.getDate("date"));
                List<Medicament> list = new ArrayList<>();
                PreparedStatement st2 = cn.prepareStatement(req2);
                st2.setInt(1, rs.getInt("patient_id"));
                st2.setString(2, rs.getString("date"));
                ResultSet rs2 = st2.executeQuery();
                float prixTotal = 0;
                while(rs2.next()){
                    Medicament medicament;
                    medicament = daoMedicament.findById(rs2.getInt("medicament_id"));
                    medicament.setQte(rs2.getInt("qte"));
                    medicament.setPrixMed(medicament.getPrixMed() * medicament.getQte());
                    list.add(medicament);
                    prixTotal += medicament.getPrixMed();
                }
                commande.setMedicaments(list);
                commande.setPrixTotal(prixTotal);
                listCommandes.add(commande);
            }
            return listCommandes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Commande> findAllCommandeByName(String nom){
        /*
            SELECT *
            FROM `patient_medicament`
            GROUP BY `patient_id` , `date`
            ORDER BY `date` DESC;
        */
        /*
            SELECT *
            FROM `patient_medicament`
            WHERE `patient_id` = 1
            AND `date` LIKE '2024-05-17 19:44:50';
        */
        DaoPatient daoPatient = new DaoPatient();
        DaoMedicament daoMedicament = new DaoMedicament();
        String req1 = "SELECT * FROM `patient_medicament` WHERE patient_id in (SELECT `code` FROM `patient` WHERE `nom` LIKE ? OR `patient_id` LIKE ? ) GROUP BY `patient_id` , `date` ORDER BY`date` DESC;";
        String req2 = "SELECT * FROM `patient_medicament` WHERE `patient_id` = ? AND `date` LIKE ?";
        Commande commande;
        try {
            PreparedStatement st = cn.prepareStatement(req1);
            st.setString(1,"%" + nom + "%");
            try {
                st.setInt(2,Integer.parseInt(nom));
            }catch (Exception e){
                st.setString(2,"");
            }
            ResultSet rs = st.executeQuery();
            List<Commande> listCommandes = new ArrayList<>();
            while(rs.next()){
                commande = new Commande();
                commande.setPatient(daoPatient.findById(rs.getInt("patient_id")));
                commande.setDate(rs.getDate("date"));
                List<Medicament> list = new ArrayList<>();
                PreparedStatement st2 = cn.prepareStatement(req2);
                st2.setInt(1, rs.getInt("patient_id"));
                st2.setString(2, rs.getString("date"));
                ResultSet rs2 = st2.executeQuery();
                float prixTotal = 0;
                while(rs2.next()){
                    Medicament medicament;
                    medicament = daoMedicament.findById(rs2.getInt("medicament_id"));
                    medicament.setQte(rs2.getInt("qte"));
                    medicament.setPrixMed(medicament.getPrixMed() * medicament.getQte());
                    list.add(medicament);
                    prixTotal += medicament.getPrixMed();
                }
                commande.setMedicaments(list);
                commande.setPrixTotal(prixTotal);
                listCommandes.add(commande);
            }
            return listCommandes;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
