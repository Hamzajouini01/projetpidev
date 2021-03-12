/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo;

import com.sun.javafx.scene.control.SelectedCellsMap;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import sun.awt.image.BufImgSurfaceData;

/**
 *
 * @author ASUS
 */
public class MainController implements Initializable {

    @FXML
    private TextField tfID_seance;
    @FXML
    private TextField tfID_formation;
    @FXML
    private TextField tflien;
    @FXML
    private TextField tfdescription;
    @FXML
    private DatePicker tfdate;
    @FXML
    private TableView<Seance> tvseance;
    @FXML
    private TableColumn<Seance, Integer> colID_seance;
    @FXML
    private TableColumn<Seance, Integer> colID_formation;
    @FXML
    private TableColumn<Seance, String> collien;
    @FXML
    private TableColumn<Seance, String> coldescription;
    @FXML
    private TableColumn<Seance, String> coldate;
    @FXML
    private Button btnajouter;
    @FXML
    private Button btnmodifier;
    @FXML
    private Button btnsupprimer;
    @FXML
    private TextField filterField;
int from=0, to =0;
    int itemPerPage=5;
    @FXML

    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnajouter) {
            insertRecord();
        } else if (event.getSource() == btnmodifier) {
            updateRecord();
        } else if (event.getSource() == btnsupprimer) {
            deleteButton();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showSeance();
    }

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetpi", "root", "");
            return conn;
        } catch (SQLException ex) {
            System.out.println("Error:" + ex.getMessage());
            return null;
        }
    }

    public ObservableList<Seance> getSeanceList() {
        ObservableList<Seance> seanceList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM seance";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Seance seance;
            while (rs.next()) {
                seance = new Seance(rs.getInt("ID_seance"), rs.getInt("ID_formation"), rs.getString("lien"), rs.getString("description"), rs.getString("Date_seance"));
                seanceList.add(seance);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return seanceList;
    }

    @FXML
    private void editAccount() {
        Seance selected = tvseance.getSelectionModel().getSelectedItem();
        tfID_seance.setText(String.valueOf(selected.getID_seance()));
        tfID_formation.setText(String.valueOf(selected.getID_formation()));
        tflien.setText(selected.getLien());
        tfdescription.setText(selected.getDescription());

    }

    public void showSeance() {
        ObservableList<Seance> list = getSeanceList();
        colID_seance.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("ID_seance"));
        colID_formation.setCellValueFactory(new PropertyValueFactory<Seance, Integer>("ID_formation"));
        collien.setCellValueFactory(new PropertyValueFactory<Seance, String>("lien"));
        coldescription.setCellValueFactory(new PropertyValueFactory<Seance, String>("description"));
        coldate.setCellValueFactory(new PropertyValueFactory<Seance, String>("Date_seance"));

        tvseance.setItems(list);

        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Seance> filteredData = new FilteredList<>(list, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Seance -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (Seance.getLien().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (Seance.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(Seance.getID_formation()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Seance> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tvseance.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tvseance.setItems(sortedData);
        
        //******************Email***************
        
         // Add recipient
 String to = "test@example.com";

// Add sender
 String from = "myusername@gmail.com";
 final String username = "6cf727084b6801";//your Gmail username 
 final String password = "645a20c1ec1351";//your Gmail password

String host = "smtp.mailtrap.io";

Properties props = new Properties();
 props.put("mail.smtp.auth", "true");
 props.put("mail.smtp.starttls.enable", "true"); 
 props.put("mail.smtp.host", host);
 props.put("mail.smtp.port", "587");

// Get the Session object
 Session session = Session.getInstance(props,
 new javax.mail.Authenticator() {
 protected PasswordAuthentication getPasswordAuthentication() {
 return new PasswordAuthentication(username, password);
 }
 });

try {
 // Create a default MimeMessage object
 Message message = new MimeMessage(session);
 
 message.setFrom(new InternetAddress(from));
 
 message.setRecipients(Message.RecipientType.TO,
 InternetAddress.parse(to));
 
 // Set Subject
 message.setSubject("Emploi du Temps");
 
 // Put the content of your message
 message.setText("Bonjour,M/Mr voici votre emploi du temps !!!");

// Send message
 Transport.send(message);

System.out.println("Sent message successfully....");

} catch (MessagingException e) {
 throw new RuntimeException(e);
 }
 }
        
    
    

    private void insertRecord() {
        /*String query = "INSERT INTO seance VALUES (" + tfID_seance.getText() + ",'formation.getText() + \"','\" + tflien.getText() + " + tfID_formation.getText() + "','" + tflien.getText() + "',"
                + tfdescription.getText() + ")";*/
        String query = ("INSERT INTO seance(ID_seance,ID_formation,lien,description,Date_seance)"
                + " VALUES(" + tfID_seance.getText() + "," + tfID_formation.getText() + ",'" + tflien.getText() + "', '" + tfdescription.getText() + "', '" + tfdate.getValue() + "');");

        executeQuery(query);
        showSeance();
    }

    private void updateRecord() {
        //String query = "UPDATE seance SET ID_formation='"+tfID_formation.getText()+"',lien='"+tflien.getText()+"',description="+tfdescription.getText()+",Date_seance="+tfdate.getValue()+" WHERE ID_seance="+tfID_seance.getText()+"";
        String query = "UPDATE seance SET ID_formation = '" + tfID_formation.getText() + "', lien = '" + tflien.getText() + "', description = '" + tfdescription.getText() + "', Date_seance = '" + tfdate.getValue() + "' WHERE seance.ID_seance = " + tfID_seance.getText() + "";
        executeQuery(query);
        showSeance();
    }

    private void deleteButton() {
        String query = "DELETE FROM seance WHERE ID_seance =" + tfID_seance.getText() + "";
        executeQuery(query);
        showSeance();
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
