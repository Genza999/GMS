package specialists;

import java.net.URL;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.*;
import specialists.SpecialistRepair.RepairType;
import javafx.scene.paint.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.sql.DriverManager;   
import java.sql.Statement;  
import org.sqlite.SQLiteConnection;


public class SpecialistRepairController implements Initializable {
    String dbUrl = "jdbc:sqlite:test.db";
	
	private ObservableList<RepairCentre> spcList = FXCollections.observableArrayList();
    private ObservableList<PartSpecialRepair> partRepairList = FXCollections.observableArrayList();
    private ObservableList<VehicleSpecialRepair> vehicleRepairList = FXCollections.observableArrayList();
    
    //Stage for popup window
    public Stage dialog;
    @FXML final private ArrayList<Label> labels = new ArrayList<>();
    @FXML final private ArrayList<Control> controls = new ArrayList<>();
    
    //FXML Variables for RepairCentre	
    @FXML private TableView<RepairCentre> spcTable; 
    @FXML private TableColumn<RepairCentre,String> spcName,spcAddress,spcPhone,spcEmail;
    @FXML private Button addSPC,editSPC,deleteSPC;
    TextField spcNameFiled;
    Label spcNameLabel;
    TextField spcAddressFiled;
    Label spcAddressLabel;
    Label spcPhoneLabel;
    TextField spcPhoneFiled;
    Label spcEmailLabel;
    TextField spcEmailFiled;
    Label spctotalNumLabel;
    TextField spctotalNumFiled;
    TextField spctotalCostFiled;
    Label spctotalCostLabel;
    
    ////FXML Variables for RepairCentre
    
    
    //Buttons
    Button add;
    Button cancel;
    
    //for SpecialistRepair
    TextField deliveryDateFiled;
    TextField returnDateFiled;
    TextField idFiled;
    TextField costFiled;
    TextField partNameFiled;
    TextField partDescriptionFiled;
    TextField partIdNumFiled;
    TextField spcName1;
    

    Label deliveryDateLabel;
    Label returnDateLabel;
    Label idFiledLabel;
    Label costLabel;
    Label repairTypeLabel;
    ChoiceBox<String> repairTypeBox;
    
    //FXML Variables for PartSpecialRepair	
    @FXML private TableView<PartSpecialRepair> partRepairTable; 
    @FXML private TableColumn<PartSpecialRepair,String> pDeliveryDate,pReturnDate,pId,pSpcName,pPrice,partName,partDescription,partIdNum;
    

    
    Label partNameLabel;   
    Label partDescriptionLabel;
    Label partIdNumLabel;
    
    //FXML Variables for VehicleSpecialRepair	vRenewDate,vLastServiceDate,vCurMileage
    @FXML private TableView<VehicleSpecialRepair> vehicleRepairTable; 
    @FXML private TableColumn<VehicleSpecialRepair,String> vDeliveryDate,vReturnDate,vId,vSpcName,vPrice,vRegNum;//,vModel,vMake,vEngSize,vFuelType,vColor,vRenewDate,vLastServiceDate,vCurMileage;
    Label vRegNumLabel;
    TextField vRegNumField;
   /* Label vModelLabel;
    Label vMakerLabel;
    Label vEngSizeLabel;
    Label vFuelTypeLabel;
    Label vColorLabel;
    Label vRenewDateLabel;
    Label vLastServiceDateLabel;
    Label vRCurMileageLabel;
    TextField vRegNumField;
    TextField vRegistrationNumberField;
    TextField vMakeField;
    TextField vEngSizeField;
    TextField vFuelTypeField;
    TextField vColorField;
    TextField vRenewDateField;
    TextField vLastServiceDateField;
    TextField vRCurMileageField;*/

    
    @FXML private Button addPartRepair,editPartRepair,deletePartRepair;

    @FXML private Label test,title;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//connect db to do initialization
        /*try{
        	Connection conn = DriverManager.getConnection(dbUrl);
        }
        catch(Exception e){
            System.out.println(e);
        }*/
    	
    	//use hard code for test
    	RepairCentre tempSpc = new RepairCentre("US","NY", "100000000", "123@gmail.com");
    	spcList.add(tempSpc);
    	spcTable.getItems().setAll(spcList);
    	
