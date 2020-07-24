package Tavi007.ElementalCombat.capabilities.defense;

import java.util.HashSet;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import Tavi007.ElementalCombat.loading.EntityData;
import Tavi007.ElementalCombat.loading.GeneralData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
				nbt.put("elem_weak", fromSetToNBT(instance.getElementalWeakness()));
				nbt.put("elem_resi", fromSetToNBT(instance.getElementalResistance()));
				nbt.put("elem_immu", fromSetToNBT(instance.getElementalImmunity()));
				nbt.put("elem_abso", fromSetToNBT(instance.getElementalAbsorption()));
				return nbt;
			}

			@Override
			public void readNBT(final Capability<IElementalDefense> capability, final IElementalDefense instance, final Direction side, final INBT nbt) {

				CompoundNBT nbtCompound = (CompoundNBT)nbt;

				//fill list with data
				instance.setElementalWeakness((HashSet<String>) fromNBTToSet(nbtCompound.getList("elem_weak", nbt.getId() )));
				instance.setElementalWeakness((HashSet<String>) fromNBTToSet(nbtCompound.getList("elem_resi", nbt.getId() )));
				instance.setElementalWeakness((HashSet<String>) fromNBTToSet(nbtCompound.getList("elem_immu", nbt.getId() )));
				instance.setElementalWeakness((HashSet<String>) fromNBTToSet(nbtCompound.getList("elem_abso", nbt.getId() )));
			}
			

			private Set<String> fromNBTToSet(ListNBT nbt)
		    {
		    	Set<String> set = new HashSet<String>();
		    	if(nbt!=null)
		    	{
			    	for (INBT item : nbt)
			    	{
			    		set.add(item.toString());
			    	}
		    	}
		    	return set;
		    }
		    
		    private ListNBT fromSetToNBT(Set<String> set)
		    {
		    	ListNBT nbt = new ListNBT();
		    	if(set != null)
		    	{
			    	for (String item : set) 
			    	{	
			    		nbt.add(StringNBT.valueOf(item));
			    	}
		    	}
		    	return nbt;
		    }
			
			
		}, () -> new ElementalDefense());
	}

	public static LazyOptional<IElementalDefense> getElementalDefense(final LivingEntity entity) {
		return entity.getCapability(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING);
	}

	//	public static LazyOptional<IElementalDefense> getElementalDefense(final ProjectileEntity entity) {
	//		return entity.getCapability(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING);
	//	}

	public static LazyOptional<IElementalDefense> getElementalDefense(final ItemStack item) {
		return item.getCapability(ELEMENTAL_DEFENSE_CAPABILITY, DEFAULT_FACING);
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
				ResourceLocation rl = new ResourceLocation(entity.getType().getRegistryName().getNamespace(), "elementalproperties/entities/" + entity.getType().getRegistryName().getPath());
				EntityData entityData = ElementalCombat.DATAMANAGER.getEntityDataFromLocation(rl);

				HashSet<String> weaknessSet = entityData.getWeaknessSet();
				HashSet<String> resistanceSet = entityData.getResistanceSet();
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
						resistanceSet.add(biomeProperties);
					}
				}
				
				final ElementalDefense elemDef = new ElementalDefense(weaknessSet, resistanceSet, immunitySet, absorbSet);
				event.addCapability(ID, createProvider(elemDef));
			}
			//			else if(event.getObject() instanceof ProjectileEntity) {
			//				
			//			}
		}

		@SubscribeEvent
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			ItemStack item = event.getObject();
			ResourceLocation rl = new ResourceLocation(item.getItem().getRegistryName().getNamespace(), "elementalproperties/items/" + item.getItem().getRegistryName().getPath());
			GeneralData itemData = ElementalCombat.DATAMANAGER.getItemDataFromLocation(rl);

			HashSet<String> weaknessSet = itemData.getWeaknessSet();
			HashSet<String> resistanceSet = itemData.getResistanceSet();
			HashSet<String> immunitySet = itemData.getImmunitySet();
			HashSet<String> absorbSet = itemData.getAbsorbSet();
			

			final ElementalDefense elemDef = new ElementalDefense(weaknessSet, resistanceSet, immunitySet, absorbSet);
			event.addCapability(ID, createProvider(elemDef));
		}
	}
}