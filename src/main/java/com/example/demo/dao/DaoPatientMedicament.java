package com.example.demo.dao;

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
        date DATE,
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
        try {
            String sql = "INSERT INTO patient_medicament (patient_id, medicament_id, qte, date) VALUES (?, ?, ?, ?)";
            PreparedStatement st = cn.prepareStatement(sql);
            if(daoMedicament.findById(patientMedicament.getMedicament().getCodeMed()) != null && daoPatient.findById(patientMedicament.getPatient().getCode()) != null){
                st.setInt(1, patientMedicament.getPatient().getCode());
                st.setInt(2, patientMedicament.getMedicament().getCodeMed());
                st.setInt(3, patientMedicament.getQte());
                st.setDate(4, new Date(patientMedicament.getDate().getTime()));
                return st.executeUpdate() == 1;
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
}
