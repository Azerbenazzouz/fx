package com.example.demo;

import com.example.demo.dao.DaoMedicament;
import com.example.demo.dao.DaoPatient;
import com.example.demo.dao.DaoPatientMedicament;
import com.example.demo.entite.Medicament;
import com.example.demo.entite.Patient;
import com.example.demo.entite.PatientMedicament;
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

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class PatientMedicamentController implements Initializable {
    private final Connection cn = DbConnection.seConnecter();
    private final DaoMedicament daoMedicament = new DaoMedicament();
    private final DaoPatient daoPatient = new DaoPatient();
    private final DaoPatientMedicament daoPatientMedicament = new DaoPatientMedicament();

    @FXML
    private TableView<Medicament> medicamentTable = new TableView<>();
    @FXML
    private TableView<Patient> patientTable = new TableView<>();
    @FXML
    private TableView<Medicament> medicamentCommandeTable = new TableView<>();

    @FXML
    TextField CodeMedRecherche;
    @FXML
    TextField CodePatRecherche;
    @FXML
    TextField QteMed;
    @FXML
    TextField QteMedModifier;
    @FXML
    Label Total;

    // List Medicament
    @FXML
    TableColumn<Medicament,String> NomMedList;
    @FXML
    TableColumn<Medicament,Integer> QteMedList;
    @FXML
    TableColumn<Medicament,Integer> PrixMedList;
    // List Patient
    @FXML
    TableColumn<Patient,Integer> CodePatList;
    @FXML
    TableColumn<Patient,String> NomPatList;
    // List Commande
    @FXML
    TableColumn<Medicament,Integer> CodeMedListCmd;
    @FXML
    TableColumn<Medicament,String> NomMedListCmd;
    @FXML
    TableColumn<Medicament,Integer> QteMedListCmd;
    @FXML
    TableColumn<Medicament,Integer> PrixMedListCmd;

    @FXML
    ObservableList<Medicament> obsMedList = FXCollections.observableArrayList();
    @FXML
    ObservableList<Patient> obsPatList = FXCollections.observableArrayList();
    @FXML
    ObservableList<Medicament> obsMedCmdList = FXCollections.observableArrayList();


    private void listerMedicament() {
        medicamentTable.getItems().clear();

        try{
            String req = "select * from medicament";
            ResultSet rs = cn.createStatement().executeQuery(req);

            while (rs.next()) {
                obsMedList.add(
                        new Medicament(
                                rs.getString(2),
                                rs.getInt(4),
                                rs.getFloat(3),
                                rs.getInt(1)
                        )
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        NomMedList.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
        PrixMedList.setCellValueFactory(new PropertyValueFactory<>("prixMed"));
        QteMedList.setCellValueFactory(new PropertyValueFactory<>("qte"));

        medicamentTable.setItems(obsMedList);
    }

    private void listerPatient() {
        patientTable.getItems().clear();

        try{
            String req = "select * from patient";
            ResultSet rs = cn.createStatement().executeQuery(req);

            while (rs.next()) {
                obsPatList.add(
                        new Patient(
                                rs.getInt(1),
                                rs.getString(2)
                        )
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        CodePatList.setCellValueFactory(new PropertyValueFactory<>("code"));
        NomPatList.setCellValueFactory(new PropertyValueFactory<>("Nom"));

        patientTable.setItems(obsPatList);
    }

    private void listerCommandes() {
        CodeMedListCmd.setCellValueFactory(new PropertyValueFactory<>("codeMed"));
        NomMedListCmd.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
        QteMedListCmd.setCellValueFactory(new PropertyValueFactory<>("Qte"));
        PrixMedListCmd.setCellValueFactory(new PropertyValueFactory<>("prixMed"));
        medicamentCommandeTable.setItems(obsMedCmdList);
        prixTotal();
    }

    public void prixTotal(){
        total = 0;
        for (Medicament medicament : obsMedCmdList){
            total += medicament.getPrixMed();
        }
        Total.setText(String.valueOf(total)+" TND");
    }

    @FXML
    private void supprimerCmd(){
        Medicament medicament = medicamentCommandeTable.getSelectionModel().getSelectedItem();
        obsMedCmdList.remove(medicament);
        listerCommandes();
    }

    @FXML
    private void modifierCmd(){
        try {
            int qte = Integer.parseInt(QteMedModifier.getText());
            Medicament medicament = medicamentCommandeTable.getSelectionModel().getSelectedItem();
            if(qte<=medicament.getQte()){
                medicament.setQte(qte);
                medicament.setPrixMed(qte*medicament.getPrixMed());
                listerCommandes();
            }
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listerMedicament();
        listerPatient();
        listerCommandes();
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
    private void GestionCommande(ActionEvent event) throws Exception {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ListeCommande.fxml")));

        Scene se=new Scene(root);
        st2.setScene(se);
        st2.setTitle("Liste Commandes");
        st2.show();
    }

    @FXML
    public void RechercherMed(){
        medicamentTable.getItems().clear();

        obsMedList.addAll(daoMedicament.findByName(CodeMedRecherche.getText()));

        medicamentTable.setItems(obsMedList);

        if(CodeMedRecherche.getText().isEmpty()){
            listerMedicament();
        }
    }

    @FXML
    public void RechercherPat(){
        patientTable.getItems().clear();

        obsPatList.addAll(daoPatient.findByName(CodePatRecherche.getText()));

        patientTable.setItems(obsPatList);

        if(CodePatRecherche.getText().isEmpty()){
            listerMedicament();
        }
    }

    Medicament medicament;
    float total = 0;

    @FXML
    private void selectMed(){
        Integer code = this.medicamentTable.getSelectionModel().getSelectedItem().getCodeMed();
        String nom=this.medicamentTable.getSelectionModel().getSelectedItem().getNomMed();
        int qte=this.medicamentTable.getSelectionModel().getSelectedItem().getQte();
        float prix=this.medicamentTable.getSelectionModel().getSelectedItem().getPrixMed();

        medicament = new Medicament(nom,qte,prix,code);
    }

    @FXML
    private void ajouterCmd(){
        try {
            int qte = Integer.parseInt(QteMed.getText());
            if(medicament != null){
                if(qte<=medicament.getQte()){
                    
                    ArrayList<Medicament> list = new ArrayList<>();
                   
                    for(int i=0;i<obsMedCmdList.size();i++){
                        Medicament med = obsMedCmdList.get(i);
                        if(med.getCodeMed() != medicament.getCodeMed()){
                            list.add(med);
                        }
                    }
                    medicament.setQte(qte);
                    medicament.setPrixMed(qte * medicament.getPrixMed());
                    list.add(medicament);

                    obsMedCmdList.clear();
                    obsMedCmdList.addAll(list);
                    listerCommandes();

                    medicamentTable.getSelectionModel().clearSelection();
                    QteMed.setText("");
                    
                }
            }
        }catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void Commander(){
        if (patientTable.getSelectionModel().getSelectedItem() != null){
            int codePat = patientTable.getSelectionModel().getSelectedItem().getCode();
            for(Medicament medicament : obsMedCmdList){
                PatientMedicament patMed = new PatientMedicament(medicament,daoPatient.findById(codePat),medicament.getQte());
                daoPatientMedicament.add(patMed);
            }

            obsMedCmdList.clear();
            listerCommandes();
            patientTable.getSelectionModel().clearSelection();
            refresh();
        }
    }

    private void refresh(){
        obsMedList.clear();
        obsPatList.clear();
        obsMedCmdList.clear();
        listerMedicament();
        listerPatient();
        listerCommandes();
    }
}
