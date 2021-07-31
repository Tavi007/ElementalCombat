package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.PacketBuffer;

public class BiomeCombatProperties {
	private HashMap<String, Integer> defense_element;
	
	public BiomeCombatProperties() {
		this.defense_element = new HashMap<String, Integer>();
	}

	public BiomeCombatProperties(HashMap<String, Integer> defense_element) {
		this.defense_element = defense_element;
	}
	
	public BiomeCombatProperties(BiomeCombatProperties biomeData) {
		this.defense_element = biomeData.getDefenseElement();
	}

	public HashMap<String, Integer> getDefenseElement() {return this.defense_element;}
	
	public void writeToBuffer(PacketBuffer buf) {
		PacketBufferHelper.writeStringToInt(buf, defense_element);
	}

	public void readFromBuffer(PacketBuffer buf) {
		defense_element = PacketBufferHelper.readStringToInt(buf);
	}
}
