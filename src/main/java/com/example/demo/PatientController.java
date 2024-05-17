package com.example.demo;

import com.example.demo.dao.DaoPatient;
import com.example.demo.entite.Patient;
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
import javafx.scene.control.Alert;
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

public class PatientController implements Initializable {
    private final Connection cn = DbConnection.seConnecter();
    private final DaoPatient daoPatient = new DaoPatient();

    @FXML
    TableView<Patient> tv = new TableView<>();

    @FXML
    TextField Code;
    @FXML
    TextField Nom;
    @FXML
    TextField Email;
    @FXML
    TextField Telephone;

    @FXML
    TextField Recherche;

    @FXML
    TableColumn<Patient, Integer> colCode;
    @FXML
    TableColumn<Patient, String> colNom;
    @FXML
    TableColumn<Patient, String> colEmail;
    @FXML
    TableColumn<Patient, String> colTelephone;

    @FXML
    ObservableList<Patient> observableList = FXCollections.observableArrayList();

    Patient patient = new Patient();

    @FXML
    private void Ajouter() {
        patient.setCode(Integer.parseInt(Code.getText()));
        patient.setNom(Nom.getText());
        patient.setEmail(Email.getText());
        patient.setTel(Telephone.getText());

        if(daoPatient.add(patient) && checkInput()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajouter Patient");
            alert.setContentText("Ajouter avec succès");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ajouter Patient");
            alert.setContentText("Echec d'ajout");
            alert.showAndWait();
        }
        lister();
        remiseAzero();
    }

    @FXML
    private void Modifier() {
        patient.setCode(Integer.parseInt(Code.getText()));
        patient.setNom(Nom.getText());
        patient.setEmail(Email.getText());
        patient.setTel(Telephone.getText());
        if(daoPatient.update(patient) && checkInput()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modification Patient");
            alert.setContentText("Modification avec succès");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modification Patient");
            alert.setContentText("Echec de modification");
            alert.showAndWait();
        }
        lister();
        remiseAzero();
    }

    @FXML
    private void Archiver() {
        if(daoPatient.delete(Integer.parseInt(Code.getText()))){
            System.out.println("Archivage avec succès");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Archivage Patient");
            alert.setContentText("Archivage avec succès");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Archivage Patient");
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
            ResultSet rs = cn.createStatement().executeQuery("select * from patient");
            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                );
                observableList.add( p );
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("tel"));

        tv.setItems(observableList);
    }

    @FXML
    public void Rechercher(){
        tv.getItems().clear();

        observableList.addAll(daoPatient.findByName(Recherche.getText()));

        tv.setItems(observableList);

        if(Recherche.getText().isEmpty()){
            lister();
        }
    }

    @FXML
    private void Consulter() {
        int code = this.tv.getSelectionModel().getSelectedItem().getCode();
        String nom = this.tv.getSelectionModel().getSelectedItem().getNom();
        String email = this.tv.getSelectionModel().getSelectedItem().getEmail();
        String telephone = this.tv.getSelectionModel().getSelectedItem().getTel();

        Code.setText(code+"");
        Nom.setText(nom);
        Email.setText(email);
        Telephone.setText(telephone);
    }

    @FXML
    private void GestionMedicament(ActionEvent event) throws Exception {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Medicament.fxml")));

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
        st2.setTitle("Gestion Patient");
        st2.show();
    }

    private void remiseAzero() {
        Code.setText("");
        Nom.setText("");
        Email.setText("");
        Telephone.setText("");
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.lister();
    }

    private boolean checkInput(){
        if (!FormatTypeCheck.checkInt(Code.getText()) && Code.getText().isEmpty()) return false;
        if (!FormatTypeCheck.checkInt(Telephone.getText()) && Telephone.getText().isEmpty()) return false;
        if (Nom.getText().isEmpty()) return false;
        if (Email.getText().isEmpty()) return false;

        return true;
    }
}
