/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

/**
 *
 * @author kisekkaDavid
 */
public class Main extends Application {
    
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("CustomerGUI.fxml"));
        primaryStage.setTitle("Customer Account");
        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.show();
        
    }
}