package bookings;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;

public class Booking{
    protected StringProperty bookingNumber;
    protected StringProperty bookingType;
    protected StringProperty registrationNumber;
    protected StringProperty mec;
    protected StringProperty customer;
    protected StringProperty date;
    protected StringProperty times;
    protected StringProperty lastServicedDate;
    protected StringProperty mileage;
    protected StringProperty cost;

    public Booking(String bn,String bt, String rn, String m, String cs, String d, String t, String lsdate, String mil, String c) {
        bookingNumber = new SimpleStringProperty(bn);
        bookingType = new SimpleStringProperty(bt);
        registrationNumber = new SimpleStringProperty(rn);
        mec = new SimpleStringProperty(m);
        customer = new SimpleStringProperty(cs);
        date = new SimpleStringProperty(d);
        times = new SimpleStringProperty(t);
        lastServicedDate = new SimpleStringProperty(lsdate);
        mileage = new SimpleStringProperty(mil);
        cost = new SimpleStringProperty(c);
    }

    Booking(TableColumn<Booking, String> bNum, TableColumn<Booking, String> bType, String regNum, TableColumn<Booking, String> mec, TableColumn<Booking, String> firstN, TableColumn<Booking, String> lastN, TableColumn<Booking, String> date, TableColumn<Booking, String> time, TableColumn<Booking, String> lastSD, TableColumn<Booking, String> cost, TableColumn<Booking, String> mileage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public void setBookingType(String btype){
        bookingType.set(btype);
    }
    
    public String getBookingType(){
        return bookingType.get();
    }
    
    public StringProperty getBookingTypeProperty(){
        return bookingType;
    }

    public void setBookingNum(String bnum){
        bookingNumber.set(bnum);
    }

    public String getBookingNum(){
        return bookingNumber.get();
    }

    public StringProperty getBookingNumProperty(){
        return bookingNumber;
    }

    public void setRegistrationNumber(String plate){
        registrationNumber.set(plate);
    }

    public String getRegistrationNumber(){
        return registrationNumber.get();
    }

     public StringProperty getRegistrationNumberProperty(){
        return registrationNumber;
    }

    public void setMechanic(String me){
        mec.set(me);
    }

    public String getMechanic(){
        return mec.get();
    }

     public StringProperty getMechanicProperty(){
        return mec;
    }

    public void setCustomer(String fname){
        customer.set(fname);
    }

    public String getCustomer(){
        return customer.get();
    }

     public StringProperty getCustomerProperty(){
        return customer;
    }
//
//    public void setLastName(String lname){
//        lastN.set(lname);
//    }
//
//    public String getLastName(){
//        return lastN.get();
//    }
//
//     public StringProperty getLastNameProperty(){
//        return lastN;
//    }

    public void setDate(String dt){
        date.set(dt);
    }

    public String getDate(){
        return date.get();
    }

    public StringProperty getDateProperty(){
        return date;
    }

    public void setTime(String ti){
        times.set(ti);
    }

    public String getTime(){
        return times.get();
    }

    public StringProperty getTimeProperty(){
        return times;
    }

    public void setLastServicedDate(String ldate){
        lastServicedDate.set(ldate);
    }

    public String getLastServicedDate(){
        return lastServicedDate.get();
    }

    public StringProperty getLastServicedDateProperty(){
        return lastServicedDate;
    }

    public void setMileage(String mile){
        mileage.set(mile);
    }

    public String getMileage(){
        return mileage.get();
    }

    public StringProperty getMileageProperty(){
        return mileage;
    }

    public void setCost(String co){
        cost.set(co);
    }

    public String getCost(){
        return cost.get();
    }

    public StringProperty getCostProperty(){
        return cost;
    }
}
