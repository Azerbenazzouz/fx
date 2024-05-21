package com.example.demo;

import com.example.demo.dao.DaoMedicament;
import com.example.demo.entite.Medicament;
import com.example.demo.utils.DbConnection;
import com.example.demo.utils.FormatTypeCheck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    TextField Rechercche;


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

        if(daoMedicament.add(medicament) && checkInput()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout Medicament");
            alert.setContentText("Ajout avec succès");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ajout Medicament");
            alert.setContentText("Echec d'ajout");
            alert.showAndWait();
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
        if(daoMedicament.update(medicament) && checkInput()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modifier Medicament");
            alert.setContentText("Modifier avec succès");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modifier Medicament");
            alert.setContentText("Echec de modification");
            alert.showAndWait();
        }
        lister();
        remiseAzero();
    }

    @FXML
    private void Archiver() {
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Archivage Medicament");
        alert2.setContentText("Voulez-vous vraiment archiver ce medicament ?");
        alert2.showAndWait();
        ButtonType buttonType = alert2.getResult();
        if(buttonType != ButtonType.OK){
            lister();
            remiseAzero();
            return;
        }
        if(daoMedicament.delete(Integer.parseInt(Id.getText()))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Archivage Medicament");
            alert.setContentText("Archivage avec succès");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Archivage Medicament");
            alert.setContentText("Echec d'archivage");
            alert.showAndWait();
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
    public void Rechercher(){
        tv.getItems().clear();

        observableList.addAll(daoMedicament.findByName(Rechercche.getText()));

        tv.setItems(observableList);

        if(Rechercche.getText().isEmpty()){
            lister();
        }
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

    @FXML
    private void GestionPatMed(ActionEvent event) throws Exception {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("PatMed.fxml")));

        Scene se=new Scene(root);
        st2.setScene(se);
        st2.setTitle("Gestion Commande");
        st2.show();
    }

    @FXML
    private void GestionCommande(ActionEvent event) throws Exception {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ListeCommande.fxml")));

        Scene se=new Scene(root);
        st2.setScene(se);
        st2.setTitle("Liste Commandes");
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

    private boolean checkInput(){
        if (!FormatTypeCheck.checkInt(Id.getText()) && Id.getText().isEmpty()) return false;
        if (!FormatTypeCheck.checkFloat(Prix.getText()) && Id.getText().isEmpty()) return false;
        if (!FormatTypeCheck.checkInt(Stock.getText()) && Stock.getText().isEmpty()) return false;
        if (Nom.getText().isEmpty()) return false;
        if (Type.getText().isEmpty()) return false;

        return true;
    }


}
