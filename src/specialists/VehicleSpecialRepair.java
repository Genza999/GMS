package specialists;

public class VehicleSpecialRepair extends SpecialistRepair {
    private String vehicleRegNum;
    
	public VehicleSpecialRepair(String dDate,String rDate, String id, double cost, String vehicleRegNum, String spc)
	{
	   super(dDate, rDate, id, SpecialistRepair.RepairType.VeHICLE, cost, spc);
	   this.vehicleRegNum = vehicleRegNum;
	}
	
	public String getVehicleRegNum()
	{
		return this.vehicleRegNum;
	}
	
	public void setVehicleRegNum(String vehicleRegNum)
	{
		this.vehicleRegNum = vehicleRegNum;
	}	
}
