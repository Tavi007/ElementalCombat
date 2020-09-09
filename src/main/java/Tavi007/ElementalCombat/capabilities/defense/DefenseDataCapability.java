package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
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

	@CapabilityInject(IDefenseData.class)
	public static final Capability<IDefenseData> ELEMENTAL_DEFENSE_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_defense");

	public static void register() {
		CapabilityManager.INSTANCE.register(IDefenseData.class, new Capability.IStorage<IDefenseData>() {

			@Override
			public INBT writeNBT(final Capability<IDefenseData> capability, final IDefenseData instance, final Direction side) {

				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				nbt.put("defense_style", NBTHelper.fromMapToNBT(instance.getStyleFactor()));
				nbt.put("defense_element", NBTHelper.fromMapToNBT(instance.getElementFactor()));
				return nbt;
			}

			@Override
			public void readNBT(final Capability<IDefenseData> capability, final IDefenseData instance, final Direction side, final INBT nbt) {

				CompoundNBT nbtCompound = (CompoundNBT)nbt;

				//fill list with data
				instance.setStyleFactor(NBTHelper.fromNBTToMap(nbtCompound.getCompound("defense_style")));
				instance.setElementFactor(NBTHelper.fromNBTToMap(nbtCompound.getCompound("defense_element")));
			}


		}, () -> new DefenseData());
	}

	public static ICapabilityProvider createProvider(final IDefenseData def) {
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
				if(!entity.getEntityWorld().isRemote()) {
					ResourceLocation rlEntity = entity.getType().getRegistryName();
					ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "entities/" + rlEntity.getNamespace() + "/" + rlEntity.getPath());
					EntityCombatProperties entityProperties = ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties);

					HashMap<String, Integer> styleMap = entityProperties.getDefenseStyle();
					HashMap<String, Integer> elementMap = entityProperties.getDefenseElement();
					// player spawn should be biome independent
					if(entityProperties.getBiomeDependency()) 
					{
						BlockPos blockPos = new BlockPos(entity.getPositionVec());
						ResourceLocation rlBiome = entity.getEntityWorld().getBiome(blockPos).getRegistryName();
						rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "biomes/" + rlBiome.getNamespace() + "/" + rlBiome.getPath()); ;
						BiomeCombatProperties biomeProperties = ElementalCombat.COMBAT_PROPERTIES_MANGER.getBiomeDataFromLocation(rlProperties);
						elementMap = DefenseDataHelper.mergeMaps(elementMap, biomeProperties.getDefenseElement());					
					}
					final DefenseData defData = new DefenseData(styleMap, elementMap);
					event.addCapability(ID, createProvider(defData));
				}
			}
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			ResourceLocation rlItem = event.getObject().getItem().getRegistryName();
			ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "items/" + rlItem.getNamespace() + "/" + rlItem.getPath());
			ItemCombatProperties itemProperties = ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties);

			//default values
			HashMap<String, Integer> styleMap = itemProperties.getDefenseStyle();
			HashMap<String, Integer> elementMap = itemProperties.getDefenseElement();

			final DefenseData defData = new DefenseData(styleMap, elementMap);
			event.addCapability(ID, createProvider(defData));
		}
	}
}