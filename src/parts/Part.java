/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.*;

/**
 *
 * @author kisekkaDavid
 */
public class Part {

    private StringProperty name;
    private StringProperty description;
    private StringProperty ID;
    private StringProperty price;
    private StringProperty installedDate;
    private StringProperty warrantyDate;
    private StringProperty location; //Either Stock Room ID or Vehicle ID.
    private StringProperty toBeInstalledOn; // Vehicle Reg Of Vehicle It Will Be ON Or The String "No Planned Installations."
    
    
    
    
    
    public Part(){
        
    }
    
    public Part(String n, String d, String iD, String p,String iDate, String wD, String l, String tBIO){
             
        this.setName(n);
        this.setDescription(d);
        this.setID(iD);
        this.setPrice(p);
        this.setInstallDate(iDate);
        this.setWarranty(wD);
        this.setLocation(l);
        this.setToBeInstalledOn(tBIO);
        
        
        
    }

//    public Part(TextField n, TextField p, TextField iDate, TextField wD, TextField l){
//
//
//    }
    
    public Part(int i){
        if(i==1){
            
            
    this.name = new SimpleStringProperty("SDA");
    this.description = new SimpleStringProperty("yes");
    this.ID = new SimpleStringProperty("SDA");
    this.price = new SimpleStringProperty("SDA");
    this.installedDate = new SimpleStringProperty("SDA");
    this.warrantyDate = new SimpleStringProperty("SDA");
    this.location = new SimpleStringProperty("SDA");
        }else if(i==2){
    this.name = new SimpleStringProperty("test");
    this.description = new SimpleStringProperty("SDFDAS");
    this.ID = new SimpleStringProperty("SSDF");
    this.price = new SimpleStringProperty("GDDA");
    this.installedDate = new SimpleStringProperty("DSFGSDA");
    this.warrantyDate = new SimpleStringProperty("FGFSDA");
    this.location = new SimpleStringProperty("FSDA");
    this.toBeInstalledOn = new SimpleStringProperty("FSDA");

        }
    }
    
    //NOT SURE WHETHER TO USE THIS YET... WOULD JUST HELP TO MAKE NEW PARTS:
    
   /* //THIS CONSTRUCTOR TAKES A PART NAME AND DUPLICATES FIRST PART IN STOCK ROOM WITH THE SAME NAME.
    public Part(String n){
        this.name = n;
        this.copyPartFrom
    }
    
    private void copyPartFromStockRoom(String n){
        
    }
    
    */
    
    public void setInstallDate(String idate){
        installedDate.set(idate);
    }
    
    public void setName(String n){
        name.set(n);
    }
    
    public void setWarranty(String w){
        warrantyDate.set(w);
    }
    
    
    
    public void setLocation(String l){
        location.set(l);
    }
    
    public void setToBeInstalledOn(String tb){
        toBeInstalledOn.set(tb);
    }
    
    public void setDescription(String d){
        description.set(d);
    }
    public void setPrice(String p){
        price.set(p);
    }
    public void setID(String id){
        ID.set(id);
    }
   
    public String getPrice(){
        return price.get();
    }
    
    public StringProperty getPriceProperty(){
        return price;
    }
    
    public String getInstalledDate(){
        return installedDate.get();
    }
    
    public StringProperty getInstallDateProperty(){
        return installedDate;
    }
    
    public String getWarrantyExpired(){
        return warrantyDate.get();
    }
    
    public StringProperty getWarrantyProperty(){
        return warrantyDate;
    }
    
    
    public String getName(){
        return name.get();
    }
    
    public String getToBeInstalledOn(){
        return toBeInstalledOn.get();
    }
    
     public StringProperty getNameProperty(){
        return name;
    }
    
    
    
    public String getDescription(){
        return description.get();
    }
    
    public StringProperty getDescriptionProperty(){
        return description;
    }
    
    public String getLocation(){
        return location.get();
    }
    
    public StringProperty getLocationProperty(){
        return location;
    }
    
    public String getID(){
        return ID.get();
    }
    
    public StringProperty getIDProperty(){
        return ID;
    }
    
    public void makePart(String partType){
        
    }
    //TO DO
    public boolean isUsed(){
        if(location.equals("stock room")||installedDate.equals("na")){
            return false;
        } else { return true;
    }
    }
    

}
    
   



