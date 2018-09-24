/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;

import java.net.URL;
import java.util.ResourceBundle;

import common.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.util.*;
import java.time.LocalDate;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author kisekkaDavid
 */
public class PartsSceneController implements Initializable {



    //Parts GUI Page 1:

    //Page one search bar text field:
    @FXML private TextField mainPartSearchField;
    //CheckBox for whether the table contains used parts:
    @FXML private CheckBox removeUsedPartsButton;
    //And a boolean representing its value so we dont always have to call isSelected().
    private boolean conUsedParts = true; 
    //FXML variables for page 1 table:
    @FXML private TableView<Part> partsTable; 
    @FXML private TableColumn<Part,String> partNameCol,partPriceCol,partInstallDateCol,partLocationCol,partDescriptionCol;
    //Observable list to hold parts for table 1:
    private ObservableList<Part> partData = FXCollections.observableArrayList();
   
    //Parts GUI Page 2:
    
    //date picker for page 2 table:
    @FXML private DatePicker page2DatePicker;
    //FXML variables for page 2 table:
    @FXML private TableView<partBookingTableObject> partBookingTable; 
    @FXML private TableColumn<partBookingTableObject,String> custNameCol,vehRegCol,bookingDateCol,partsRequiredCol;
    //Observable list to hold parts for table 2:
    private ObservableList<partBookingTableObject> partBookingData = FXCollections.observableArrayList();
    //checkBox to remove past bookings on page 2:
    @FXML private CheckBox removePastBookingsCheckBox;
    //checkBox to remove SR bookings on page 2:
    @FXML private CheckBox removeSRCheckBox;
    @FXML final private ArrayList<Label> labels = new ArrayList<>();
    @FXML final private ArrayList<Control> controls = new ArrayList<>();



    //Parts GUI Page 3:
    
  
    //FXML variables for page 2 table:
    @FXML private TableView<partBookingTableObject> vehiclePartsTable; 
    //@FXML private TableColumn<partBookingTableObject,String> custNameCol,vehRegCol,bookingDateCol,partsRequiredCol;
    //Observable list to hold parts for table 2:
    private ObservableList<partBookingTableObject> vehiclePartsData = FXCollections.observableArrayList();
    
    
    
    
    
    @FXML private Label label;
    Part p;
    Part pp;
    Part selectedPart;
    
    
    
    
    
    Label warning;
    TextField partNameTF;
    TextField partPriceTF;
    TextField partInstallTF;
    TextField partWarrantyTF;
    TextField partLocationTF;
    public Stage dialog;
    
    //Buttons
    Button add;
    Button cancel;
    
    
    
    private Main mainApp;
//    private DataBaseManager database;
    private PartsDatabase partDatabase;
    public Connection connection;

