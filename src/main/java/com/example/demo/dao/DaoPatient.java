package com.example.demo.dao;

import com.example.demo.entite.Patient;
import com.example.demo.utils.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoPatient implements IDao<Patient>{
    private Connection connection = DbConnection.seConnecter();

    @Override
    public boolean add(Patient t) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO patient(code,nom, email, tel) VALUES(?,?, ?, ?)");
            preparedStatement.setInt(1, t.getCode());
            preparedStatement.setString(2, t.getNom());
            preparedStatement.setString(3, t.getEmail());
            preparedStatement.setString(4, t.getTel());
            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Patient patient) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE patient SET nom = ?, email = ?, tel = ? WHERE code = ?");
            preparedStatement.setString(1, patient.getNom());
            preparedStatement.setString(2, patient.getEmail());
            preparedStatement.setString(3, patient.getTel());
            preparedStatement.setInt(4, patient.getCode());
            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM patient WHERE code = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Patient findById(int id) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE code = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Patient(resultSet.getInt("code"), resultSet.getString("nom"), resultSet.getString("email"), resultSet.getString("tel"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient");
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                patients.add(new Patient(resultSet.getInt("code"), resultSet.getString("nom"), resultSet.getString("email"), resultSet.getString("tel")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return patients;
    }

    @Override
    public List<Patient> findByName(String nom) {
        List<Patient> patients = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE nom LIKE ? OR code LIKE ?");
            preparedStatement.setString(1,nom);
            try {
                preparedStatement.setInt(2,Integer.parseInt(nom));
            }catch (Exception e){
                preparedStatement.setString(2,"");
            }
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                patients.add(new Patient(resultSet.getInt("code"), resultSet.getString("nom"), resultSet.getString("email"), resultSet.getString("tel")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return patients;
    }

}