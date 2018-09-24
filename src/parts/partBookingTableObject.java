/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts;


import java.util.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kisekkaDavid
 */
public class partBookingTableObject {
    
    private ArrayList<Part> allPartsUsedOnBooking; // A List Of All The Parts Assosciated With A Booking.
    private StringProperty customerName; //stringProperty;
    private StringProperty vehReg; //
    private StringProperty date; // 
    private StringProperty allParts;
    
   
    
    public partBookingTableObject(Part p){
        
        this.customerName = new SimpleStringProperty("tbs");
        this.vehReg = new SimpleStringProperty("tbs");
        this.date = new SimpleStringProperty("tbs");
        this.allParts = new SimpleStringProperty("tbs");

        allPartsUsedOnBooking = new ArrayList<>(0);
        vehReg.set(p.getLocation()+"");
        allPartsUsedOnBooking.add(p);

    }
    
    public void addCustName(String cN){
        customerName.set(cN);
    }
    
    public StringProperty getCustNameProperty(){

        return customerName;
    }

    public StringProperty getVehRegProperty(){
        return vehReg;
    }
    
     public StringProperty getAllPartsProperty(){

         int i = 0;
         String all = "";
//         while(allPartsUsedOnBooking.get(i)!= null){
//
//             all = all + allPartsUsedOnBooking.get(i).getName() + ", ";
//         }

//         for(i = 0;i <= allPartsUsedOnBooking.size();i++ ){
             all = all + allPartsUsedOnBooking.get(i).getName() + ", ";

//         }
         allParts.set(all);

         return allParts;
    }
     
     public StringProperty getBookingDateProperty(){
         return date;
     }
    
    
    public void addPart(Part p){
       
            allPartsUsedOnBooking.add(p);
        
        
    }
    
    public void addFuturePart(Part p){
        if(vehReg == null){
        vehReg.set(p.getToBeInstalledOn());
        this.date.set(p.getInstalledDate());
        } else {
            allPartsUsedOnBooking.add(p);
        }
        
    }
    
    public static partBookingTableObject turnPartsIntoFutureBookingObject(ArrayList<Part> parts){
        
        //put part variables into booking object:
        partBookingTableObject tableObj = new partBookingTableObject(new Part(0));
        String customerName = "I HAVE TO DO THIS BIT";
        tableObj.addCustName(customerName);
        int i = 0;
        
        while(parts.get(i)!=null){
            tableObj.addFuturePart(parts.get(i));
            i++;
        }
        
        return tableObj;
    }
    
    public static ObservableList<partBookingTableObject> getObservableBookingObjectListFromArrayList(ArrayList<Part> partList){
        
        
        //For Past Booking Objects:
        
        ArrayList<ArrayList<Part>> objectSets = new ArrayList<ArrayList<Part>>(0);
        
        boolean found = false; //boolean to keep track of if a part has been placed.
        
        for(int i=0; i<partList.size(); i++){
            
            String firstLetterOfLocation = partList.get(i).getLocation().substring(0,1);
            if(firstLetterOfLocation.equals("v")){
                //this means part is eligible so find its set and put it in:
            for(int t=0; t<objectSets.size(); t++){
                //Try To Put It In The Right Set:
                if(objectSets.get(t).get(0).getLocation().equals(partList.get(i).getLocation())){
                    objectSets.get(t).add(partList.get(i));
                }
                
            }// check if part needs to be put in a new set:
            if(!found){
                ArrayList<Part> newList = new ArrayList<Part>(0);
                newList.add(partList.get(i));
                objectSets.add(newList);
                
            }
            
            }//End If (Putting eligible part in place).
            found = false;
        }
        
        //Now add the array list of different object sets into the observable list as actual put together objects:
        ObservableList<partBookingTableObject> finalList = FXCollections.observableArrayList();
        
        //Loop through all lists and create a single table object from them:
        for(int q=0; q<objectSets.size(); q++){
            //create new table object with first part form the list:
            partBookingTableObject newObj = new partBookingTableObject(objectSets.get(q).get(0));
            //Add the rest of the list:
            for(int z=1; z<objectSets.get(q).size(); z++){
                newObj.addPart(objectSets.get(q).get(z));
            }
            finalList.add(newObj);
        }
        
        
        return finalList;
    }
    
    
}
