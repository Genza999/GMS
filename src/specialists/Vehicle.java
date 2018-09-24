package specialists;



import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

class Vehicle{
    protected StringProperty owner;
    protected StringProperty registrationNumber;
    protected StringProperty type;
    protected StringProperty model;
    protected StringProperty make;
    protected StringProperty engineSize;
    protected StringProperty fuelType;
    protected StringProperty colour;
    protected StringProperty motRenewalDate;
    protected StringProperty isUnderWarranty;
    protected StringProperty companyName;
    protected StringProperty companyAddress;
    protected StringProperty expirationDate;
    
    public Vehicle(String o,String regNum,String t,String mod,String mk,String engSize,String fuelTp,String col,String mot,String iuw,String cn,String ca,String ed){
        owner = new SimpleStringProperty(o);
        registrationNumber = new SimpleStringProperty(regNum);
        type = new SimpleStringProperty(t);
        model = new SimpleStringProperty(mod);
        make = new SimpleStringProperty(mk);
        engineSize = new SimpleStringProperty(engSize);
        fuelType = new SimpleStringProperty(fuelTp);
        colour = new SimpleStringProperty(col);
        motRenewalDate = new SimpleStringProperty(mot);
        isUnderWarranty = new SimpleStringProperty(iuw);
        companyName = new SimpleStringProperty(cn);
        companyAddress = new SimpleStringProperty(ca);
        expirationDate = new SimpleStringProperty(ed);
    }
    
    public void setOwner(String o){
        owner.set(o);
    }
    
    public String getOwner(){
        return owner.get();
    }
    
    public StringProperty getOwnerProperty(){
        return owner;
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

    public void setType(String t){
        type.set(t);
    }
    
    public String getType(){
        return type.get();
    }
    
     public StringProperty getTypeProperty(){
        return type;
    }
    
    public void setModel(String mod){
        model.set(mod);
    }

    public String getModel(){
        return model.get();
    }
    
     public StringProperty getModelProperty(){
        return model;
    }

    public void setMake(String m){
        make.set(m);
    }

    public String getMake(){
        return make.get();
    }
    
    public StringProperty getMakeProperty(){
        return make;
    }
     
    public void setEngineSize(String size){
        engineSize.set(size);
    }

    public String getEngineSize(){
        return engineSize.get();
    }
    
    public StringProperty getEngineSizeProperty(){
        return engineSize;
    }

    public void setFuelType(String type){
        fuelType.set(type);
    }

    public String getFuelType(){
        return fuelType.get();
    }
    
    public StringProperty getFuelTypeProperty(){
        return fuelType;
    }

    public void setColour(String c){
        colour.set(c);
    }

    public String getColour(){
        return colour.get();
    }
    
    public StringProperty getColourProperty(){
        return colour;
    }

    public void setRenewalDate(String date){
        motRenewalDate.set(date);
    }

    public String getRenewalDate(){
        return motRenewalDate.get();
    }
    
    public StringProperty getRenewalDateProperty(){
        return motRenewalDate;
    }
    
    public void setIsUnderWarranty(String uw){
        isUnderWarranty.set(uw);
    }

    public String getIsUnderWarranty(){
        return isUnderWarranty.get();
    }
    
    public StringProperty getIsUnderWarrantyProperty(){
        return isUnderWarranty;
    }
    
    public void setCompanyName(String cn){
        companyName.set(cn);
    }

    public String getCompanyName(){
        return companyName.get();
    }
    
    public StringProperty getCompanyNameProperty(){
        return companyName;
    }
    
    public void setCompanyAddress(String a){
        companyAddress.set(a);
    }

    public String getCompanyAddress(){
        return companyAddress.get();
    }
    
    public StringProperty getCompanyAddressProperty(){
        return companyAddress;
    }

    public void setExpirationDate(String date){
        expirationDate.set(date);
    }

    public String getExpirationDate(){
        return expirationDate.get();
    }
    
    public StringProperty getExpirationDateProperty(){
        return expirationDate;
    }
}

