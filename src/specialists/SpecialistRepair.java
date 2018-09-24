package specialists;


public class SpecialistRepair {
	protected RepairType repairType;
	protected double price;
	protected String deliveryDate;
	protected String returnDate;
	protected String id;
	protected String spcName;
	
	public enum RepairType {
	   VeHICLE, PART;
	}
	
	public SpecialistRepair(String dDate,String rDate, String id, RepairType repairType, double price, String spcName)	
	{
		this.deliveryDate = dDate;
		this.returnDate = rDate;
		this.id = id;	
		this.repairType = repairType;
		this.price = price;
		this.spcName = spcName;
	}

	public double getPrice()
	{
		return this.price;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}

	public void setSPCName(String spcName)
	{
		this.spcName = spcName;
	}

	public void setDeliveryDate(String deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}
	
	public String getDeliveryDate()
	{
		return this.deliveryDate;
	}
	
	public void setReturnDate(String returnDate)
	{
		this.returnDate = returnDate;
	}
	
	public String getReturnDate()
	{
		return this.returnDate;
	}
	
	public String getID()
	{
		return this.id;
	}
	
	public String getSPCName()
	{
		return this.spcName;
	}
}
