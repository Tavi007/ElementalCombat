package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.defense.ElementalDefenseCapability;
import Tavi007.ElementalCombat.enchantments.ElementalResistanceEnchantment;
import Tavi007.ElementalCombat.enchantments.ElementalWeaponEnchantment;
import Tavi007.ElementalCombat.capabilities.attack.ElementalAttackCapability;
import Tavi007.ElementalCombat.particle.ParticleList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class StartupCommon {
	
	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event){
		ElementalAttackCapability.register();
		ElementalDefenseCapability.register();
		ElementalCombat.LOGGER.info("setup method registered.");
    }
	
	@SubscribeEvent
	public static void registerEnchantments(Register<Enchantment> event) {
		event.getRegistry().register(new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.FIRE).setRegistryName(Enchantments.FIRE_PROTECTION.getRegistryName()));
		event.getRegistry().register(new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.ICE).setRegistryName("ice_protection"));
		event.getRegistry().register(new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.WATER).setRegistryName("water_protection"));
		event.getRegistry().register(new ElementalResistanceEnchantment(ElementalResistanceEnchantment.Type.THUNDER).setRegistryName("thunder_protection"));

		event.getRegistry().register(new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.ICE).setRegistryName("ice_aspect"));
		event.getRegistry().register(new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.WATER).setRegistryName("water_aspect"));
		event.getRegistry().register(new ElementalWeaponEnchantment(ElementalWeaponEnchantment.Type.THUNDER).setRegistryName("thunder_aspect"));

	    ElementalCombat.LOGGER.info("ElementalCombat enchantments registered.");
	}
}
