package com.example.demo;

import com.example.demo.dao.DaoUtilisateur;
import com.example.demo.entite.Utilisateur;
import com.example.demo.utils.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Objects;
import java.util.ResourceBundle;

public class UtilisateurController {
    private final Connection cn = DbConnection.seConnecter();
    private final DaoUtilisateur daoUtilisateur = new DaoUtilisateur();

    @FXML
    TextField Nom;
    @FXML
    TextField Email;
    @FXML
    TextField Password;
    @FXML
    TextField Telephone;


    Utilisateur utilisateur = new Utilisateur();
    @FXML
    public void Register(ActionEvent event){
        utilisateur.setNom(Nom.getText());
        utilisateur.setEmail(Email.getText());
        utilisateur.setMotDePasse(Password.getText());
        utilisateur.setTelephone(Integer.parseInt(Telephone.getText()));
        utilisateur.setRole("Role_Utilisateur");

        if(daoUtilisateur.add(utilisateur)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("S'inscrire");
            alert.setContentText("S'inscrire avec succès");
            alert.showAndWait();
            try {
                GestionPatient(event);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("S'inscrire");
            alert.setContentText("Echec d'inscri");
            alert.showAndWait();
        }
    }

    @FXML
    public void Login(ActionEvent event){
        utilisateur.setEmail(Email.getText());
        utilisateur.setMotDePasse(Password.getText());

        if(daoUtilisateur.Authentifier(utilisateur)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authentifier");
            alert.setContentText("Authentifier avec succès");
            alert.showAndWait();
            try {
                GestionPatient(event);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentifier");
            alert.setContentText("Echec de authentification");
            alert.showAndWait();
        }
    }

    @FXML
    public void toLogin(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));

        Scene se=new Scene(root);
        st2.setScene(se);
        st2.setTitle("Se Connecter");
        st2.show();
    }

    @FXML
    public void toRegister(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Register.fxml")));

        Scene se=new Scene(root);
        st2.setScene(se);
        st2.setTitle("S'inscrire");
        st2.show();
    }

    private void GestionPatient(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage st2= new Stage();
        Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Patient.fxml")));

        Scene se=new Scene(root);
        st2.setScene(se);
        st2.setTitle("Gestion Patient");
        st2.show();
    }

}
