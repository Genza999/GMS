package common;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * Tabs Pane Controller
 *
 * @author kisekkaDavid
 */

public class AuthenticatorController implements Initializable {
    public AuthenticatorModel authenticatorModel = new AuthenticatorModel();
    private String userType = "";
    
    @FXML
    private Button closeButton,loginButton;
    
    @FXML
    private Label isConnected;
    
    @FXML
    private TextField txtUsername;
    
    @FXML
    private TextField txtPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (authenticatorModel.isDbConnected()) {
            isConnected.setText("Connected");
        } 
        
        else {
            isConnected.setText("Not Connected");
        }
    }
    
    @FXML
    public void login() {
        try {
            if (authenticatorModel.isLogin(txtUsername.getText(), txtPassword.getText())) {
                isConnected.setText("Username and Password is correct");
                userType = authenticatorModel.getUserType(txtUsername.getText(), txtPassword.getText());
                
                 //GO TO SYSTEM
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Tabs.fxml"));
                    
                    fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
                        @Override
                        public Object call(Class<?> controllerClass) {
                            if (controllerClass == TabsController.class) {
                                TabsController controller = new TabsController();
                                controller.setUserType(userType);
                                return controller;
                            } 
                            else {
                                try {
                                    return controllerClass.newInstance();
                                } 
                                catch (Exception exc) {
                                    throw new RuntimeException(exc); 
                                }
                            }
                        }
                    });

                    Parent root1 = (Parent) fxmlLoader.load();
                    
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    //stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(new Scene(root1)); 
                    stage.setResizable(false);
                    stage.show();

                    Stage current = (Stage) loginButton.getScene().getWindow();
                    current.close();
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
            else {
                isConnected.setText("Username and Password is not correct");
            }
        } catch (SQLException e) {
            isConnected.setText("Username and Password is not correct");
            e.printStackTrace();
        }
    }
    
    @FXML
    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
