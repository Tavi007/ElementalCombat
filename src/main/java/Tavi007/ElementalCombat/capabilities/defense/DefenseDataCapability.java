package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.NBTHelper;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.loading.GeneralCombatProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome.TempCategory;
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
				nbt.put("defense_style", NBTHelper.fromMapToNBT(instance.getStyleScaling()));
				nbt.put("defense_element", NBTHelper.fromMapToNBT(instance.getElementScaling()));
				return nbt;
			}

			@Override
			public void readNBT(final Capability<IDefenseData> capability, final IDefenseData instance, final Direction side, final INBT nbt) {

				CompoundNBT nbtCompound = (CompoundNBT)nbt;

				//fill list with data
				instance.setStyleScaling(NBTHelper.fromNBTToMap(nbtCompound.getCompound("defense_style")));
				instance.setElementScaling(NBTHelper.fromNBTToMap(nbtCompound.getCompound("defense_element")));
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
					ResourceLocation rl = new ResourceLocation(ElementalCombat.MOD_ID, entity.getType().getRegistryName().getNamespace() + "/entities/" + entity.getType().getRegistryName().getPath());
					EntityCombatProperties entityProperties = ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rl);

					HashMap<String, Double> styleMap = entityProperties.getDefenseStyle();
					HashMap<String, Double> elementMap = entityProperties.getDefenseElement();
					// player spawn should be biome independent
					if(entityProperties.getBiomeDependency()) 
					{
						String biomeProperties = null;
						BlockPos blockPos = new BlockPos(entity.getPositionVec());

						TempCategory category = entity.getEntityWorld().getBiome(blockPos).getTempCategory();
						if(category == TempCategory.COLD){
							biomeProperties = "ice";
						}
						else if(category == TempCategory.WARM){
							biomeProperties = "fire";
						}
						else if(category == TempCategory.OCEAN){
							biomeProperties = "water";
						}
						if(biomeProperties != null){
							elementMap.put(biomeProperties, 0.5);
						}
					}

					final DefenseData defData = new DefenseData(styleMap, elementMap);
					event.addCapability(ID, createProvider(defData));
				}
			}
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			ItemStack item = event.getObject();
			ResourceLocation rl = new ResourceLocation(ElementalCombat.MOD_ID, item.getItem().getRegistryName().getNamespace() + "/items/" + item.getItem().getRegistryName().getPath());
			GeneralCombatProperties itemProperties = ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rl);
			
			//default values
			HashMap<String, Double> styleMap = itemProperties.getDefenseStyle();
			HashMap<String, Double> elementMap = itemProperties.getDefenseElement();

			final DefenseData defData = new DefenseData(styleMap, elementMap);
			event.addCapability(ID, createProvider(defData));
		}
	}
}