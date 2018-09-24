/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers;
import common.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author kisekkaDavid
 */
public class editController implements Initializable{

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel 'Update Customer'?", ButtonType.YES, ButtonType.NO);
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
    public void fillFields(Customer c){
        textID.setText(c.getCustomerID());
        choiceType.setValue(c.getType());
        textFirstName.setText(c.getFirstName());
        textLastName.setText(c.getLastName());
        textAddress.setText(c.getPhone());
        textPostcode.setText(c.getPostcode());
        textPhone.setText(c.getPhone());
        textEmail.setText(c.getEmailAddress());
        
        textID.setDisable(true);
               
    }
    
    @FXML
    private void handleEditButton(ActionEvent event) throws SQLException{
        conn = dc.Connector();
        
        String customerID = textID.getText();
        String type = null;
        String firstName = null;
        String lastName = null;
        String Address = null;
        String Postcode = null;
        String Phone = null;
        String Email = null;
        String query = "UPDATE customers SET type=?,name=?,surname=?,address=?,postcode=?,phone=?,email=? where customer_id=?";
        
         

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
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, type);
                pst.setString(2, firstName);
                pst.setString(3, lastName);
                pst.setString(4, Address);
                pst.setString(5, Postcode);
                pst.setString(6, Phone);
                pst.setString(7, Email);
                pst.setString(8, customerID);
            
                pst.executeUpdate();
                pst.close();
      
            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.close(); 
            }
                        
        }
        catch(Exception e){
            e.printStackTrace();
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
