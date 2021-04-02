package Tavi007.ElementalCombat.loading;

import java.util.HashMap;

public class MobCombatProperties extends ItemCombatProperties {
	private final boolean biome_dependency; 

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
}
