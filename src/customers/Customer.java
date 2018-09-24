package customers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    
        private final StringProperty customerID;
        private final StringProperty type;
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty address;
	private final StringProperty postcode;
        private final StringProperty phone;
	private final StringProperty emailAddress;
        
        public Customer(String customerID, String type, String firstName, String lastName, String address, String postcode, String phone, String emailAddress)
        {
            this.customerID = new SimpleStringProperty(customerID);
            this.type = new SimpleStringProperty(type);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.address = new SimpleStringProperty(address);
            this.postcode = new SimpleStringProperty(postcode);
            this.phone = new SimpleStringProperty(phone);
            this.emailAddress = new SimpleStringProperty(emailAddress);                    
        }

        public String getCustomerID() {
		return customerID.get();
	}
        
        public StringProperty getCustomerIDProperty(){
            return customerID;
        }                

	public void setCustomerID(String customerID) {
		this.customerID.set(customerID);
	}
        
        public String getType() {
		return type.get();
	}
        
        public StringProperty getTypeProperty(){
            return type;
        }                

	public void setType(String type) {
		this.type.set(type);
	}
        
        
	public String getFirstName() {
		return firstName.get();
	}
        
        public StringProperty getFirstNameProperty(){
            return firstName;
        }                

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
              

	public String getLastName() {
		return lastName.get();
	}
        
        public StringProperty getLastNameProperty(){
            return lastName;
        }  
        
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
        

	public String getAddress() {
		return address.get();
	}

        public StringProperty getAddressProperty(){
                return address;
        }

	public void setAddress(String address) {
		this.address.set(address);
	}
        

	public String getPostcode() {
		return postcode.get();
	}

        public StringProperty getPostcodeProperty(){
                return postcode;
        }
        
	public void setPostcode(String postcode) {
		this.postcode.set(postcode);
	}
        
	public String getPhone() {
		return phone.get();
	}

        public StringProperty getPhoneProperty(){
                return phone;
        }

	public void setPhone(String phone) {
		this.phone.set(phone);
	}
        
        
	public String getEmailAddress() {
		return emailAddress.get();
	}

        public StringProperty getEmailAddressProperty(){
                return emailAddress;
        }

	public void setEmailAddress(String emailAddress) {
		this.emailAddress.set(emailAddress);
	}

}