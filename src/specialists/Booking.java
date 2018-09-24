package specialists;

public class Booking {
    private String lastServiceDate;
    private String currentMileage;
    
    public Booking(String lastServiceDate, String currentMileage)
    {
	  this.lastServiceDate = lastServiceDate;
	  this.currentMileage = currentMileage; 
    }
  
	public void setLastServiceDate(String lastServiceDate)
	{
		this.lastServiceDate = lastServiceDate;
	}
	
	public String getLastServiceDate()
	{
		return this.lastServiceDate;
	}
	
	public void setCurrentMileage(String currentMileage)
	{
		this.currentMileage = currentMileage; 
	}
	
	public String getCurrentMileage()
	{
		return this.currentMileage;
	}
}
