package specialists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RepairCentre {
	private String name;
	private String address;
	private String phone;
	private String email;
	
	private ArrayList<PartSpecialRepair> partRepairList;
	private ArrayList<VehicleSpecialRepair> vehicleRepairList;
	
	public RepairCentre(String name, String address, String phone, String email)
	{
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.partRepairList = new ArrayList<PartSpecialRepair>();
		this.vehicleRepairList = new ArrayList<VehicleSpecialRepair>();
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public String getPhone()
	{
		return this.phone;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public double getTotalCost()
	{
		double total = 0;
		if(this.partRepairList != null){
		for(Iterator<PartSpecialRepair> it = partRepairList.iterator(); it.hasNext();){
			total += it.next().getPrice();
		}
		}
		
		if(this.vehicleRepairList != null){
		for(Iterator<VehicleSpecialRepair> it = vehicleRepairList.iterator(); it.hasNext();){
			total += it.next().getPrice();
		}
		}
		
        return total;		
	}
	
	public int getTotalNumber()
	{
		return this.partRepairList.size()+this.vehicleRepairList.size();
	}

	public List<PartSpecialRepair> getPartRepairList()
	{
		return this.partRepairList;
	}
	
	public List<VehicleSpecialRepair> getVehicleRepairList()
	{
		return this.vehicleRepairList;
	}
	
}
