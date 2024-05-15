package com.example.demo.dao;

import com.example.demo.entite.Utilisateur;
import com.example.demo.utils.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoUtilisateur implements IDao<Utilisateur> {
    private Connection connection = DbConnection.seConnecter();

    @Override
    public boolean add(Utilisateur utilisateur) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO utilisateur (nom, email, password, telephone, role) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3,utilisateur.getMotDePasse());
            preparedStatement.setInt(4,utilisateur.getTelephone());
            preparedStatement.setString(5,utilisateur.getRole());
            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean Authentifier(Utilisateur utilisateur){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM utilisateur WHERE email LIKE ? AND password = ?");
            preparedStatement.setString(1,utilisateur.getEmail());
            preparedStatement.setString(2,utilisateur.getMotDePasse());
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if(resultSet.next()) {
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Utilisateur utilisateur) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE utilisateur SET nom = ?, email = ? , password = ?, telephone = ? , role = ?  WHERE id = ?)");
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3,utilisateur.getMotDePasse());
            preparedStatement.setInt(4,utilisateur.getTelephone());
            preparedStatement.setString(5,utilisateur.getRole());
            preparedStatement.setInt(6,utilisateur.getId());
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM utilisateur WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Utilisateur findById(int id) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM utilisateur WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if(resultSet.next()){
                return new Utilisateur(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("telephone"),
                        resultSet.getString("role"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM utilisateur");
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()){
                utilisateurs.add(new Utilisateur(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("telephone"),
                        resultSet.getString("role"))
                );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> findByName(String nom) {
        return List.of();
    }
}
