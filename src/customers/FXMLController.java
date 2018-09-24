/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers;
import common.*;
import javafx.stage.Modality;
import vehicles.*;
import bookings.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kisekkaDavid
 */
public class FXMLController implements Initializable {

    public Database connectionModel = new Database();
    
    @FXML Label isConnected;
    @FXML Label labelName;
    @FXML Label labelID;
    
    Stage addStage = new Stage();
    Stage editStage = new Stage();
    
    @FXML
    private TableView<Customer> tableCustomer;
    @FXML
    private TableColumn<Customer, String> columnCustomerID;
    @FXML
    private TableColumn<Customer, String> columnType;
    @FXML
    private TableColumn<Customer, String> columnFirstName;
    @FXML
    private TableColumn<Customer, String> columnLastName;
    @FXML
    private TableColumn<Customer, String> columnAddress;
    @FXML
    private TableColumn<Customer, String> columnPostcode;
    @FXML
    private TableColumn<Customer, String> columnPhone;
    @FXML
    private TableColumn<Customer, String> columnEmailAddress;
    
    @FXML private TableView<Vehicle> tableVehicle;    
    @FXML TableColumn<Vehicle, String> columnVOwnerID, columnVRegNum, columnVType, columnVModel, columnVMake, columnVEngineSize, columnVFuelType, columnVMOT, columnVColour, columnVWarranty, columnVCName, columnVCAddress, columnVExpirationDate;
    @FXML private TableView<Booking> tableBooking;
    @FXML TableColumn<Booking, String> columnBID, columnBType, columnBRegNum, columnBDate, columnBTime, columnBMileage, columnBLastService, columnBMechanic, columnBCost;
    
    @FXML
    private Button refreshBtn,logOut;
    @FXML
    private TextField searchField; 
    
    private ObservableList<Customer> data;
    private ObservableList<Vehicle> vehicleData;
    private ObservableList<Booking> bookingData;
    
    public SqliteConnection dc;
    public Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(connectionModel.isDbConnected()){
            isConnected.setText("Connected");
        }
        else {
            isConnected.setText("Not Connected");
        }
        columnCustomerID.setCellValueFactory(c -> c.getValue().getCustomerIDProperty());
        columnType.setCellValueFactory(c -> c.getValue().getTypeProperty());
        columnFirstName.setCellValueFactory(c -> c.getValue().getFirstNameProperty());
        columnLastName.setCellValueFactory(c -> c.getValue().getLastNameProperty());
        columnAddress.setCellValueFactory(c -> c.getValue().getAddressProperty());
        columnPostcode.setCellValueFactory(c -> c.getValue().getPostcodeProperty());
        columnPhone.setCellValueFactory(c -> c.getValue().getPhoneProperty());
        columnEmailAddress.setCellValueFactory(c -> c.getValue().getEmailAddressProperty());
        
        dc = new SqliteConnection();
        loadData();

        tableCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            Customer c = newSelection;
            if(c != null) {
                labelName.setText(c.getFirstName() + " " + c.getLastName());
                labelID.setText(c.getCustomerID());
                labelName.setVisible(true);
                labelID.setVisible(true);
            
                loadVehicles(c.getCustomerID());
                loadBookings(c.getCustomerID());
                //loadParts(c.getCustomerID());
               //loadSpecialistRepairs(c.getCustomerID());
            }
    });
        
    }
    
    @FXML
    private void loadData(){
        try {
            conn = dc.Connector();
            data = FXCollections.observableArrayList();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM customers;");
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String customerID = rs.getString("customer_id");
                String type = rs.getString("type");
                String firstName = rs.getString("name");
                String lastName = rs.getString("surname");
                String address = rs.getString("address");
                String postcode = rs.getString("postcode");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                
                Customer c = new Customer(customerID, type, firstName, lastName, address, postcode, phone, email);
                data.add(c);
            }

        FilteredList<Customer> filteredData = new FilteredList<>(data, c -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } 
                
                String matchCase = newValue.toLowerCase();
                
                if(customer.getCustomerID().toLowerCase().contains(matchCase)) {
                    return true;
                }
                else if (customer.getFirstName().toLowerCase().contains(matchCase)) {
                    return true;
                }
                else if (customer.getLastName().toLowerCase().contains(matchCase)) {
                    return true;
                }
                return false;
            });      
        });
        
        tableCustomer.setItems(filteredData);
            
        } 
        catch (SQLException ex) {
            System.err.println("error: " + ex);
        }
    }
    
    @FXML
    private void handleAddCustomer(ActionEvent event) throws IOException{
        try {
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addFXML.fxml"));     
            loader.setController(new addController());
            
            root = loader.load();
            addStage.setScene(new Scene(root));
            addStage.show(); 
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleEditCustomer(ActionEvent event) throws IOException {
        try {
            Customer c = tableCustomer.getSelectionModel().getSelectedItem();
            if(c == null){
                return;
            }
            
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editFXML.fxml"));     
            loader.setController(new editController());
            root = loader.load();
            
            editController controller = loader.<editController>getController();
            controller.fillFields(c);
            
            editStage.setScene(new Scene(root));
            editStage.show();    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleDeleteCustomer(ActionEvent event) throws Exception {
        Customer c = tableCustomer.getSelectionModel().getSelectedItem();
        if(c == null){
            return;
        }     
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?\n" + c.getFirstName() + " " + c.getLastName(), ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                PreparedStatement pst = conn.prepareStatement("DELETE FROM customers WHERE customer_id=?");
                pst.setString(1, c.getCustomerID());
                pst.executeUpdate();
                
                pst = conn.prepareStatement("SELECT vehicle_reg_num FROM vehicles WHERE customer_id=?");
                pst.setString(1, c.getCustomerID());
                
                PreparedStatement pst2 = null;
                ResultSet rs = pst.executeQuery();
                
                while(rs.next()){
                    String regNum = rs.getString("vehicle_reg_num");
                    pst2 = conn.prepareStatement("DELETE FROM vehicles WHERE vehicle_reg_num=?");
                    pst2.setString(1, regNum);
                    pst2.executeUpdate();
                
                    pst2 = conn.prepareStatement("DELETE FROM parts WHERE vehicle_reg_num=?");
                    pst2.setString(1, regNum);
                    pst2.executeUpdate();
                
                    pst2 = conn.prepareStatement("DELETE FROM bookings WHERE vehicle_reg_num=?");
                    pst2.setString(1, regNum);
                    pst2.executeUpdate();
               
                    pst2 = conn.prepareStatement("DELETE FROM specialist_repairs WHERE vehicle_reg_num=?");
                    pst2.setString(1, regNum);
                    pst2.executeUpdate();
                }
                
            }
            catch(SQLException e){
                
            }

        }
        else if (alert.getResult() == ButtonType.NO){
            alert.close();
        }       

        
    }

    
    @FXML
    private void loadVehicles(String id) {
        try {
            columnVRegNum.setCellValueFactory(v -> v.getValue().getRegistrationNumberProperty());
            columnVType.setCellValueFactory(v -> v.getValue().getTypeProperty());
            columnVModel.setCellValueFactory(v -> v.getValue().getModelProperty());
            columnVMake.setCellValueFactory(v -> v.getValue().getMakeProperty());
            columnVEngineSize.setCellValueFactory(v -> v.getValue().getEngineSizeProperty());
            columnVFuelType.setCellValueFactory(v -> v.getValue().getFuelTypeProperty());
            columnVMOT.setCellValueFactory(v -> v.getValue().getRenewalDateProperty());
            columnVColour.setCellValueFactory(v -> v.getValue().getColourProperty());
            columnVWarranty.setCellValueFactory(v -> v.getValue().getIsUnderWarrantyProperty());
            columnVCName.setCellValueFactory(v -> v.getValue().getCompanyNameProperty());
            columnVCAddress.setCellValueFactory(v -> v.getValue().getCompanyAddressProperty());
            columnVExpirationDate.setCellValueFactory(v -> v.getValue().getExpirationDateProperty());            
            
            String owner = null;
            String regNum = null;
            String vType = null;
            String model = null;
            String make = null;
            String engineSize = null;
            String fuelType = null;
            String colour = null;
            String mot = null;
            String warranty = null;
            String cName = null;
            String cAddress = null;
            String expirationDate = null;
            
            conn = dc.Connector();
            vehicleData = FXCollections.observableArrayList();
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM vehicles where customer_id=?;");
            pst.setString(1, id);
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                owner = rs.getString("customer");
                regNum = rs.getString("vehicle_reg_num");
                vType = rs.getString("type");
                model = rs.getString("model");
                make = rs.getString("make");
                engineSize = rs.getString("engsize");
                fuelType = rs.getString("fueltype");
                colour = rs.getString("colour");
                mot = rs.getString("mot");
                warranty = rs.getString("warranty");
                cName = rs.getString("companyname");
                cAddress = rs.getString("companyaddress");
                expirationDate = rs.getString("expirationdate");
                
                Vehicle v = new Vehicle(owner, id, regNum, vType, model, make, engineSize, fuelType, colour, mot, warranty, cName, cAddress, expirationDate);
                vehicleData.add(v);
            }
            tableVehicle.getItems().setAll(vehicleData);
            
        } catch (SQLException ex) {
            System.err.println("error: " + ex);
        }
    }
        
    @FXML
    private void loadBookings(String id) {
        try {
            columnBID.setCellValueFactory(b -> b.getValue().getBookingNumProperty());
            columnBType.setCellValueFactory(b -> b.getValue().getBookingTypeProperty());
            columnBRegNum.setCellValueFactory(b -> b.getValue(). getRegistrationNumberProperty());
            columnBDate.setCellValueFactory(b -> b.getValue().getDateProperty());
            columnBTime.setCellValueFactory(b -> b.getValue().getTimeProperty());
            columnBMileage.setCellValueFactory(b -> b.getValue().getMileageProperty());
            columnBLastService.setCellValueFactory(b -> b.getValue().getLastServicedDateProperty());     
            columnBMechanic.setCellValueFactory(b -> b.getValue().getMechanicProperty());
            columnBCost.setCellValueFactory(b -> b.getValue().getCostProperty()); 
            
            String bID = null;
            String bType = null;
            String bRegNum = null;
            String customer = null;
            String date = null;
            String time = null;
            String mileage = null;
            String service = null;
            String mechanic = null;
            String cost = null;

            
            conn = dc.Connector();
            bookingData = FXCollections.observableArrayList();
            PreparedStatement pst = conn.prepareStatement("SELECT vehicle_reg_num FROM vehicles WHERE customer_id=?;");
            pst.setString(1, id);
            PreparedStatement pst2 = null;
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pst2 = conn.prepareStatement("SELECT * FROM bookings WHERE vehicle_reg=?");
                pst2.setString(1, rs.getString("vehicle_reg_num"));
                ResultSet rs2 = pst2.executeQuery();
                while(rs2.next()){
                    bID = rs2.getString("booking_id");
                    bType = rs2.getString("booking_type");
                    bRegNum = rs2.getString("vehicle_reg");
                    customer= rs2.getString("customer");
                    date = rs2.getString("date");
                    time = rs2.getString("time");
                    mileage = rs2.getString("current_mileage");
                    service = rs2.getString("last_service_date");
                    mechanic = rs2.getString("mechanics");
                    cost = rs2.getString("total_cost");
                
                Booking b = new Booking(bID,bType,bRegNum,mechanic,customer,date,time,service,mileage,cost);
                bookingData.add(b);
                }
            }
            tableBooking.getItems().setAll(bookingData);   
        }
        catch (SQLException ex) {
            System.err.println("error: " + ex);
        }
    }

    @FXML
    public void logout(){
        //GO TO AUTHENTICATION PAGE
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/common/Authenticator.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();

            Stage current = (Stage) logOut.getScene().getWindow();
            current.close();
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
}



    
