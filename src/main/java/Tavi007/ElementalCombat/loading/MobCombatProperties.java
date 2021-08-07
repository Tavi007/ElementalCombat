package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

import Tavi007.ElementalCombat.util.PacketBufferHelper;
import net.minecraft.network.PacketBuffer;

public class MobCombatProperties extends ItemCombatProperties  {
	private boolean biome_dependency; 

	public MobCombatProperties(HashMap<String, Integer> defenseStyle, HashMap<String, Integer> defenseElement, String attackStyle, String attackElement, boolean biomeDependency)
	{
		super(defenseStyle, defenseElement, attackStyle, attackElement);
		this.biome_dependency = biomeDependency;
	}

	public MobCombatProperties()
	{
		super();
		this.biome_dependency = false;
	}

	public MobCombatProperties(MobCombatProperties entityData) {
		super(entityData.getDefenseStyle(), entityData.getDefenseElement(), entityData.getAttackStyle(), entityData.getAttackElement());
		this.biome_dependency = entityData.biome_dependency;
	}

	public boolean getBiomeDependency()
	{
		return this.biome_dependency;
	}

	@Override
	public void writeToBuffer(PacketBuffer buf) {
		PacketBufferHelper.writeStringToInt(buf, defense_style);
		PacketBufferHelper.writeStringToInt(buf, defense_element);
		buf.writeString(attack_style);
		buf.writeString(attack_element);
		buf.writeBoolean(biome_dependency);
	}

	@Override
	public void readFromBuffer(PacketBuffer buf) {
		defense_style = PacketBufferHelper.readStringToInt(buf);
		defense_element = PacketBufferHelper.readStringToInt(buf);
		attack_style = buf.readString();
		attack_element = buf.readString();
		biome_dependency = buf.readBoolean();
	}
	
	@Override
	public String toString() {
		return "\nDefStyle=" + defense_style.toString()
		+ "\nDefElement=" + defense_element.toString()
		+ "\nAtckStyle=" + attack_style.toString()
		+ "\nAtckElement=" + attack_element.toString()
		+ "\nBiomeDepend=" + biome_dependency;
	}

}
