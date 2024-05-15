package com.example.demo;

import com.example.demo.dao.DaoMedicament;
import com.example.demo.entite.Medicament;
import com.example.demo.utils.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MedicamentController implements Initializable {
    private final Connection cn = DbConnection.seConnecter();
    private final DaoMedicament daoMedicament = new DaoMedicament();


    @FXML
    TableView<Medicament> tv = new TableView<>();

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

    @FXML
    ObservableList<Medicament> observableList = FXCollections.observableArrayList();

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
        remiseAzero();
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
        remiseAzero();
    }

    @FXML
    private void Archiver() {
        if(daoMedicament.delete(Integer.parseInt(Id.getText()))){
            System.out.println("Archivage avec succès");
        }else{
            System.out.println("Echec d'archivage");
        }
        lister();
        remiseAzero();
    }
    @FXML
    private void Clear(){
        lister();
        remiseAzero();
    }

    private void lister() {
        tv.getItems().clear();

        try {
            String req = "select * from medicament";
            ResultSet rs = cn.createStatement().executeQuery(req);
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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("codeMed"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixMed"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("qte"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeMed"));

        tv.setItems(observableList);
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

    @FXML
    private void GestionPatient(ActionEvent event) throws Exception {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Patient.fxml")));

        Scene se=new Scene(root);
        st2.setScene(se);
        st2.setTitle("Gestion Patient");

        st2.show();
    }

    private void remiseAzero() {
        Id.setText("");
        Nom.setText("");
        Prix.setText("");
        Stock.setText("");
        Type.setText("");
    }
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        this.lister();
    }
}
