package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.capabilities.defense.DefenseDataCapability;
import Tavi007.ElementalCombat.enchantments.ElementalResistanceEnchantment;
import Tavi007.ElementalCombat.enchantments.ElementalWeaponEnchantment;
import Tavi007.ElementalCombat.enchantments.StyleResistanceEnchantment;
import Tavi007.ElementalCombat.network.EntityMessage;
import Tavi007.ElementalCombat.network.PackageHandlerOnClient;
import Tavi007.ElementalCombat.network.PackageHandlerOnServer;

import java.util.Optional;

import Tavi007.ElementalCombat.capabilities.attack.AttackDataCapability;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;

public class StartupCommon {
	private static final byte DEFENSEDATA_MESSAGE_TO_CLIENT_ID = 1; 
	public static final String MESSAGE_PROTOCOL_VERSION = "1.0"; 
	
	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event){
		//capabilities
		AttackDataCapability.register();
		DefenseDataCapability.register();

		//networking
		ElementalCombat.simpleChannel = NetworkRegistry.newSimpleChannel(ElementalCombat.simpleChannelRL, () -> MESSAGE_PROTOCOL_VERSION,
				PackageHandlerOnClient::isThisProtocolAcceptedByClient,
				PackageHandlerOnServer::isThisProtocolAcceptedByServer);
		
		ElementalCombat.simpleChannel.registerMessage(DEFENSEDATA_MESSAGE_TO_CLIENT_ID, EntityMessage.class,
				EntityMessage::encode, EntityMessage::decode,
				PackageHandlerOnClient::onMessageReceived,
	            Optional.of(NetworkDirection.PLAY_TO_CLIENT));
		
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

		event.getRegistry().register(new StyleResistanceEnchantment(StyleResistanceEnchantment.Type.PROJECTILE).setRegistryName(Enchantments.PROJECTILE_PROTECTION.getRegistryName()));
		event.getRegistry().register(new StyleResistanceEnchantment(StyleResistanceEnchantment.Type.EXPLOSION).setRegistryName(Enchantments.BLAST_PROTECTION.getRegistryName()));

		ElementalCombat.LOGGER.info("Elemental Combat enchantments registered.");
	}
}
