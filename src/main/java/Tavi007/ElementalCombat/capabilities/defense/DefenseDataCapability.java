package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.NBTHelper;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DefenseDataCapability {

	@CapabilityInject(DefenseData.class)
	public static final Capability<DefenseData> ELEMENTAL_DEFENSE_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_defense");

	public static void register() {
		CapabilityManager.INSTANCE.register(DefenseData.class, new Capability.IStorage<DefenseData>() {

			@Override
			public INBT writeNBT(final Capability<DefenseData> capability, final DefenseData instance, final Direction side) {

				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				nbt.put("defense_style", NBTHelper.fromMapToNBT(instance.getStyleFactor()));
				nbt.put("defense_element", NBTHelper.fromMapToNBT(instance.getElementFactor()));
				nbt.put("enchantment_data", NBTHelper.fromMapToNBT(instance.getEnchantmentData()));
				return nbt;
			}

			@Override
			public void readNBT(final Capability<DefenseData> capability, final DefenseData instance, final Direction side, final INBT nbt) {

				CompoundNBT nbtCompound = (CompoundNBT)nbt;

				//fill list with data
				instance.setStyleFactor(NBTHelper.fromNBTToMap(nbtCompound.getCompound("defense_style")));
				instance.setElementFactor(NBTHelper.fromNBTToMap(nbtCompound.getCompound("defense_element")));
				instance.setEnchantmentData(NBTHelper.fromNBTToMap(nbtCompound.getCompound("enchantment_data")));
			}


		}, () -> new DefenseData());
	}

	public static ICapabilityProvider createProvider(final DefenseData def) {
		return new SerializableCapabilityProvider<>(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING, def);
	}


	/**
	 * Event handler for the {@link IElementalDefense} capability.
	 */
	@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID)
	private static class EventHandler {

		/**
		 * Attach the {@link IElementalDefense} capability to all living entities.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof LivingEntity) {
				LivingEntity entity = (LivingEntity) event.getObject();
				EntityCombatProperties entityProperties = ElementalCombatAPI.getDefaultProperties(entity);

				HashMap<String, Integer> styleMap = new HashMap<String, Integer>(entityProperties.getDefenseStyle());
				HashMap<String, Integer> elementMap = new HashMap<String, Integer>(entityProperties.getDefenseElement());
				// player spawn is usually biome independent
				if(entityProperties.getBiomeDependency()) 
				{
					BlockPos blockPos = new BlockPos(entity.getPositionVec());
					BiomeCombatProperties biomeProperties = ElementalCombatAPI.getDefaultProperties(entity.getEntityWorld().getBiome(blockPos));
					DefenseDataHelper.mergeMaps(elementMap, biomeProperties.getDefenseElement());					
				}
				final DefenseData defData = new DefenseData(styleMap, elementMap);
				event.addCapability(ID, createProvider(defData));
			}
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			ItemCombatProperties itemProperties = ElementalCombatAPI.getDefaultProperties(event.getObject());
			//default values
			HashMap<String, Integer> styleMap = new HashMap<String, Integer>(itemProperties.getDefenseStyle());
			HashMap<String, Integer> elementMap = new HashMap<String, Integer>(itemProperties.getDefenseElement());

			final DefenseData defData = new DefenseData(styleMap, elementMap);
			event.addCapability(ID, createProvider(defData));
		}
	}
}