    	//String dDate,String rDate, String id, RepairType repairType, double price, String spcName
    	PartSpecialRepair tempPartRepair = new PartSpecialRepair("2/1/2017","2/27/2017","001",3000,"Engine","broken", "99999", "US");
    	partRepairList.add(tempPartRepair);
    	partRepairTable.getItems().setAll(partRepairList);
    	
    	//String o,String regNum,String t,String mod,String mk,String engSize,String fuelTp,String col,String mot,String iuw,String cn,String ca,String ed
    	//Vehicle tempVehicle = new Vehicle("Jame","001","xx","xx","Honda","Civic 1.6","Litre", "SE","Blue","mot","ss","","");
    	VehicleSpecialRepair tempVehicleRepair = new VehicleSpecialRepair("2/1/2017","2/27/2017","001",3000,"0000001","US");
    	vehicleRepairList.add(tempVehicleRepair);
    	vehicleRepairTable.getItems().setAll(vehicleRepairList);
    	
    	//for SPC
    	spcName.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getName()));
    	spcAddress.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getAddress()));
    	spcPhone.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getPhone()));
    	spcEmail.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getEmail()));

       	// for partRepair
    	partName.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getName()));
    	partDescription.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getDescription()));
    	partIdNum.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getIdNumber()));
    	pDeliveryDate.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getDeliveryDate()));
    	pReturnDate.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getReturnDate()));
    	pId.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getID()));
    	pSpcName.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getSPCName()));
        pPrice.setCellValueFactory(v -> new SimpleStringProperty(Double.toString(v.getValue().getPrice())));

        // for VehicleRepair
        vDeliveryDate.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getDeliveryDate()));
        vReturnDate.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getReturnDate()));
        vId.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getID()));
        vSpcName.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getSPCName()));
        vPrice.setCellValueFactory(v -> new SimpleStringProperty(Double.toString(v.getValue().getPrice())));
        vRegNum.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getVehicleRegNum()));
    }
    
    public boolean fieldsCheck(){
        int counter = 0;
        int checkCounter = 0;
      /*  for(Control c : controls){
            if((c instanceof ChoiceBox && ((ChoiceBox)c).getValue()==null && !c.isDisabled()) || (c instanceof DatePicker && ((DatePicker)c).getValue()==null && !c.isDisabled()) || (c instanceof TextField && ((TextField)c).getText().equals("")) && !c.isDisabled()){
                labels.get(counter).setTextFill(Color.web("#FF0000"));
                counter++;
                checkCounter++;
               
            }
            else{
                labels.get(counter).setTextFill(Color.web("#000000"));
                counter++;
            }
        }*/
        return checkCounter==0;
    }
    
    private boolean checkSpcName() throws SQLException{
     for(int i=0; i<spcList.size(); i++){
            if(spcList.get(i).getName().equals(spcNameFiled.getText())){
                return false;
            }
        }
     return true;
    }
    
    @FXML
    private void addSpc() throws IOException{
        //Create the pop-up form to add a new vehicle
        try{
            createPopup("Add Spc","Add", null);
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
        //Add button
        add.setOnAction((ActionEvent e) -> {
            if(fieldsCheck()) {
                PreparedStatement s = null;
                ResultSet rs = null;    
                try {
                    if(checkSpcName()){
                    	RepairCentre tempSpc = new RepairCentre(spcNameFiled.getText(),spcAddressFiled.getText(), spcPhoneFiled.getText(), spcEmailFiled.getText());
                    	spcList.add(tempSpc);
                               
                        dialog.close();
                        spcTable.getItems().setAll(spcList);
                    }
                    else{
                    	spcNameFiled.setText(spcNameFiled.getText()+" already exists");
                    }
                }catch(SQLException err) {
                    System.out.println(err);
                } finally {
                    if(s != null) {
                        try {
                            s.close();
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        });
    }
    
    @FXML
    private void editSpc(ActionEvent event) throws IOException, SQLException {
        RepairCentre spc = spcTable.getSelectionModel().getSelectedItem();
        if(spc == null){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Edit Confirmation");
            alert.setHeaderText("Edit Confirmation");
            alert.setContentText("No Spc is selected?");

            Optional<ButtonType> result = alert.showAndWait();
            alert.close();
            return;
        }
        
        try{
            createPopup("Edit Spc","Edit", spc);
        }
        catch(SQLException e){
            System.out.println(e);
        }  

        //UPDATE ROW ON DATABASE
        add.setOnAction((ActionEvent e) -> {
            PreparedStatement s = null;
            if (fieldsCheck()) {
                try {
                	RepairCentre tempSpc = new RepairCentre(spcNameFiled.getText(),spcAddressFiled.getText(), spcPhoneFiled.getText(), spcEmailFiled.getText());
                	boolean isFound = false;
                	for(int i=0; i<spcList.size(); i++){
                		if(spcList.get(i).getName().equals(tempSpc.getName())){
                			isFound = true;
                		  spcList.set(i, tempSpc);
                		}
                	}
                	if(!isFound){
                	  spcList.add(tempSpc);
                	}
                           
                    dialog.close();
                    spcTable.getItems().setAll(spcList);
                    
                    //Update row in database (users can't change registration number because is primary key

                } finally {
                    if(s != null) {
                        try {
                            s.close();
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                    }
                }
                //dialog.close();
                //spcTable.refresh();
                //try{
                    //buildData("");
                //}
                //catch(SQLException exe){
                  //  System.out.println(exe);
                //} 
            }
        });
        
    }
    
    @FXML
    private void deleteSpc(ActionEvent event) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this spc?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
        	RepairCentre spc = spcTable.getSelectionModel().getSelectedItem();
            spcTable.getItems().remove(spc);
            spcList.remove(spc);
            
            //Remove from database

        } 
        else {
            alert.close();
        }
    }
    
    public void createPopup(String operation,String buttonText, RepairCentre spc) throws SQLException{
        //Nodes
        HBox hb = new HBox();
        
        //Controls
        //Label title = new Label(operation + " Vehicle");
        spcNameFiled = new TextField();
        Label spcNameLabel = new Label("Name:");
        spcAddressFiled = new TextField();
        Label spcAddressLabel= new Label("Address:");
        Label spcPhoneLabel = new Label("Phone:");
        spcPhoneFiled = new TextField();
        Label spcEmailLabel = new Label("Email:");
        spcEmailFiled = new TextField();

        if(spc!=null){
            spcNameFiled.setText(spc.getName());
            spcAddressFiled.setText(spc.getAddress());
            spcPhoneFiled.setText(spc.getPhone()); //To avoid collisions in database
            spcEmailFiled.setText(spc.getEmail());
        }
        	
        //Buttons
        add = new Button();
        cancel = new Button();
        
        //Setting up - Button appearances
        add.setPrefSize(120, 25);
        add.setText(buttonText);
        cancel.setPrefSize(120,25);
        cancel.setText("Cancel");

        //Setting up - Stage for popup
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(15);
        dialogVbox.setPadding(new Insets(10,10,10,20));
        

        hb = new HBox(8);
        hb.getChildren().addAll(spcNameLabel, spcNameFiled);
        dialogVbox.getChildren().addAll(hb);
        hb = new HBox(8);
        hb.getChildren().addAll(spcAddressLabel, spcAddressFiled);
        dialogVbox.getChildren().addAll(hb);
        hb = new HBox(8);
        hb.getChildren().addAll(spcPhoneLabel, spcPhoneFiled);
        dialogVbox.getChildren().addAll(hb);
        hb = new HBox(8);
        hb.getChildren().addAll(spcEmailLabel, spcEmailFiled);
        dialogVbox.getChildren().addAll(hb);
       
        
        //List of Controls
        controls.add(spcNameFiled);controls.add(spcAddressFiled);controls.add(spcPhoneFiled);controls.add(spcEmailFiled);
        
        //List of Labels
        labels.add(spcNameLabel);labels.add(spcAddressLabel);labels.add(spcPhoneLabel);labels.add(spcEmailLabel);
        
        //BUTTONS
        hb = new HBox(30);
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.getChildren().addAll(cancel,add);
        dialogVbox.getChildren().add(hb);
        cancel.setOnAction(event -> dialog.close());
        
        //Set the scene
        Scene dialogScene = new Scene(dialogVbox, 700, 645);
        dialog.setTitle(operation);
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();   
    }

    @FXML
    private void addSpecialistRepair() throws IOException{
        //Create the pop-up form to add a new vehicle
        try{
            createSpecialistRepairPopup("Add SpecialistRepair","Add", null, null);
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
    	
    	//String o,String regNum,String t,String mod,String mk,String engSize,String fuelTp,String col,String mot,String iuw,String cn,String ca,String ed
    	//Vehicle tempVehicle = new Vehicle("Jame","001","xx","xx","Honda","Civic 1.6","Litre", "SE","Blue","mot","ss","","");

        //Add button
        add.setOnAction((ActionEvent e) -> {
            if(fieldsCheck()) {
                PreparedStatement s = null;
                ResultSet rs = null;    
                try {
                    if(costFiled != null){
                    	if(repairTypeBox.getValue().equals("Vehicle")){
                        	VehicleSpecialRepair tempVehicleRepair = new VehicleSpecialRepair(deliveryDateFiled.getText(),returnDateFiled.getText(),idFiled.getText(),
                        			Double.valueOf(costFiled.getText()),vRegNumField.getText(),spcNameFiled.getText());
                        	vehicleRepairList.add(tempVehicleRepair);
                            dialog.close();
                        	vehicleRepairTable.getItems().setAll(vehicleRepairList);
                    	} else {
                        	PartSpecialRepair tempPartRepair = new PartSpecialRepair(deliveryDateFiled.getText(),returnDateFiled.getText(),idFiled.getText(),
                        			Double.valueOf(costFiled.getText()),partNameFiled.getText(), partDescriptionFiled.getText(),partIdNumFiled.getText(),spcNameFiled.getText());
                        	partRepairList.add(tempPartRepair);
                            dialog.close();
                        	partRepairTable.getItems().setAll(partRepairList);
                    	}


                    }
                    else{
                    costFiled.setText(costFiled.getText()+" shouldnt have the currency symbol");
                    }
                } finally {
                    if(s != null) {
                        try {
                            s.close();
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        });
    }
    
    @FXML
    private void editSpecialistRepair(ActionEvent event) throws IOException, SQLException {
    	PartSpecialRepair curPartRepair = partRepairTable.getSelectionModel().getSelectedItem();
    	VehicleSpecialRepair curVehicleRepair = vehicleRepairTable.getSelectionModel().getSelectedItem();
        if(curPartRepair == null && curVehicleRepair==null){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Edit Confirmation");
            alert.setHeaderText("Edit Confirmation");
            alert.setContentText("No Spc is selected?");

            Optional<ButtonType> result = alert.showAndWait();
            alert.close();
            return;
        }
        
        try{
            createSpecialistRepairPopup("Edit SpecialistRepair","Edit", curVehicleRepair, curPartRepair);
        }
        catch(SQLException e){
            System.out.println(e);
        }  

        //UPDATE ROW ON DATABASE
        add.setOnAction((ActionEvent e) -> {
            PreparedStatement s = null;
            if (fieldsCheck()) {
                try {
                	if(repairTypeBox.getValue().equals("Vehicle")){
                    	VehicleSpecialRepair tempVehicleRepair = new VehicleSpecialRepair(deliveryDateFiled.getText(),returnDateFiled.getText(),idFiled.getText(),
                    			Double.valueOf(costFiled.getText()),vRegNumField.getText(),spcNameFiled.getText());
                    	boolean isFound = false;
                    	for(int i=0; i<vehicleRepairList.size(); i++){
                    		if(vehicleRepairList.get(i).getID().equals(tempVehicleRepair.getID())){
                    			isFound = true;
                    			vehicleRepairList.set(i, tempVehicleRepair);
                    		}
                    	}
                    	if(!isFound){
                    		vehicleRepairList.add(tempVehicleRepair);
                    	}
                        dialog.close();
                    	vehicleRepairTable.getItems().setAll(vehicleRepairList);
                	} else {
                    	PartSpecialRepair tempPartRepair = new PartSpecialRepair(deliveryDateFiled.getText(),returnDateFiled.getText(),idFiled.getText(),
                    			Double.valueOf(costFiled.getText()),partNameFiled.getText(), partDescriptionFiled.getText(),partIdNumFiled.getText(),spcNameFiled.getText());
                    	boolean isFound = false;
                    	for(int i=0; i<partRepairList.size(); i++){
                    		if(partRepairList.get(i).getID().equals(tempPartRepair.getID())){
                    			isFound = true;
                    			partRepairList.set(i, tempPartRepair);
                    		}
                    	}
                    	if(!isFound){
                    		partRepairList.add(tempPartRepair);
                    	}

                        dialog.close();
                    	partRepairTable.getItems().setAll(partRepairList);
                	}
                	
//
//                    dialog.close();
//                    spcTable.getItems().setAll(spcList);
                    
                    //Update row in database (users can't change registration number because is primary key

                } finally {
                    if(s != null) {
                        try {
                            s.close();
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        }
                    }
                }
                //dialog.close();
                //spcTable.refresh();
                //try{
                    //buildData("");
                //}
                //catch(SQLException exe){
                  //  System.out.println(exe);
                //} 
            }
        });
        
    }
    
    @FXML
    private void deleteSpecialistRepair(ActionEvent event) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this spc?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
        	PartSpecialRepair curPartRepair = partRepairTable.getSelectionModel().getSelectedItem();
        	VehicleSpecialRepair curVehicleRepair = vehicleRepairTable.getSelectionModel().getSelectedItem();
        	if(curPartRepair != null){
                partRepairTable.getItems().remove(curPartRepair);
                partRepairList.remove(curPartRepair);

        	}
        	if(curVehicleRepair != null){
                vehicleRepairTable.getItems().remove(curVehicleRepair);
            	vehicleRepairList.remove(curVehicleRepair);

        	}
            
            //Remove from database

        } 
        else {
            alert.close();
        }
    }
    
    public void createSpecialistRepairPopup(String operation,String buttonText, VehicleSpecialRepair vehicleRepair, PartSpecialRepair partRepair) throws SQLException{
    	  //Nodes
        HBox hb = new HBox();
        
        //Controls
        deliveryDateFiled = new TextField(); 
        returnDateFiled = new TextField();
        idFiled = new TextField();
        spcNameFiled = new TextField();
        costFiled = new TextField();
        repairTypeBox = new ChoiceBox<>();
        
        
        //TextField spcName;
        repairTypeLabel = new Label("Repair Type:");
        deliveryDateLabel = new Label("Delivery Date:");
        returnDateLabel = new Label("Return Date:");
        idFiledLabel = new Label("ID:");
        spcNameLabel = new Label("Spc Name:");
        costLabel = new Label("Price");

        
        //for part
        partNameFiled = new TextField();
        partDescriptionFiled = new TextField(); 
        partIdNumFiled = new TextField(); 
        
        partNameLabel = new Label("Part Name:");   
        partDescriptionLabel = new Label("Part Desc:");
        partIdNumLabel = new Label("Part Number:");
        
        //for vehicle   
        vRegNumLabel = new Label("Reg Num:"); 
        vRegNumField = new TextField(); 


       /* Label vModelLabel = new Label("Model:"); 
        Label vMakerLabel = new Label("Maker:"); 
        Label vEngSizeLabel = new Label("Engine Size:"); 
        Label vFuelTypeLabel = new Label("Fuel Type:"); 
        Label vColorLabel = new Label("Color:"); 
        Label vRenewDateLabel = new Label("Renew Date:"); 
        Label vLastServiceDateLabel = new Label("Last Service Date:"); 
        Label vRCurMileageLabel = new Label("Current Mileage:"); 
        TextField vRegNumField = new TextField(); 
        TextField vRegistrationNumberField = new TextField(); 
        TextField vMakeField = new TextField(); 
        TextField vEngSizeField = new TextField(); 
        TextField vFuelTypeField = new TextField(); 
        TextField vColorField = new TextField(); 
        TextField vRenewDateField = new TextField(); 
        TextField vLastServiceDateField = new TextField(); 
        TextField vRCurMileageField = new TextField(); */

        if(vehicleRepair!=null){
            deliveryDateFiled.setText(vehicleRepair.getDeliveryDate()); 
            returnDateFiled.setText(vehicleRepair.getReturnDate()); 
            idFiled.setText(vehicleRepair.getID()); 
            spcNameFiled.setText(vehicleRepair.getSPCName()); 
            vRegNumField.setText(vehicleRepair.getVehicleRegNum()); 
            costFiled.setText(Double.toString(vehicleRepair.getPrice()));
            
        } else if(partRepair != null){
            deliveryDateFiled.setText(partRepair.getDeliveryDate()); 
            returnDateFiled.setText(partRepair.getReturnDate()); 
            idFiled.setText(partRepair.getID()); 
            spcNameFiled.setText(partRepair.getSPCName()); 
            costFiled.setText(Double.toString(partRepair.getPrice()));
           
            returnDateFiled.setText(partRepair.getReturnDate()); 
            idFiled.setText(partRepair.getID()); 
            spcNameFiled.setText(partRepair.getSPCName()); 
        }
        	
        //Buttons
        add = new Button();
        cancel = new Button();
        
        //Setting up - Button appearances
        add.setPrefSize(120, 25);
        add.setText(buttonText);
        cancel.setPrefSize(120,25);
        cancel.setText("Cancel");

        //Setting up - Stage for popup
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(15);
        dialogVbox.setPadding(new Insets(10,10,10,20));
        
        
        //TextField spcName;
        repairTypeLabel = new Label("Repair Type:");
        ObservableList<String> tp = FXCollections.observableArrayList("Vehicle", "Part");
        repairTypeBox = new ChoiceBox<>(tp);
        hb.getChildren().addAll(repairTypeLabel, repairTypeBox);
        dialogVbox.getChildren().addAll(hb);
        repairTypeBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
          if(newValue.equals("Vehicle")){
        	  vRegNumField.setVisible(true);
              partNameFiled.setVisible(false);
              partDescriptionFiled.setVisible(false); 
              partIdNumFiled.setVisible(false); 
          } else {
        	  vRegNumField.setVisible(false);
              partNameFiled.setVisible(true);
              partDescriptionFiled.setVisible(true); 
              partIdNumFiled.setVisible(true);   
          }
        });
        
        hb = new HBox(8);
        hb.getChildren().addAll(deliveryDateLabel, deliveryDateFiled);
        dialogVbox.getChildren().addAll(hb);
        hb = new HBox(8);
        hb.getChildren().addAll(returnDateLabel, returnDateFiled);
        dialogVbox.getChildren().addAll(hb);
        hb = new HBox(8);
        hb.getChildren().addAll(idFiledLabel, idFiled);
        dialogVbox.getChildren().addAll(hb);
        hb = new HBox(8);
        hb.getChildren().addAll(spcNameLabel, spcNameFiled);
        dialogVbox.getChildren().addAll(hb);

        hb = new HBox(8);
        hb.getChildren().addAll(costLabel, costFiled);
        dialogVbox.getChildren().addAll(hb);
        
        //for part
        hb = new HBox(8);
        hb.getChildren().addAll(partNameLabel, partNameFiled);
        dialogVbox.getChildren().addAll(hb);
        
        hb = new HBox(8);
        hb.getChildren().addAll(partDescriptionLabel, partDescriptionFiled);
        dialogVbox.getChildren().addAll(hb);
        
        hb = new HBox(8);
        hb.getChildren().addAll(partIdNumLabel, partIdNumFiled);
        dialogVbox.getChildren().addAll(hb);
        
        
        //for vehicle   
        hb = new HBox(8);
        hb.getChildren().addAll(vRegNumLabel, vRegNumField);
        dialogVbox.getChildren().addAll(hb);
        
        //List of Controls
        //controls.add(spcNameFiled);controls.add(spcAddressFiled);controls.add(spcPhoneFiled);controls.add(spcEmailFiled);
        
        //List of Labels
        //labels.add(spcNameLabel);labels.add(spcAddressLabel);labels.add(spcPhoneLabel);labels.add(spcEmailLabel);
        
        //BUTTONS
        hb = new HBox(30);
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.getChildren().addAll(cancel,add);
        dialogVbox.getChildren().add(hb);
        cancel.setOnAction(event -> dialog.close());
        
        //Set the scene
        Scene dialogScene = new Scene(dialogVbox, 700, 645);
        dialog.setTitle(operation);
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();   
    }
}
