package Tavi007.ElementalCombat.items;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

public class ElementalChestplate extends  ArmorItem {

	String[] elementMode = {"light", "dark"};
	
	public ElementalChestplate(IArmorMaterial materialIn, EquipmentSlotType slot, Properties p_i48534_3_) {
		super(materialIn, slot, p_i48534_3_);
	}
	
	public void setDefense(long time) {
		// time: 18000 = midnight; 6000 = noon; 12000 and 0 (or 24000) halfway points
		int factor = (int) Math.sin(time * Math.PI/12000) * 75;
		if(time>0 && time<12000) {
			//daytime
			
		}
		else {
			
		}
		
	}
	
}
