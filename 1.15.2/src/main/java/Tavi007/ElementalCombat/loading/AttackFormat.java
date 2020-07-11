package Tavi007.ElementalCombat.loading;

public class AttackFormat
{
	   private final String name;
	   private final Integer value;
	   
	   public AttackFormat()
	   {
		   this.name = "";
		   this.value = 0;
	   }
	   
	   public AttackFormat(String name, Integer value)
	   {
		   this.name = name;
		   this.value = value;
	   }
	   
	   public String getName()
	   {
		   return this.name;
	   }
	   
	   public Integer getValue()
	   {
		   return this.value;
	   }
}
