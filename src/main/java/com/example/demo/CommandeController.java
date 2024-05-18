package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.demo.dao.DaoMedicament;
import com.example.demo.dao.DaoPatient;
import com.example.demo.dao.DaoPatientMedicament;
import com.example.demo.entite.Commande;
import com.example.demo.entite.Medicament;
import com.example.demo.entite.Patient;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CommandeController implements Initializable {
    private final Connection cn = DbConnection.seConnecter();
    private final DaoMedicament daoMedicament = new DaoMedicament();
    private final DaoPatient daoPatient = new DaoPatient();
    private final DaoPatientMedicament daoPatientMedicament = new DaoPatientMedicament();

    private File csvFile;

    @FXML
    private TableView<Commande> commandeTable;
    @FXML
    private TableView<Medicament> medicamentTable;

    @FXML
    private TextField CodePatRecherche;

    @FXML
    private Label Total;

    @FXML
    private TableColumn<Patient, String> NomPatCmd;
    @FXML
    private TableColumn<Commande, Date> DateCmd;
    @FXML
    private TableColumn<Commande, Float> PrixCmd;
    @FXML
    private TableColumn<Medicament, String> NomMed;
    @FXML
    private TableColumn<Medicament, Integer> QteMed;
    @FXML
    private TableColumn<Medicament, Float> PrixMed;

    @FXML
    ObservableList<Commande> observableCommandeList = FXCollections.observableArrayList();
    @FXML
    ObservableList<Medicament> observableMedList = FXCollections.observableArrayList();

    @FXML
    void GestionMedicament(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Medicament.fxml")));

        Scene se=new Scene(root);
        st2.setScene(se);
        st2.setTitle("Gestion Patient");

        st2.show();
    }

    @FXML
    void GestionPatient(ActionEvent event) throws IOException {
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
        st2.setTitle("Gestion Patient");
        st2.show();
    }

    @FXML
    void RechercherPat() {
        commandeTable.getItems().clear();
        medicamentTable.getItems().clear();

        observableCommandeList.addAll(daoPatientMedicament.findAllCommandeByName(CodePatRecherche.getText()));
        commandeTable.setItems(observableCommandeList);

        if(CodePatRecherche.getText().isEmpty()){
            lister();
        }
    }

    @FXML
    void Exporter(ActionEvent event){
        Commande c = commandeTable.getSelectionModel().getSelectedItem();
        if(c == null) return;

        csvFile = new File("Export Medicament "+c.getNomPatient()+" "+c.getDate().toString());
        try {
            PrintWriter out = new PrintWriter(csvFile);
            for(Medicament med : c.getMedicaments()){
                out.println(med.getCodeMed()+" , "+med.getNomMed()+" , "+med.getQte()+" , "+med.getPrixMed());
            }
            out.close();
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    private void lister(){
        commandeTable.getItems().clear();

        for(Commande c : daoPatientMedicament.findAllCommande()){
            observableCommandeList.add(c);
        }

        NomPatCmd.setCellValueFactory(new PropertyValueFactory<>("nomPatient"));
        DateCmd.setCellValueFactory(new PropertyValueFactory<>("date"));
        PrixCmd.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));

        commandeTable.setItems(observableCommandeList);
    }

    @FXML
    public void SelectCommande(){
        Commande c = this.commandeTable.getSelectionModel().getSelectedItem();

        this.medicamentTable.getItems().clear();

        observableMedList.addAll(c.getMedicaments());

        NomMed.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
        QteMed.setCellValueFactory(new PropertyValueFactory<>("Qte"));
        PrixMed.setCellValueFactory(new PropertyValueFactory<>("PrixMed"));

            prixTotal();
        this.medicamentTable.setItems(observableMedList);

    }

    public void prixTotal(){
        float total = 0;
        for (Medicament medicament : observableMedList){
            total += medicament.getPrixMed();
        }
        Total.setText(String.valueOf(total)+" TND");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lister();
    }
}
