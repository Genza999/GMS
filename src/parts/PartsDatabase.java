/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;

//import common.*;
import java.util.*;


import java.sql.Connection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import parts.VehiclePartsTableObject;
import javafx.scene.control.TextField;
import parts.partBookingTableObject;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



/**
 *
 * @author kisekkaDavid
 */
public class PartsDatabase {
    TextField partNameTf;
    TextField partPriceTF;
    TextField partWarrantyTF;
    TextField partInstallTF;
    TextField partLocationT;




   
    public Connection connection;

    public PartsDatabase(Connection c){
       try {
            
            connection = c;
            
        } 
        
        catch (Exception e) {
            System.out.println("never made connection");
        }
       
    }
    
   
    public ArrayList<Part> getAllParts(){
        
         //search database and retrieve list of eligible parts.
        String req = "SELECT * FROM parts;"; 
        
        PreparedStatement s = null;
               ResultSet rs = null; 
                
        ArrayList<Part> allParts = new ArrayList<Part>(0);
                
                try {
                           s = connection.prepareStatement(req);
                          rs = s.executeQuery();
                            
                while(rs.next()) {
                String pName = rs.getString("name");
                String pDes = rs.getString("description");
                String pID = rs.getString("part_identification");
                String pPrice = rs.getString("price");
                String pInstall = rs.getString("install_date");
                String pWarr = rs.getString("warranty");
                String pLoc = rs.getString("location");
                String pToBeInstall= rs.getString("to_be_installed_on");

                Part p = new Part(pName,pDes,pID,pPrice,pInstall,pWarr,pLoc,pToBeInstall);
                
                allParts.add(p);
                  
            } }   catch (SQLException e ) {
            System.out.println("error getting all parts.");
        }
                
                return allParts;
        
    }
    
    //This Method Returns All Parts As An Observable List.
    public ObservableList<Part> getAllPartsForTable1(boolean containUsedParts){
        
        //Create Empty Observable list to hold parts.
        ObservableList<Part> newData = FXCollections.observableArrayList();
        ArrayList<Part> parts = new ArrayList<Part>(0);
        
                //Get all parts from the database and put them in an observable list:
                try {
                      parts = this.getAllParts();
                      for(int i=0; i<parts.size(); i++){
                          newData.add(parts.get(i));
                      }
             } catch (Exception e ) {
            System.out.println("1");
        }
                
             //Remove used parts if required.
        if(containUsedParts){
        return newData;
        } else {
        return this.removeUsedParts(newData);
        
        }
        
    }

    
    //this method searches the database and returns a list of eligible parts:
    public  ObservableList<Part> searchParts(String search,boolean containUsedParts){
        
        
      //Create Empty Observable list to hold parts.
        ObservableList<Part> parts = this.getAllPartsForTable1(containUsedParts);
        //Remove Anything Not Fitting The Search:
        for(int i=0; i<parts.size(); i++){
            if(parts.get(i).getName().equals(search)||parts.get(i).getID().equals(search)){
                
            } else { parts.remove(i); }
        }
        
     return parts;   
    }
    
    //This Method Removes The Used Parts From The Given List.
    private ObservableList<Part> removeUsedParts(ObservableList<Part> partList){
        
        int i =0;
        
        while(partList.get(i) != null){
            
            //Check If install date is in the past:
            //if(partList.get(i).getInstall) MAKE THIS CHECK DATES.
            if(!partList.get(i).isUsed()){
                partList.remove(i);
            } 
            i++;
        }
        
         return partList;
    }
    
     public void deletePart(String partID){
        
    }
    
     //handle past/future bookings shit and SR later.
     //HAVE TO ADD IN THE DATE BIT.
     public ObservableList<partBookingTableObject>  getPartBookingObjects(boolean incSR, boolean incPB){
         
          //Create Empty Observable list to hold parts.
        ObservableList<partBookingTableObject> newData = FXCollections.observableArrayList();
        ArrayList<Part> parts = new ArrayList<Part>(0);
        
        
                //Get all parts from the database, turn them into an observable list of part booking objects.
                try {
                      parts = this.getAllParts();
                      newData = partBookingTableObject.getObservableBookingObjectListFromArrayList(parts);
                      
             } catch (Exception e ) {
            System.out.println("error");
        }
        
                return newData;
    }
     
    
     
     public ObservableList<partBookingTableObject> getPartBookingObjectsRefinedDate(boolean incSR,boolean incPB, LocalDate dateChosen){
         
           //Get Full List Regardless Of Date: 
           ObservableList<partBookingTableObject> resultList = this.getPartBookingObjects(incSR, incPB);
           
           resultList = this.getPartBookingObjects(incSR, incPB);
           
           //Remove ALl Bookings Not On The Right Date:
         
         return resultList;
     }
     
     
     //To Delete??
    // public ObservableList<Part> getBookingPartObjectsForGivenDate(){
          /* SQL

        SELECT * FROM parts WHERE installedDate EQUALS "stock room"
        */
       //this.getPartBookingObjects(true, true)
         //Then Take Out The Ones Not On That Date
     
         /* Actual SQL:
         
         
         */
     //}
     
    public void addPartToDatabaseWithStrings(String n, String d, String iD, String p,String iDate, String wD, String l, String tBIO){
        
        PreparedStatement s = null;
                ResultSet rs = null; 
                
                try {
                 s = connection.prepareStatement("INSERT INTO parts (name,description,price,partID,install_date,warranty_date,location,to_be_installed_on) VALUES (?,?,?,?,?,?,?,?);");
                        s.setString(1, n);
                        s.setString(2, d);
                        s.setString(3, iD);
                        s.setString(4, p);
                        s.setString(5, iDate);
                        s.setString(6, wD);
                        s.setString(7, l);
                        s.setString(8, tBIO);
                        s.executeUpdate();
                } catch(Exception e){
                }
    }
        
    

//    public void addPartToDataBase(Part p){
//
//         PreparedStatement s = null;
//                ResultSet rs = null;
//
//                        String name = partNameTf.getText();
//                        String price = partPriceTF.getText();
//                        String Warranty = partWarrantyTF.getText();
//                        String Install = partInstallTF.getText();
//                        String location = partLocationT.getText();
//
//        try {
//                        s = connection.prepareStatement("INSERT INTO parts (name,price,Warranty,Install,location) VALUES (?,?,?,?,?);");
//                        s.setString(1, name);
//                        s.setString(2, price);
//                        s.setString(3, Warranty);
//                        s.setString(4, Install);
//                        s.setString(5, location);
//                        s.executeUpdate();
//        } catch(Exception e){
//        }
//
//    }
    
    public void addPartToDatabaseWithValues(ArrayList<String> list){
        
    }
    
   
    
    public Part getPartFromDatabase(String PartID){
        return new Part(1);
    }
    
   
     
     
     //This Method Returns All The Unused Parts
     public ObservableList<Part> getAllUnusedParts(){
         
          /* SQL
        
        req = SELECT * FROM parts WHERE location EQUALS 'stock room';
        ObservableList<Part> newData = this.makeDataRequest(req);
        return newData;
        */
          
          
        ObservableList<Part> partData = FXCollections.observableArrayList();
         partData.add(new Part(1));
         partData.add(new Part(2));
         partData.add(new Part(2));
         partData.add(new Part(2));
         partData.add(new Part(1));
         
         return partData;
         
    }
    
    
    
   
    
    }


    

