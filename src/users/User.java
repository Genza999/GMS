package users;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author kisekkaDavid
 */

public class User {
    protected StringProperty username;
    protected StringProperty password;
    protected StringProperty firstname;
    protected StringProperty surname;
    protected StringProperty type;
    
    public User(String un,String pass,String fn,String sn,String tp){
        username = new SimpleStringProperty(un);
        password = new SimpleStringProperty(pass);
        firstname = new SimpleStringProperty(fn);
        surname = new SimpleStringProperty(sn);
        type = new SimpleStringProperty(tp);    
    }
    
    public void setUsername(String u){
        username.set(u);
    }
    
    public String getUsername(){
        return username.get();
    }
    
    public StringProperty getUsernameProperty(){
        return username;
    }

    public void setPassword(String pw){
        password.set(pw);
    }

    public String getPassword(){
        return password.get();
    }
    
     public StringProperty getPasswordProperty(){
        return password;
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
    
    public void setFirstname(String fn){
        firstname.set(fn);
    }

    public String getFirstname(){
        return firstname.get();
    }
    
     public StringProperty getFirstnameProperty(){
        return firstname;
    }

    public void setSurname(String sn){
        surname.set(sn);
    }

    public String getSurname(){
        return surname.get();
    }
    
    public StringProperty getSurnameProperty(){
        return surname;
    }
}