    //For The Pop Up:
    public Stage popUp;
    private boolean incSR = true;
    private boolean incPB = true;
    
    
    //Maybe Get rid of this?
    public PartsSceneController(){
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //create the database with its connection:
//        database = new DataBaseManager();
        partDatabase = new PartsDatabase(connection);

        //Initialise The Settings For The Tables On Each Page And Where Each Collumn Will Get Their Data From.
//        this.setTableOneSettings();
//        this.setTableTwoSettings();
//        this.setTableThreeSettings();

        //Set columns of First Part Table.
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(2));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));
        partData.add(new Part(1));

        partNameCol.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        partPriceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
        partInstallDateCol.setCellValueFactory(cellData -> cellData.getValue().getInstallDateProperty());
        partLocationCol.setCellValueFactory(cellData -> cellData.getValue().getWarrantyProperty());
        partDescriptionCol.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        partsTable.setItems(partData);


        System.out.println("finished initialise");


        p = new Part(1);
        pp = new Part(2);

        partBookingTableObject obj = new partBookingTableObject(p);

        obj.addPart(pp);

        partBookingData.add(obj);

        custNameCol.setCellValueFactory(cellData -> cellData.getValue().getCustNameProperty());
        vehRegCol.setCellValueFactory(cellData -> cellData.getValue().getVehRegProperty());
        bookingDateCol.setCellValueFactory(cellData -> cellData.getValue().getBookingDateProperty());
        partsRequiredCol.setCellValueFactory(cellData -> cellData.getValue().getAllPartsProperty());

        partBookingTable.setItems(partBookingData);

        System.out.println("finished initialise again");
    }
    
    
    
    //
    //
    // FIRST TAB ONLY:
    //
    //
    
    //This Method is Called When The First Page Is Clicked On.
    @FXML private void allPartsPaneClicked(){
        this.updateTable1();
    }
    
    
   
   //Below Two Methods Regard Filling The Table On PAge 1: 
    
    //This Method Can Be Called To Fill The Page One Table Parts Specific To The Options Chosen At This Time.
    @FXML public void updateTable1(){
        //Use the parts database to search all eligible items and set them in the table.
        try{
        this.setPartsTable(partDatabase.getAllPartsForTable1(conUsedParts));
        } catch(Exception e){System.out.println("Failed"); }
    }
    
    //This method updates the parts in the page 1 table to the observable list given.
    @FXML private void setPartsTable(ObservableList<Part> newParts){
       
       partData = newParts;
       partsTable.setItems(partData);
       
    }
    
    //Control Methods For Top Row Of Parts Page 1 GUI:
    
      @FXML //This Method Is Called When The Page One Search Button Is Pressed.
    public void allPartsSearchButtonPressed(ActionEvent event) {
        
        //Get Text From TextField.
        String search = mainPartSearchField.getText();
        //Refine The Table To Include These Options Only:
        this.setPartsTable(partDatabase.searchParts(search,conUsedParts));
        
        
    }
    
    //This method either refines the table to take out used parts or adds them back in depending on the state of the checkbox.
    @FXML private void removeUsedPartsPressedP1(ActionEvent event) {
        
        //set conUsedParts boolean to the value of the checkbox in the GUI.
        conUsedParts = removeUsedPartsButton.isSelected();
        
        //Get List Of Eligable Parts From Database:
        partData = partDatabase.getAllPartsForTable1(conUsedParts);
        
        //fill table with them:
        partsTable.setItems(partData); 
        
    }
    
    //Controls For Bottom 3 Buttons On Page 3:
    
    //This Method Is Called When The Page 1 Add Parts Button Is Pressed, Simply Runs The Add Part Pop Up.
    @FXML private void addPartToStockButtonPressed(){
        try{ 
            this.createAddPartPopUp();
        } catch (Exception e){
            //FINISH THIS.
        }
        //Add button[
//        add.setOnAction((ActionEvent e) -> {
//            partData = partDatabase.addPartToDataBase(partNameTF,partPriceTF,partInstallTF,partWarrantyTF,partLocationTF);
//
//        });
    }
    
    //This Method Is Called When The Delete part button is pressed on page 1
     //The Method finds which part is selected and deletes it from the database and table.
     @FXML public void deletePartsButtonPressedP1(){
        
        //Get Selected Part From Table:
         selectedPart = new Part();
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        
        String partID = selectedPart.getID();
        partDatabase.deletePart(partID);
        this.updateTable1();
    }

    private boolean checkPart() throws SQLException{
        PreparedStatement s = null;
        ResultSet rs = null;
        try{
            s = connection.prepareStatement("SELECT name FROM partData;");
            rs = s.executeQuery();
            while(rs.next()) {
                String un = rs.getString("username");
                if(un.equals(partNameTF.getText())){
                    return false;
                }
            }
            if(partNameTF.getText().length()==5){
                return true;
            }
            else{
                return false;
            }
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
        finally {
            if (s != null) { s.close();rs.close(); }
        }
    }


    public boolean fieldsCheck(){
        int counter = 0;
        int checkCounter = 0;
        for(Control c : controls){
            if((c instanceof TextField && ((TextField)c).getText().equals("") && !c.isDisabled()) || (c instanceof ChoiceBox && ((ChoiceBox)c).getValue()==null && !c.isDisabled())){
                labels.get(counter).setTextFill(Color.web("#FF0000"));
                counter++;
                checkCounter++;

            }
            else{
                labels.get(counter).setTextFill(Color.web("#000000"));
                counter++;
            }
        }
        return checkCounter==0;
    }
     
     @FXML public void editPartsButtonPressedP1(){
         
     }
//
//    public void buildData(String q) throws SQLException{
//        parts = FXCollections.observableArrayList();
//
//        PreparedStatement s = null;
//        ResultSet rs = null;
//
//        try {
//            if(q.equals("")){
//                s = connection.prepareStatement("SELECT * FROM partData;");
//            }
//            else{
//                s = connection.prepareStatement("SELECT * FROM partData WHERE name LIKE ? OR vehicle_reg LIKE ?;");
//                s.setString(1,"%"+q+"%");
//                s.setString(2,"%"+q+"%");
//            }
//
//            rs = s.executeQuery();
//
//            while(rs.next()) {
//                String part_id = rs.getString("part_id");
//                String vehicle_reg = rs.getString("vehicle_reg");
//                String name = rs.getString("name");
//                String description = rs.getString("description");
//                String price = rs.getString("price");
//                String installed_date = rs.getString("installed_date");
//                String warranty_date = rs.getString("warranty_date");
//                String location = rs.getString("location");
//
//                Part u = new Part(name,description,part_id,price,installed_date,warranty_date,location,vehicle_reg);
//                parts.add(u);
//            }
//
//            partsTable.getItems().setAll(parts);
//        } catch (SQLException e ) {
//            System.out.println(e);
//        } finally {
//            if (s != null) { s.close();rs.close(); }
//        }
//    }
//
    
    
    
    //
    //
    // SECOND TAB ONLY:
    //
    //
     
     //Below Two Methods Regard Filling The Table On Page 2: 
     
     //this method is called when the second pane is clicked, it fills the table.
     @FXML public void bookingPaneClicked(){
         this.updateTable2();
     }
    
     
    //this method fills the part booking table taking into account the options chosen.
    @FXML public void updateTable2(){
        
        //Use the parts database to search all eligible items and set them in the table.
        
        if(page2DatePicker.getValue()==null){
            
            //No Date Chosen So Only Have To Take Into Account Whether To include SR And Past Bookings:
            
            //Search Database For Eligible Parts 
            this.setPartBookingTable(partDatabase.getPartBookingObjects(incSR,incPB));
            
            
        } else {
            //A date has been chosen so have to take into account the date aswell as whether to include SR And Past Bookings:
            LocalDate dateChosen = page2DatePicker.getValue();
            
            //Search Database For Eligible Parts //MUST CHANGE THIS TO ACCOUNT FOR THE DATE.
            this.setPartBookingTable(partDatabase.getPartBookingObjects(incSR,incPB));
        } 
    }
    
    
    //This method updates the parts in the page 1 table to the observable list given.
    @FXML private void setPartBookingTable(ObservableList<partBookingTableObject> newParts){
       
       partBookingData = newParts;
       partBookingTable.setItems(partBookingData);
       
    }
    
    //This Method Is Called When The Date IS Refined On Page 2.
    @FXML private void bookingDateChosen(){
       
        this.updateTable2();
        
    }
    
    //This method is called when the remove SR button is clicked.
    @FXML private void removeSRbuttonPressed(){
       //Update the boolean determining wheter to include SR bookings:
       incSR = removeSRCheckBox.isSelected();
       //update the table with these preferences:
       this.updateTable2();
       
    }
    
    //This method is called when the remove SR button is clicked.
    @FXML private void removePastBookingsPressed(){
        
        //Update the boolean determining wheter to include past bookings:
        incPB = removePastBookingsCheckBox.isSelected();
        //update the table with these preferences:
        this.updateTable2();
        
       
    }
    
    
    
    
    
    //MaybeNotNeeded.
     public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

     
    }
     
     //this method should not be used....... ?
    @FXML private void setPartsTableAL(ArrayList<Part> newParts){
       //Have To get Rid Of Old Parts And Set Them As This.
       partData.clear();
       for(int i=0; i<newParts.size(); i++){
       partData.add(newParts.get(i));
       }
       partsTable.setItems(partData);
       
    }
    
     
