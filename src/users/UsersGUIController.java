package users;

import common.*;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kisekkaDavid
 */

public class UsersGUIController implements Initializable {
    //Variables for database connection
    public Database conn = new Database();
    public Connection c; 
    
    //Stage for popup window
    public Stage dialog;
    
    //Observable list contains users objects for table view
    private ObservableList<User> users; //= FXCollections.observableArrayList();
    
    //FXML Variables for Users
    @FXML private TableView<User> usersTable; 
    @FXML private TableColumn<User,String> userUsername,userFirstname,userSurname,userPassword,userType;

    @FXML private Label test,title;
    @FXML private Button addUser,editUser,deleteUser,logOut;
  
    //Variables to manage labels and controls of popup window
    @FXML final private ArrayList<Label> labels = new ArrayList<>();
    @FXML final private ArrayList<Control> controls = new ArrayList<>();

    TextField username;
    Label existingUsername;
    TextField password;
    TextField firstname;
    TextField surname;
    ChoiceBox<String> type;

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
            userUsername.setCellValueFactory(u -> u.getValue().getUsernameProperty());
            userFirstname.setCellValueFactory(u -> u.getValue().getFirstnameProperty());
            userSurname.setCellValueFactory(u -> u.getValue().getSurnameProperty());
            userPassword.setCellValueFactory(u -> u.getValue().getPasswordProperty());
            userType.setCellValueFactory(u -> u.getValue().getTypeProperty());
            
            //Disable edit and delete buttons
            editUser.setDisable(true);
            deleteUser.setDisable(true);
                 
            //Fill Table View
            try{
                //Retrieve data from database
                 buildData("");
            }
            catch(SQLException e){
                System.out.println("ERROR");
            }
            
