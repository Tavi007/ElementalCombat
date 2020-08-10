package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashMap;
import java.util.HashSet;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.NBTHelper;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import Tavi007.ElementalCombat.loading.EntityData;
import Tavi007.ElementalCombat.loading.GeneralData;
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
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ElementalDefenseCapability {

	@CapabilityInject(IElementalDefense.class)
	public static final Capability<IElementalDefense> ELEMENTAL_DEFENSE_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_defense");

	public static void register() {
		CapabilityManager.INSTANCE.register(IElementalDefense.class, new Capability.IStorage<IElementalDefense>() {

			@Override
			public INBT writeNBT(final Capability<IElementalDefense> capability, final IElementalDefense instance, final Direction side) {

				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				nbt.put("elem_weak", NBTHelper.fromMapToNBT(instance.getElementalWeakness()));
				nbt.put("elem_resi", NBTHelper.fromMapToNBT(instance.getElementalResistance()));
				nbt.put("elem_immu", NBTHelper.fromSetToNBT(instance.getElementalImmunity()));
				nbt.put("elem_abso", NBTHelper.fromSetToNBT(instance.getElementalAbsorption()));
				return nbt;
			}

			@Override
			public void readNBT(final Capability<IElementalDefense> capability, final IElementalDefense instance, final Direction side, final INBT nbt) {

				CompoundNBT nbtCompound = (CompoundNBT)nbt;

				//fill list with data
				instance.setElementalWeakness(NBTHelper.fromNBTToMap(nbtCompound.getCompound("elem_weak")));
				instance.setElementalResistance(NBTHelper.fromNBTToMap(nbtCompound.getCompound("elem_resi")));
				instance.setElementalImmunity(NBTHelper.fromNBTToSet(nbtCompound.getList("elem_immu", Constants.NBT.TAG_STRING ))); //List of strings
				instance.setElementalAbsorption(NBTHelper.fromNBTToSet(nbtCompound.getList("elem_abso", Constants.NBT.TAG_STRING )));
			}


		}, () -> new ElementalDefense());
	}

	public static ICapabilityProvider createProvider(final IElementalDefense atck) {
		return new SerializableCapabilityProvider<>(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING, atck);
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
					ResourceLocation rl = new ResourceLocation(entity.getType().getRegistryName().getNamespace(), "elementalproperties/entities/" + entity.getType().getRegistryName().getPath());
					EntityData entityData = ElementalCombat.DATAMANAGER.getEntityDataFromLocation(rl);

					HashMap<String, Integer> weaknessMap = entityData.getWeaknessMap();
					HashMap<String, Integer> resistanceMap = entityData.getResistanceMap();
					HashSet<String> immunitySet = entityData.getImmunitySet();
					HashSet<String> absorbSet = entityData.getAbsorbSet();

					// player spawn should be biome independent
					if(entityData.getBiomeDependency()) 
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
							resistanceMap.put(biomeProperties,1);
						}
					}

					final ElementalDefense elemDef = new ElementalDefense(weaknessMap, resistanceMap, immunitySet, absorbSet);
					event.addCapability(ID, createProvider(elemDef));
				}
			}
		}

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			ItemStack item = event.getObject();
			ResourceLocation rl = new ResourceLocation(item.getItem().getRegistryName().getNamespace(), "elementalproperties/items/" + item.getItem().getRegistryName().getPath());
			GeneralData itemData = ElementalCombat.DATAMANAGER.getItemDataFromLocation(rl);
			
			//default values
			HashMap<String, Integer> weaknessSet = itemData.getWeaknessMap();
			HashMap<String, Integer> resistanceSet = itemData.getResistanceMap();
			HashSet<String> immunitySet = itemData.getImmunitySet();
			HashSet<String> absorbSet = itemData.getAbsorbSet();

			final ElementalDefense elemDef = new ElementalDefense(weaknessSet, resistanceSet, immunitySet, absorbSet);
			event.addCapability(ID, createProvider(elemDef));
		}
	}
}