@FXML public void createAddPartPopUp() throws SQLException{
        
//Nodes
        HBox hb = new HBox();
       
        Label title = new Label("Add Part");
        
        
        partNameTF = new TextField();
        partPriceTF = new TextField();
        partWarrantyTF = new TextField();
        partInstallTF = new TextField();
        partLocationTF = new TextField();
  
        //Buttons
        add = new Button("Add Part");
        cancel = new Button("Cancel And Return To Page");
       


        //Setting up - Stage for popup
        popUp = new Stage();
        hb.setAlignment(Pos.CENTER);
        popUp.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(15);
        dialogVbox.setPadding(new Insets(10,10,10,20));

        hb = new HBox(5);
        hb.setAlignment(Pos.CENTER);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        hb.getChildren().add(title);
        dialogVbox.getChildren().add(hb);
        
        hb = new HBox(5);
        hb.setAlignment(Pos.CENTER);
        Label partNameL = new Label("Part Name: ");
        hb.getChildren().addAll(partNameL, partNameTF);
        dialogVbox.getChildren().add(hb);
       
        hb = new HBox(8);
        hb.setAlignment(Pos.CENTER);
        Label priceL = new Label("Price: ");
        hb.getChildren().addAll(priceL,partPriceTF);
        dialogVbox.getChildren().addAll(hb);
        
     
        
       
        hb = new HBox(8);
        hb.setAlignment(Pos.CENTER);
        Label installL = new Label("Install Date: ");
        hb.getChildren().addAll(installL, partInstallTF);
        dialogVbox.getChildren().addAll(hb);
       
        hb = new HBox(8);
        Label warrantyL = new Label("Warrenty End Date: ");
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(warrantyL,partWarrantyTF);
        dialogVbox.getChildren().add(hb);
        
        hb = new HBox(8);
        hb.setAlignment(Pos.CENTER);
        Label locationL = new Label("Location: ");
        warning = new Label("");
        hb.getChildren().addAll(locationL, partLocationTF,warning);
        dialogVbox.getChildren().add(hb);
        
         //List of Controls
       
        //BUTTONS
        hb = new HBox(30);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(add,cancel);
        dialogVbox.getChildren().add(hb);
   
        //Set the scene
        Scene dialogScene = new Scene(dialogVbox, 600, 450);
        popUp.setTitle("Add Part");
        popUp.setScene(dialogScene);
        popUp.setResizable(false);
        popUp.show();      
    }

    public void setTableOneSettings(){

    }

    public void setTableTwoSettings(){

    }
    
    
    
    
}
