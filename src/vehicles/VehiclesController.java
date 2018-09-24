package vehicles;

/**
 *
 * @author kisekkaDavid
 */

import common.*;
import bookings.Booking;

import java.net.URL;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.paint.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class VehiclesController implements Initializable {
    //Variables for database connection
    public Database conn = new Database();
    public Connection c;

    //Stage for popup window
    public Stage dialog;

    //Observable list contains vehicle objects for table view
    private ObservableList<Vehicle> vehicles; //= FXCollections.observableArrayList();
    private final ObservableList<String> temps = FXCollections.observableArrayList("Honda Civic 1.6 Litre SE","Ford Focus 1.2 Litre","Audi A3 1.0 TFSI","Volkswagen Polo 1.0 SE","Hyundai Tucson 2.0 SE","Audi A1 1.2 TFSI","Audi Q3 2.0 TFSI","Audi S6 5.2 FSI","Subaru Legacy 2.5 Gas","Honda Accord 2.4 Gas","BMW M6 4.4","Fiat 500 1.2 Diesel","Opel Astra 1.5 Diesel","BMW S500 2.0 Petrol","Volkswagen Up 1.5 Diesel","Opel Meriva 1.5 Gas","Fiat Multipla 2.0 Petrol","Fiat Panda 1.5 Diesel","Peugeot 206 1.5 Diesel","Peugeot 1007 2.0 Gas","Renault R4 1.5 Diesel","Renault Clio 1.5 Gas","Ford Explorer 2.0 Petrol","Hyundai Elantra 1.5 Diesel","Skoda Odavia 1.5 Gas","Renault Megane 2.0 Petrol","Opel Insignis 1.5 Diesel","Nissan Qashqai 2.0 Petrol","Toyota Avensis 1.2 Gas","Fiat Bravo 1.5 Diesel");
    //private ObservableList<Part> parts; //= FXCollections.observableArrayList();
    private ObservableList<Booking> bookings; //= FXCollections.observableArrayList();

    //FXML Variables for Vehicles
    @FXML private TableView<Vehicle> vehiclesTable;
    //@FXML private TableView<Part> partsTable
    @FXML private TableView<Booking> bookingsTable;
    @FXML private TableColumn<Vehicle,String> customer,customer_id,regNum,type,make,model,engineSize,fuelType,colour,mot,warranty,compname,compaddress,expdate;
    //@FXML private TableColumn<Part,String> partId,partName,partCost,partInstalledDate,partWarrantyExpiration;
    @FXML private TableColumn<Booking,String> bookingId,bookingType,bookingMileage,bookingCost,bookingDate,bookingTime,bookingLastServiceDate;
    @FXML private Label test,title;
    @FXML private Button addVehicle,editVehicle,deleteVehicle,logOut;

    //Customer details
    @FXML private Label fullName,custType,address,postcode,phone,email,customerId;
    //Booking details
    @FXML private Label nextBooking,nextBookingTime;

    //Variables to manage labels and controls of popup window
    @FXML final private ArrayList<Label> labels = new ArrayList<>();
    @FXML final private ArrayList<Control> controls = new ArrayList<>();
    ChoiceBox<String> customers;
    ChoiceBox<String> types;
    ChoiceBox<String> templates;
    TextField regNumField;
    Label existingRegNum;
    TextField modelField;
    TextField makeField;
    TextField engineSizeField;
    TextField fuelTypeField;
    TextField colourField;
    DatePicker motPicker;
    CheckBox warrantyBox;
    TextField companyNameField;
    TextField companyAddressField;
    DatePicker expirationPicker;

    //QUERIES TEXTFIELD
    @FXML private TextField searchField;

    //Buttons
    Button add;
    Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Check connection with database
        if (conn.isDbConnected()) {
            System.out.println("Connected");
            test.setText("Connected to database");

            try{
                c = Database.connector();
            }
            catch(Exception e){
                System.out.println(e);
            }

            //Set columns of Vehicles TableView
            customer.setCellValueFactory(v -> v.getValue().getOwnerProperty());
            customer_id.setCellValueFactory(v -> v.getValue().getOwnerIdProperty());
            regNum.setCellValueFactory(v -> v.getValue().getRegistrationNumberProperty());
            type.setCellValueFactory(v -> v.getValue().getTypeProperty());
            make.setCellValueFactory(v -> v.getValue().getMakeProperty());
            model.setCellValueFactory(v -> v.getValue().getModelProperty());
            engineSize.setCellValueFactory(v -> v.getValue().getEngineSizeProperty());
            fuelType.setCellValueFactory(v -> v.getValue().getFuelTypeProperty());
            colour.setCellValueFactory(v -> v.getValue().getColourProperty());
            mot.setCellValueFactory(v -> v.getValue().getRenewalDateProperty());
            warranty.setCellValueFactory(v -> v.getValue().getIsUnderWarrantyProperty());
            compname.setCellValueFactory(v -> v.getValue().getCompanyNameProperty());
            compaddress.setCellValueFactory(v -> v.getValue().getCompanyAddressProperty());
            expdate.setCellValueFactory(v -> v.getValue().getExpirationDateProperty());
            
            /*
            //Set columns of Parts TableView
            partId.setCellValueFactory(p -> p.getValue().getPartId());
            partName.setCellValueFactory(p -> p.getValue().getPartName());
            partCost.setCellValueFactory(p -> p.getValue().getPartCost());
            partInstalledDate.setCellValueFactory(p -> p.getValue().getPartInstalledDate());
            partWarrantyExpiration.setCellValueFactory(p -> p.getValue().getPartWarrantyExpiration());
            */

            //Set columns of Bookings TableView
            bookingId.setCellValueFactory(b -> b.getValue().getBookingNumProperty());
            bookingType.setCellValueFactory(b -> b.getValue().getBookingTypeProperty());
            bookingDate.setCellValueFactory(b -> b.getValue().getDateProperty());
            bookingTime.setCellValueFactory(b -> b.getValue().getTimeProperty());
            bookingMileage.setCellValueFactory(b -> b.getValue().getMileageProperty());
            bookingLastServiceDate.setCellValueFactory(b -> b.getValue().getLastServicedDateProperty());
            bookingCost.setCellValueFactory(b -> b.getValue().getCostProperty());


            //Disable edit and delete buttons
            editVehicle.setDisable(true);
            deleteVehicle.setDisable(true);

            //Fill Table View
            try{
                //Retrieve data from database
                buildData("");
            }
            catch(SQLException e){
                System.out.println("ERROR");
            }
            //Fill table view with data from database
            //vehiclesTable.getItems().setAll(getVehicles());

            //Listener to set edit and delete buttons disabled or active
            vehiclesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if(newSelection != null) {
                    //Active buttons
                    editVehicle.setDisable(false);
                    deleteVehicle.setDisable(false);

                    //Fill Customer Details on bottom tab
                    try{
                        buildCustomer(newSelection);
                        //buildParts(newSelection);
                        buildBookings(newSelection);
                        buildNextBookingDate(newSelection);
                    }
                    catch(SQLException e){
                        System.out.println(e);
                    }
                }
                else{
                    editVehicle.setDisable(true);
                    deleteVehicle.setDisable(true);
                }
            });

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                try{
                    buildData(newValue);
                }
                catch(SQLException e){
                    System.out.println(e);
                }
            });
        }
        else {
            test.setText("Not Connected to database");
        }

    }

    //Users adds a new vehicle and pushes it to database and updates the table
    @FXML
    private void addVehicle() throws IOException{
        //Create the pop-up form to add a new vehicle
        try{
            createPopup("Add Vehicle","Add");
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
                    if(checkRegNum()){
                        String customer1 = customers.getValue();
                        String regNum1 = regNumField.getText();
                        String type1 = types.getValue();
                        String model1 = modelField.getText();
                        String make1 = makeField.getText();
                        String engine = engineSizeField.getText();
                        String fuelType1 = fuelTypeField.getText();
                        String colour1 = colourField.getText();
                        String mot1 = motPicker.getValue().toString();
                        String warranty1;
                        String companyName;
                        String companyAddress;
                        String expdate1;
                        if (warrantyBox.isSelected()==true) {
                            warranty1 = "Yes";
                            companyName = companyNameField.getText();
                            companyAddress = companyAddressField.getText();
                            expdate1 = expirationPicker.getValue().toString();
                        } else {
                            warranty1 = "No";
                            companyName = "";
                            companyAddress = "";
                            expdate1 = "";
                        }

                        String[] customer_parts = customer1.split("-");

                        s = c.prepareStatement("INSERT INTO vehicles (customer,customer_id,vehicle_reg_num,type,model,make,engsize,fueltype,colour,mot,warranty,companyname,companyaddress,expirationdate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
                        s.setString(1, customer_parts[0]);
                        s.setString(2, customer_parts[1]);
                        s.setString(3, regNum1);
                        s.setString(4, type1);
                        s.setString(5, model1);
                        s.setString(6, make1);
                        s.setString(7, engine);
                        s.setString(8, fuelType1);
                        s.setString(9, colour1);
                        s.setString(10, mot1);
                        s.setString(11, warranty1);
                        s.setString(12, companyName);
                        s.setString(13, companyAddress);
                        s.setString(14, expdate1);
                        s.executeUpdate();

                        dialog.close();
                        vehiclesTable.refresh();
                    }
                    else{
                        existingRegNum.setText("Vehicle with this registration number already exists");
                        existingRegNum.setTextFill(Color.web("#FF0000"));
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
                try{
                    buildData("");
                }
                catch(SQLException exe){
                    System.out.println(exe);
                }
            }
        });
    }

    //Users select a row and click edit to change vehicle details
    @FXML
    private void editVehicle(ActionEvent event) throws IOException {
        Vehicle v = vehiclesTable.getSelectionModel().getSelectedItem();
        try{
            createPopup("Edit Vehicle","Edit");
        }
        catch(SQLException e){
            System.out.println(e);
        }
        customers.setValue(v.getOwner()+"-"+v.getOwnerId());
        regNumField.setText(v.getRegistrationNumber());
        regNumField.setDisable(true); //To avoid collisions in database
        types.setValue(v.getType());
        modelField.setText(v.getModel());
        makeField.setText(v.getMake());
        colourField.setText(v.getColour());
        engineSizeField.setText(v.getEngineSize());
        fuelTypeField.setText(v.getFuelType());
        LocalDate l1 = LocalDate.parse(v.getRenewalDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        motPicker.setValue(l1);
        if(v.getIsUnderWarranty().equals("Yes")){
            warrantyBox.setSelected(true);
            companyNameField.setDisable(false);
            companyNameField.setText(v.getCompanyName());
            companyAddressField.setText(v.getCompanyAddress());
            companyAddressField.setDisable(false);
            expirationPicker.setDisable(false);
            LocalDate l2 = LocalDate.parse(v.getExpirationDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            expirationPicker.setValue(l2);
        }
        else{
            warrantyBox.setSelected(false);
            companyNameField.setDisable(true);
            companyAddressField.setDisable(true);
            expirationPicker.setDisable(true);
        }

        //UPDATE ROW ON DATABASE
        add.setOnAction((ActionEvent e) -> {
            PreparedStatement s = null;
            if(fieldsCheck()) {
                try {
                    String customer1 = customers.getValue();
                    String regNum1 = regNumField.getText();
                    String type1 = types.getValue();
                    String model1 = modelField.getText();
                    String make1 = makeField.getText();
                    String engine = engineSizeField.getText();
                    String fuelType1 = fuelTypeField.getText();
                    String colour1 = colourField.getText();
                    String mot1 = motPicker.getValue().toString();
                    String warranty1 = "";
                    String companyName = "";
                    String companyAddress = "";
                    String expdate1 = "";
                    if (warrantyBox.isSelected()==true) {
                        warranty1 = "Yes";
                        companyName = companyNameField.getText();
                        companyAddress = companyAddressField.getText();
                        expdate1 = expirationPicker.getValue().toString();
                    } else {
                        warranty1 = "No";
                        companyName = "";
                        companyAddress = "";
                        expdate1 = "";
                    }

                    String[] customer_parts = customer1.split("-");

                    //Update row in database (users can't change registration number because is primary key
                    s = c.prepareStatement("UPDATE vehicles SET customer=?,customer_id=?,type=?,model=?,make=?,engsize=?,fueltype=?,colour=?,mot=?,warranty=?,companyname=?,companyaddress=?,expirationdate=? WHERE vehicle_reg_num=?;");
                    s.setString(1, customer_parts[0]);
                    s.setString(2, customer_parts[1]);
                    s.setString(3, type1);
                    s.setString(4, model1);
                    s.setString(5, make1);
                    s.setString(6, engine);
                    s.setString(7, fuelType1);
                    s.setString(8, colour1);
                    s.setString(9, mot1);
                    s.setString(10, warranty1);
                    s.setString(11, companyName);
                    s.setString(12, companyAddress);
                    s.setString(13, expdate1);
                    s.setString(14, regNum1);
                    s.executeUpdate();
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
                dialog.close();
                vehiclesTable.refresh();
                try{
                    buildData("");
                }
                catch(SQLException exe){
                    System.out.println(exe);
                }
            }
        });

    }

    @FXML
    private void deleteVehicle(ActionEvent event) throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this vehicle?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Vehicle selectedItem = vehiclesTable.getSelectionModel().getSelectedItem();
            vehiclesTable.getItems().remove(selectedItem);

            //Remove from database
            PreparedStatement s = null;

            try{
                //Deleting row in Vehicle table
                s = c.prepareStatement("DELETE FROM vehicles WHERE vehicle_reg_num=?;");
                s.setString(1,selectedItem.getRegistrationNumber());
                s.executeUpdate();

                //Deleting row in Parts table
                s = c.prepareStatement("DELETE FROM parts WHERE vehicle_reg=?;");
                s.setString(1,selectedItem.getRegistrationNumber());
                s.executeUpdate();

                //Deleting row in Bookings table
                s = c.prepareStatement("DELETE FROM bookings WHERE vehicle_reg=?;");
                s.setString(1,selectedItem.getRegistrationNumber());
                s.executeUpdate();

                //Deleting row in Specialist Repairs table
                s = c.prepareStatement("DELETE FROM specialist_repairs WHERE vehicle_reg=?;");
                s.setString(1,selectedItem.getRegistrationNumber());
                s.executeUpdate();
            }

            catch(SQLException err) {
                System.out.println(err);
            }
            finally {
                s.close();
            }
            vehiclesTable.refresh();

            try{
                buildData("");
            }
            catch(SQLException exe){
                System.out.println(exe);
            }
        }
        else {
            alert.close();
        }
    }

    public void buildData(String q) throws SQLException{
        vehicles = FXCollections.observableArrayList();

        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            if(q.equals("")){
                s = c.prepareStatement("SELECT * FROM vehicles;");
            }
            else{
                s = c.prepareStatement("SELECT * FROM vehicles WHERE vehicle_reg_num LIKE ? OR make LIKE ?;");
                s.setString(1,"%"+q+"%");
                s.setString(2,"%"+q+"%");
            }

            rs = s.executeQuery();

            while (rs.next()) {
                String customer = rs.getString("customer");
                String customer_id = rs.getString("customer_id");
                String regNum = rs.getString("vehicle_reg_num");
                String type = rs.getString("type");
                String model = rs.getString("model");
                String make = rs.getString("make");
                String engineSize = rs.getString("engsize");
                String fuelType = rs.getString("fueltype");
                String colour = rs.getString("colour");
                String mot = rs.getString("mot");
                String warranty = rs.getString("warranty");
                String compname = rs.getString("companyname");
                String compaddress = rs.getString("companyaddress");
                String expdate = rs.getString("expirationdate");

                Vehicle v = new Vehicle(customer,customer_id,regNum,type,model,make,engineSize,fuelType,colour,mot,warranty,compname,compaddress,expdate);

                vehicles.add(v);
            }

            vehiclesTable.getItems().setAll(vehicles);
        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (s != null) { s.close();rs.close(); }
        }
    }

    public boolean fieldsCheck(){
        int counter = 0;
        int checkCounter = 0;
        for(Control c : controls){
            if((c instanceof ChoiceBox && ((ChoiceBox)c).getValue()==null && !c.isDisabled()) || (c instanceof DatePicker && ((DatePicker)c).getValue()==null && !c.isDisabled()) || (c instanceof TextField && ((TextField)c).getText().equals("")) && !c.isDisabled()){
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

    public void createPopup(String operation,String buttonText) throws SQLException{
        //Nodes
        HBox hb = new HBox();
        //Controls
        Label title = new Label(operation + " Vehicle");
        regNumField = new TextField();
        modelField = new TextField();
        makeField = new TextField();
        engineSizeField = new TextField();
        fuelTypeField = new TextField();
        colourField = new TextField();
        motPicker = new DatePicker();
        warrantyBox = new CheckBox();
        companyNameField = new TextField();
        companyAddressField = new TextField();
        expirationPicker = new DatePicker();
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

        hb = new HBox(5);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        hb.getChildren().add(title);
        dialogVbox.getChildren().add(hb);

        String getCustomers = "SELECT name,surname,customer_id FROM customers";
        customers = new ChoiceBox<>();
        hb = new HBox(5);
        Label customersLab = new Label("Customer: ");
        hb.getChildren().addAll(customersLab, customers);
        dialogVbox.getChildren().add(hb);

        try (PreparedStatement s = c.prepareStatement(getCustomers)) {
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String cust_id = rs.getString("customer_id");
                customers.getItems().add(name+" "+surname + "-" + cust_id);

            }
        } catch(SQLException e ) {
            System.out.println(e);
        }

        hb = new HBox(5);
        Label regNumLab = new Label("Plate Number: ");
        existingRegNum = new Label("");
        hb.getChildren().addAll(regNumLab,regNumField,existingRegNum);
        dialogVbox.getChildren().add(hb);

        ObservableList<String> tp = FXCollections.observableArrayList("Car", "Van", "Truck");

        types = new ChoiceBox<>(tp);
        hb = new HBox(8);
        Label typeLab = new Label("Type: ");
        hb.getChildren().addAll(typeLab, types);
        dialogVbox.getChildren().addAll(hb);

        templates = new ChoiceBox<>();
        Collections.sort(temps, (String s1, String s2) -> s1.compareTo(s2));

        templates.setItems(temps);
        hb = new HBox(8);
        Label templatesLab = new Label("Template: ");
        hb.getChildren().addAll(templatesLab, templates);
        dialogVbox.getChildren().addAll(hb);
        templates.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String[] parts = newValue.split(" ");
            makeField.setText(parts[0]);
            modelField.setText(parts[1]);
            engineSizeField.setText(parts[2]);
            fuelTypeField.setText(parts[3]);
        });

        hb = new HBox(8);
        Label modelLab = new Label("Model: ");
        hb.getChildren().addAll(modelLab, modelField);
        dialogVbox.getChildren().addAll(hb);

        hb = new HBox(8);
        Label makeLab = new Label("Make: ");
        hb.getChildren().addAll(makeLab, makeField);
        dialogVbox.getChildren().add(hb);

        hb = new HBox(8);
        Label engineSizeLab = new Label("Engine Size: ");
        hb.getChildren().addAll(engineSizeLab, engineSizeField);
        dialogVbox.getChildren().add(hb);

        hb = new HBox(8);
        Label fuelTypeLab = new Label("Fuel Type: ");
        hb.getChildren().addAll(fuelTypeLab, fuelTypeField);
        dialogVbox.getChildren().add(hb);

        hb = new HBox(8);
        Label colourLab = new Label("Colour: ");
        hb.getChildren().addAll(colourLab, colourField);
        dialogVbox.getChildren().add(hb);

        hb = new HBox(8);
        Label motLab = new Label("MoT Renewal Date: ");
        hb.getChildren().addAll(motLab, motPicker);
        dialogVbox.getChildren().add(hb);

        hb = new HBox(8);
        Label warrantyLab = new Label("Warranty: ");
        //warrantyBox.setSelected(true);
        hb.getChildren().addAll(warrantyLab, warrantyBox);
        dialogVbox.getChildren().add(hb);

        warrantyBox.setOnAction((e) -> {
            if(warrantyBox.isSelected()) {
                companyNameField.setDisable(false);
                companyAddressField.setDisable(false);
                expirationPicker.setDisable(false);
            }
            else {
                companyNameField.setDisable(true);
                companyNameField.setText("");
                companyAddressField.setDisable(true);
                companyAddressField.setText("");
                expirationPicker.setDisable(true);
                expirationPicker.setValue(null);
            }
        });

        hb = new HBox(8);
        Label companyNameLab = new Label("Company Name: ");
        hb.getChildren().addAll(companyNameLab, companyNameField);
        companyNameField.setDisable(true);
        dialogVbox.getChildren().add(hb);

        hb = new HBox(8);
        Label companyAddressLab = new Label("Company Address: ");
        hb.getChildren().addAll(companyAddressLab, companyAddressField);
        companyAddressField.setDisable(true);
        dialogVbox.getChildren().add(hb);

        hb = new HBox(8);
        Label expDateLab = new Label("Expiration Date: ");
        hb.getChildren().addAll(expDateLab,expirationPicker);
        expirationPicker.setDisable(true);
        dialogVbox.getChildren().add(hb);

        //List of Controls
        controls.add(customers);controls.add(regNumField);controls.add(types);controls.add(modelField);controls.add(makeField);controls.add(engineSizeField);
        controls.add(fuelTypeField);controls.add(colourField);controls.add(motPicker);controls.add(companyNameField);controls.add(companyAddressField);
        controls.add(expirationPicker);

        //List of Labels
        labels.add(customersLab);labels.add(regNumLab);labels.add(typeLab);labels.add(modelLab);labels.add(makeLab);labels.add(engineSizeLab);
        labels.add(fuelTypeLab);labels.add(colourLab);labels.add(motLab);labels.add(companyNameLab);labels.add(companyAddressLab);labels.add(expDateLab);

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

    private void buildCustomer(Vehicle v) throws SQLException{
        String customer_Id = "";
        String customer_name = "";
        String customer_surname = "";
        String caddress="";
        String ctype="";
        String cpostcode="";
        String cphone="";
        String cemail="";
        String nextBookingDate = "";

        String getCustomerInfo = "SELECT * FROM customers WHERE customer_id=?";

        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            s = c.prepareStatement(getCustomerInfo);
            s.setString(1,v.getOwnerId());

            rs = s.executeQuery();

            while(rs.next()) {
                customer_Id = rs.getString("customer_id");
                customer_name = rs.getString("name");
                customer_surname = rs.getString("surname");
                caddress = rs.getString("address");
                cpostcode = rs.getString("postcode");
                ctype = rs.getString("type");
                cphone = rs.getString("phone");
                cemail = rs.getString("email");
            }

            fullName.setText(customer_name+" "+customer_surname);
            address.setText(caddress);
            custType.setText(ctype);
            postcode.setText(cpostcode);
            phone.setText(cphone);
            email.setText(cemail);
            customerId.setText(customer_Id);
        } catch (SQLException e ) {
            System.out.println(e);
        }
        finally {
            s.close();
            rs.close();
        }
    }

    private void buildParts(Vehicle v) throws SQLException{
        //parts = FXCollections.observableArrayList();
        //BUILD TABLE FOR PARTS
        String id = "";
        String name = "";
        String price = "";
        String installedDate = "";
        String warrantyExpiration = "";

        String getCustomerInfo = "SELECT * FROM parts WHERE vehicle_reg=?";
        PreparedStatement s = null;
        ResultSet rs = null;

        try {
            s = c.prepareStatement(getCustomerInfo);
            s.setString(1,v.getRegistrationNumber());
            rs = s.executeQuery();

            while(rs.next()) {
                id = rs.getString("part_id");
                name = rs.getString("name");
                price = rs.getString("price");
                installedDate = rs.getString("installed_date");
                warrantyExpiration = rs.getString("warrantyExpiration");

                //Part p = new Part();
                //parts.add(p);
            }

            //partsTable.getItems().setAll(parts);

        } catch (SQLException e ) {
            System.out.println(e);
        }
        finally {
            s.close();
            rs.close();
        }
    }

    private void buildBookings(Vehicle v) throws SQLException{
        //BUILD TABLE FOR BOOKINGS
        bookings = FXCollections.observableArrayList();

        //BUILD TABLE FOR PARTS
        String id = "";
        String regNum = "";
        String customer = "";
        String type = "";
        String cost = "";
        String date = "";
        String time = "";
        String mileage = "";
        String lastServiceDate = "";
        String mechanics = "";

        String getCustomerInfo = "SELECT * FROM bookings WHERE vehicle_reg=?";

        PreparedStatement s = null;
        ResultSet rs=null;

        try{
            s = c.prepareStatement(getCustomerInfo);
            s.setString(1,v.getRegistrationNumber());
            rs = s.executeQuery();

            while(rs.next()) {
                id = rs.getString("booking_id");
                regNum = rs.getString("vehicle_reg");
                customer = rs.getString("customer");
                date = rs.getString("date");
                time = rs.getString("time");
                cost = rs.getString("total_cost");
                type = rs.getString("booking_type");
                mileage = rs.getString("current_mileage");
                lastServiceDate = rs.getString("last_service_date");
                mechanics = rs.getString("mechanics");

                Booking b = new Booking(id,type,regNum,mechanics,customer,date,time,lastServiceDate,mileage,cost);
                bookings.add(b);
            }
            bookingsTable.getItems().setAll(bookings);
        } catch (SQLException e ) {
            System.out.println(e);
        }
        finally {
            s.close();
            rs.close();
        }
    }

    private void buildNextBookingDate(Vehicle v) throws SQLException{
        PreparedStatement s = null;
        ResultSet rs = null;

        String getNextBooking = "SELECT date,time FROM bookings WHERE vehicle_reg=? AND date>? ORDER BY date(date) ASC Limit 1";
        String nextBookingDate = "";
        String nextBookingT = "";

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        String date = dateFormat.format(dt);
        System.out.println(date);

        try{
            s = c.prepareStatement(getNextBooking);
            s.setString(1,v.getRegistrationNumber());
            s.setString(2,date);
            rs = s.executeQuery();

            if(!rs.isClosed()){
                nextBookingDate = rs.getString("date");
                nextBookingT = rs.getString("time");
                nextBooking.setText(nextBookingDate);
                nextBookingTime.setText(nextBookingT);
            }
            else{
                System.out.println("No scheduled bookings for this vehicle.");
                nextBooking.setText("No scheduled bookings");
                nextBookingTime.setText("");
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        finally{
            s.close();
            rs.close();
        }
    }

    private boolean checkRegNum() throws SQLException{
        PreparedStatement s = null;
        ResultSet rs = null;
        try{
            s = c.prepareStatement("SELECT vehicle_reg_num FROM vehicles;");
            rs = s.executeQuery();
            while(rs.next()) {
                String regNum = rs.getString("vehicle_reg_num");
                if(regNum.equals(regNumField.getText())){
                    return false;
                }
            }
            return true;
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
        finally {
            s.close();
            rs.close();
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

