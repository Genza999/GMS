package specialists;

public class PartSpecialRepair extends SpecialistRepair {
    private String name;
    private String description;
    private String idNumber;
    
	public PartSpecialRepair(String dDate,String rDate, String id, double repairPrice, String pName, String pDes, String pIdNum,String spc)
	{
	   super(dDate, rDate, id, SpecialistRepair.RepairType.PART, repairPrice, spc);
       this.name = pName;
       this.description = pDes;
       this.idNumber = pIdNum;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public void setIdNumber(String idNumber)
	{
		this.idNumber = idNumber;
	}
	
	public String getIdNumber()
	{
		return this.idNumber;
	}	

}