            //Listener to set edit and delete buttons disabled or active 
            usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if(newSelection!=null){
                    editUser.setDisable(false);
                    deleteUser.setDisable(false);
                }
                else{
                    editUser.setDisable(true);
                    deleteUser.setDisable(true);
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
    
    //Users adds a new user and pushes it to database and updates the table
    @FXML
    private void addUser() throws IOException{
        //Create the pop-up form to add a new user
        try{
            createPopup("Add User","Add");
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
                    if(checkUsername()){
                        String uusername = username.getText();
                        String ffirstname = firstname.getText();
                        String ssurname = surname.getText();
                        String ppassword = password.getText();
                        String ttype = type.getValue();

                        s = c.prepareStatement("INSERT INTO users (username,user_type,firstname,surname,password) VALUES (?,?,?,?,?);");
                        s.setString(1, uusername);
                        s.setString(2, ttype);
                        s.setString(3, ffirstname);
                        s.setString(4, ssurname);
                        s.setString(5, ppassword);
                        s.executeUpdate();

                        dialog.close();
                        usersTable.refresh();
                    }
                    else{
                        existingUsername.setText("User with this username already exists or username doesn't constist of 5 digits");
                        existingUsername.setTextFill(Color.web("#FF0000"));
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
    private void editUser(ActionEvent event) throws IOException {
        User u = usersTable.getSelectionModel().getSelectedItem();
        try{
            createPopup("Edit User","Edit");
        }
        catch(SQLException e){
            System.out.println(e);
        }
        username.setText(u.getUsername());
        username.setDisable(true); //To avoid collisions in database
        firstname.setText(u.getFirstname());
        surname.setText(u.getSurname());
        type.setValue(u.getType());
        password.setText(u.getPassword());
        
        //UPDATE ROW ON DATABASE
        add.setOnAction((ActionEvent e) -> {
            PreparedStatement s = null;
            if(fieldsCheck()) {
                try {
                    String uusername = username.getText();
                    String ffirstname = firstname.getText();
                    String ssurname = surname.getText();
                    String ppassword = password.getText();
                    String ttype = type.getValue();
                    
                    //Update row in database (users can't change username because is primary key)
                    s = c.prepareStatement("UPDATE users SET user_type=?,firstname=?,surname=?,password=? WHERE username=?;");
                    s.setString(1, ttype);
                    s.setString(2, ffirstname);
                    s.setString(3, ssurname);
                    s.setString(4, ppassword);
                    s.setString(5, uusername);
                     
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
                usersTable.refresh();
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
    private void deleteUser(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete this user?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            User selectedItem = usersTable.getSelectionModel().getSelectedItem();
            usersTable.getItems().remove(selectedItem);
            
            //Remove from database
            PreparedStatement s = null;
               
            try{
                //Deleting row in Vehicle table
                s = c.prepareStatement("DELETE FROM users WHERE username=?;");
                s.setString(1,selectedItem.getUsername());
                s.executeUpdate();  
            }
                
            catch(SQLException err) {
                System.out.println(err);
            }
            finally {
                s.close();
            }  
            usersTable.refresh();

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
        users = FXCollections.observableArrayList();
              
        PreparedStatement s = null;
        ResultSet rs = null;
        
        try {
            if(q.equals("")){
                s = c.prepareStatement("SELECT * FROM users;");
            }
            else{
                s = c.prepareStatement("SELECT * FROM users WHERE username LIKE ? OR surname LIKE ?;");
                s.setString(1,"%"+q+"%");
                s.setString(2,"%"+q+"%");
            }
            
            rs = s.executeQuery();
            
            while(rs.next()) {
                String uusername = rs.getString("username");
                String ffirstname = rs.getString("firstname");
                String ssurname = rs.getString("surname");
                String ttype = rs.getString("user_type");
                String ppassword = rs.getString("password");

                User u = new User(uusername,ppassword,ffirstname,ssurname,ttype);
                users.add(u);   
            }
            
            usersTable.getItems().setAll(users);
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
    
    public void createPopup(String operation,String buttonText) throws SQLException{
        //Nodes
        HBox hb = new HBox();
        //Controls
        Label title = new Label(operation + " User");
        firstname = new TextField();
        surname = new TextField();
        type = new ChoiceBox();
        username = new TextField();
        password = new TextField();
  
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
        
        hb = new HBox(5);
        Label firstnameLab = new Label("Firstname: ");
        hb.getChildren().addAll(firstnameLab,firstname);
        dialogVbox.getChildren().add(hb);
       
        hb = new HBox(8);
        Label surnameLab = new Label("Surname: ");
        hb.getChildren().addAll(surnameLab, surname);
        dialogVbox.getChildren().addAll(hb);
        
        ObservableList<String> tp = FXCollections.observableArrayList("admin", "daily");
        
        type = new ChoiceBox<>(tp);
        hb = new HBox(8);
        Label typeLab = new Label("Type: ");
        hb.getChildren().addAll(typeLab, type);
        dialogVbox.getChildren().addAll(hb);
       
        hb = new HBox(8);
        Label usernameLab = new Label("Username: ");
        existingUsername = new Label("");
        hb.getChildren().addAll(usernameLab,username,existingUsername);
        dialogVbox.getChildren().add(hb);
        
        hb = new HBox(8);
        Label passwordLab = new Label("Password: ");
        hb.getChildren().addAll(passwordLab, password);
        dialogVbox.getChildren().add(hb);
        
         //List of Controls
        controls.add(firstname);controls.add(surname);controls.add(type);controls.add(username);controls.add(password);
        
        //List of Labels
        labels.add(firstnameLab);labels.add(surnameLab);labels.add(typeLab);labels.add(usernameLab);labels.add(passwordLab);
        
        //BUTTONS
        hb = new HBox(30);
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.getChildren().addAll(cancel,add);
        dialogVbox.getChildren().add(hb);
        cancel.setOnAction(event -> dialog.close());
        //Set the scene
        Scene dialogScene = new Scene(dialogVbox, 700, 300);
        dialog.setTitle(operation);
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();      
    }    
    
    private boolean checkUsername() throws SQLException{
        PreparedStatement s = null;
        ResultSet rs = null;    
        try{
            s = c.prepareStatement("SELECT username FROM users;");    
            rs = s.executeQuery();
            while(rs.next()) {
                String un = rs.getString("username");
                if(un.equals(username.getText())){
                    return false;
                }
            }
            if(username.getText().length()==5){
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
