package com.example.demo.dao;

import com.example.demo.entite.Medicament;
import com.example.demo.utils.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class DaoMedicament implements IDao<Medicament> {
    private Connection connection = DbConnection.seConnecter();

    @Override
    public boolean add(Medicament medicament) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO medicament(codeMed,nomMed, prixMed, qte, typeMed) VALUES(?,?, ?, ?, ?)");
            preparedStatement.setInt(1, medicament.getCodeMed());
            preparedStatement.setString(2, medicament.getNomMed());
            preparedStatement.setFloat(3, medicament.getPrixMed());
            preparedStatement.setInt(4, medicament.getQte());
            preparedStatement.setString(5, medicament.getTypeMed());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Medicament t) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE medicament SET nomMed = ?, prixMed = ?, qte = ?, typeMed = ? WHERE codeMed = ?");
            preparedStatement.setString(1, t.getNomMed());
            preparedStatement.setFloat(2, t.getPrixMed());
            preparedStatement.setInt(3, t.getQte());
            preparedStatement.setString(4, t.getTypeMed());
            preparedStatement.setInt(5, t.getCodeMed());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM medicament WHERE codeMed = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Medicament findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM medicament WHERE codeMed = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return new Medicament(resultSet.getInt("codeMed"), resultSet.getString("nomMed"), resultSet.getFloat("prixMed"), resultSet.getInt("qte"), resultSet.getString("typeMed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medicament> findAll() {
        List<Medicament> medicaments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM medicament");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                medicaments.add(new Medicament(resultSet.getInt("codeMed"), resultSet.getString("nomMed"), resultSet.getFloat("prixMed"), resultSet.getInt("qte"), resultSet.getString("typeMed")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }

    @Override
    public List<Medicament> findByName(String nom) {
        List<Medicament> medicaments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM medicament WHERE nomMed LIKE ? OR codeMed LIKE ?");
            preparedStatement.setString(1,"%" + nom + "%");
            try {
                preparedStatement.setInt(2,Integer.parseInt(nom));
            }catch (Exception e){
                preparedStatement.setString(2,"");
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                medicaments.add(new Medicament(resultSet.getInt("codeMed"), resultSet.getString("nomMed"), resultSet.getFloat("prixMed"), resultSet.getInt("qte"), resultSet.getString("typeMed")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicaments;
    }

    public boolean updateQte(int codeMed, int qte) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE medicament SET qte = ? WHERE codeMed = ?");
            preparedStatement.setInt(1, qte);
            preparedStatement.setInt(2, codeMed);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean minusQte(int codeMed, int qte) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT qte FROM medicament WHERE codeMed = ?");
            preparedStatement.setInt(1, codeMed);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int qteActuel = resultSet.getInt("qte");
                if(qteActuel >= qte){
                    return updateQte(codeMed, qteActuel - qte);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
