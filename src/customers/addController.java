/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers;

import common.*;

import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author kisekkaDavid
 */

public class addController implements Initializable {

    @FXML
    private AnchorPane addCustomerPopup;
    @FXML
    private TextField textID;
    @FXML
    private ChoiceBox<String> choiceType;
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textPostcode;
    @FXML
    private TextField textPhone;
    @FXML
    private TextField textEmail;
    @FXML
    private Label labelID;
    @FXML
    private Label labelType;
    @FXML
    private Label labelFirstName;
    @FXML
    private Label labelLastName;
    @FXML
    private Label labelAddress;
    @FXML
    private Label labelPostcode;
    @FXML
    private Label labelPhone;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelInvalid;
    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;
    
    public Database connectionModel = new Database();
    public SqliteConnection dc;
    public Connection conn;
    
    @FXML Label isConnected;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        if(connectionModel.isDbConnected()){
            isConnected.setText("Connected");
        }
        else {
            isConnected.setText("Not Connected");
        }
        dc = new SqliteConnection();
        choiceType.setItems(FXCollections.observableArrayList("Private", "Business"));

    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Cancel 'Add New Customer'?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }
    else if (alert.getResult() == ButtonType.NO){
        alert.close();
    }
        
        
    }

    
    @FXML
    private void handleAddButton(ActionEvent event) {
        conn = dc.Connector();
        String query = "INSERT INTO customers(customer_id,type,name,surname,address,postcode,phone,email) VALUES(?,?,?,?,?,?,?,?);";
        String customerID = null;
        String type = null;
        String firstName = null;
        String lastName = null;
        String Address = null;
        String Postcode = null;
        String Phone = null;
        String Email = null;
        
        
        try {
            boolean allFilled = true;
            
            if(choiceType.getSelectionModel().getSelectedItem()==null){
                labelType.setTextFill(Color.web("#FF0000"));
                allFilled = false;
            }
            else {
                type = choiceType.getSelectionModel().getSelectedItem(); 
            }
            if(textFirstName.getText().equals("")) {
                labelFirstName.setTextFill(Color.web("#FF0000")); 
                allFilled = false;
            }
            else {
                firstName = textFirstName.getText();
            }
        
            if(textLastName.getText().equals("")) {
                labelLastName.setTextFill(Color.web("#FF0000")); 
                allFilled = false;
            }
            else {
                lastName = textLastName.getText();
            }
        
            if(textAddress.getText().equals("")) {
                labelAddress.setTextFill(Color.web("#FF0000"));
                allFilled = false;
            }
            else {
                Address = textAddress.getText();
            }
        
            if(textPostcode.getText().equals("")) {
                labelPostcode.setTextFill(Color.web("#FF0000")); 
                allFilled = false;
            }
            else {
                Postcode = textPostcode.getText();
            }
        
            if(textPhone.getText().equals("")) {
                labelPhone.setTextFill(Color.web("#FF0000"));
                allFilled = false;
            }
            else if(invalidPhone(textPhone.getText())){
                labelPhone.setTextFill(Color.web("#FF0000"));
                allFilled = false;
            }
            else {
                Phone = textPhone.getText();
            }
        
            if(textEmail.getText().equals("")) {
                labelEmail.setTextFill(Color.web("#FF0000")); 
                allFilled = false;
            }
            else {
                Email = textEmail.getText();
            }

            if(allFilled) {
                customerID = generateID(firstName, lastName); 
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, customerID);
                pst.setString(2, type);
                pst.setString(3, firstName);
                pst.setString(4, lastName);
                pst.setString(5, Address);
                pst.setString(6, Postcode);
                pst.setString(7, Phone);
                pst.setString(8, Email);
            
                pst.executeUpdate();
                pst.close();
                
                Stage stage = (Stage) cancelBtn.getScene().getWindow();
                stage.close();
            }
                        
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
    
    private String generateID(String fName, String lName){
        int i = 1;
        String id = fName.substring(0,1)+lName.substring(0,3)+i;
        if(IDExists(id)) {
            i++;
            id = fName.substring(0,1)+lName.substring(0,3)+i;
        }
        return id;        
    }
    private boolean IDExists(String id){
        boolean exists = false;
        System.out.println(id);
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT customer_id FROM customers;");
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                if(rs.getString("customer_id").equals(id)) {
                    exists = true;
                    break;
                }
            }
            pst.close();
            rs.close();
            return exists;
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return true;
        }

    }
    
    private boolean invalidPhone(String phoneNo) {
        for(char c : phoneNo.toCharArray()){
            if(!Character.isDigit(c)){
                labelInvalid.setText("*invalid phone number");
                labelInvalid.setVisible(true);
                return true;
            }
        }
        if(phoneNo.length()!=10){
                labelInvalid.setText("*Please enter a valid 10-digit phone number");
                labelInvalid.setVisible(true);
            return true;
        }
        
        return false;
    } 
}
