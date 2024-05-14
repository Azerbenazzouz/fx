package com.example.demo;

import com.example.demo.dao.DaoMedicament;
import com.example.demo.entite.Medicament;
import com.example.demo.utils.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MedicamentController {
    private Connection cn = DbConnection.seConnecter();
    private DaoMedicament daoMedicament = new DaoMedicament();

    @FXML
    TableView<Medicament> tv = new TableView<Medicament>();

    @FXML
    TextField Id;
    @FXML
    TextField Nom;
    @FXML
    TextField Prix;
    @FXML
    TextField Stock;
    @FXML
    TextField Type;


    @FXML
    TableColumn<Medicament, Integer> colId;
    @FXML
    TableColumn<Medicament, String> colNom;
    @FXML
    TableColumn<Medicament, Float> colPrix;
    @FXML
    TableColumn<Medicament, Integer> colStock;
    @FXML
    TableColumn<Medicament, String> colType;

    ObservableList<Medicament> observableList;

    Medicament medicament = new Medicament();

    @FXML
    private void Ajouter() {
        medicament.setCodeMed(Integer.parseInt(Id.getText()));
        medicament.setNomMed(Nom.getText());
        medicament.setPrixMed(Float.parseFloat(Prix.getText()));
        medicament.setQte(Integer.parseInt(Stock.getText()));
        medicament.setTypeMed(Type.getText());

        if(daoMedicament.add(medicament)){
            System.out.println("Ajout avec succès");
        }else{
            System.out.println("Echec d'ajout");
        }
        lister();
        remiseAzéro();
    }

    @FXML
    private void Modifier() {
        medicament.setCodeMed(Integer.parseInt(Id.getText()));
        medicament.setNomMed(Nom.getText());
        medicament.setPrixMed(Float.parseFloat(Prix.getText()));
        medicament.setQte(Integer.parseInt(Stock.getText()));
        medicament.setTypeMed(Type.getText());
        if(daoMedicament.update(medicament)){
            System.out.println("Modification avec succès");
        }else{
            System.out.println("Echec de modification");
        }
        lister();
        remiseAzéro();
    }

    @FXML
    private void Archiver() {
        if(daoMedicament.delete(Integer.parseInt(Id.getText()))){
            System.out.println("Archivage avec succès");
        }else{
            System.out.println("Echec d'archivage");
        }
        lister();
        remiseAzéro();
    }
    @FXML
    private void Clear(){
        lister();
        remiseAzéro();
    }

    private void lister() {
        tv.getItems().clear();
        observableList = FXCollections.observableArrayList();
        observableList.add(
                new Medicament(
                        1,
                        "eee",
                        44.5F,
                        5,
                        "ezouz"
                )
        );
        try {
            ResultSet rs = cn.createStatement().executeQuery("select * from medicament");
            while (rs.next()) {
                observableList.add(
                        new Medicament(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getFloat(3),
                                rs.getInt(4),
                                rs.getString(5)
                        )
                );
                System.out.println(rs.getInt(1));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        tv.setItems(observableList);

        colId.setCellValueFactory(new PropertyValueFactory<Medicament,Integer>("codeMed"));
        colNom.setCellValueFactory(new PropertyValueFactory<Medicament,String>("nomMed"));
        colPrix.setCellValueFactory(new PropertyValueFactory<Medicament,Float>("prixMed"));
        colStock.setCellValueFactory(new PropertyValueFactory<Medicament,Integer>("qte"));
        colType.setCellValueFactory(new PropertyValueFactory<Medicament,String>("typeMed"));
    }

    @FXML
    private void Consulter() {
        int id = this.tv.getSelectionModel().getSelectedItem().getCodeMed();
        String nom = this.tv.getSelectionModel().getSelectedItem().getNomMed();
        float prix = this.tv.getSelectionModel().getSelectedItem().getPrixMed();
        int stock = this.tv.getSelectionModel().getSelectedItem().getQte();
        String type = this.tv.getSelectionModel().getSelectedItem().getTypeMed();
        Id.setText(id+"");
        Nom.setText(nom);
        Prix.setText(prix+"");
        Stock.setText(stock+"");
        Type.setText(type);
    }

    private void remiseAzéro() {
        Id.setText("");
        Nom.setText("");
        Prix.setText("");
        Stock.setText("");
        Type.setText("");
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.lister();
    }
}
