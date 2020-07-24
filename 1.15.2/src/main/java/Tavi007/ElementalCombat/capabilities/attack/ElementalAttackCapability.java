package Tavi007.ElementalCombat.capabilities.attack;

import java.util.HashMap;
import java.util.Set;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import Tavi007.ElementalCombat.loading.AttackFormat;
import Tavi007.ElementalCombat.loading.EntityData;
import Tavi007.ElementalCombat.loading.GeneralData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ElementalAttackCapability {

	@CapabilityInject(IElementalAttack.class)
	public static final Capability<IElementalAttack> ELEMENTAL_ATTACK_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "elemental_attack");

	public static void register() {
		CapabilityManager.INSTANCE.register(IElementalAttack.class, new Capability.IStorage<IElementalAttack>() {

			@Override
			public INBT writeNBT(final Capability<IElementalAttack> capability, final IElementalAttack instance, final Direction side) {

				HashMap<String, Integer> atckMap = instance.getElementalAttack();

				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				CompoundNBT nbt_inner = new CompoundNBT();

				if(atckMap != null)
				{
					atckMap.forEach((elemString, value) ->
					{
						nbt_inner.putInt(elemString, value);
					});
				}
				nbt.put("elem_atck", nbt_inner);
				return nbt;
			}

			@Override
			public void readNBT(final Capability<IElementalAttack> capability, final IElementalAttack instance, final Direction side, final INBT nbt) {

				CompoundNBT nbtCompound = (CompoundNBT)nbt;

				//fill list with data
				HashMap<String, Integer> atckMap = new HashMap<String, Integer>();
				CompoundNBT nbtCompound_inner = nbtCompound.getCompound("elem_atck");
				if(nbtCompound_inner!=null)
				{
					Set<String> keySet = nbtCompound_inner.keySet();
					for (String key : keySet)
					{ 
						int value = nbtCompound_inner.getInt(key);
						atckMap.put(key, value);
					}
				}
				instance.setElementalAttack(atckMap);
			}
		}, () -> new ElementalAttack());
	}

	public static LazyOptional<IElementalAttack> getElementalAttack(final LivingEntity entity) {
		return entity.getCapability(ELEMENTAL_ATTACK_CAPABILITY, DEFAULT_FACING);
	}

	//	public static LazyOptional<IElementalAttack> getElementalAttack(final ProjectileEntity entity) {
	//		return entity.getCapability(ELEMENTAL_ATTACK_CAPABILITY, DEFAULT_FACING);
	//	}

	public static LazyOptional<IElementalAttack> getElementalAttack(final ItemStack item) {
		return item.getCapability(ELEMENTAL_ATTACK_CAPABILITY, DEFAULT_FACING);
	}

	public static ICapabilityProvider createProvider(final IElementalAttack atck) {
		return new SerializableCapabilityProvider<>(ELEMENTAL_ATTACK_CAPABILITY, DEFAULT_FACING, atck);
	}


	/**
	 * Event handler for the {@link IElementalAttack} capability.
	 */
	@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID)
	private static class EventHandler {

		/**
		 * Attach the {@link IElementalAttack} capability to all living entities.
		 *
		 * @param event The event
		 */
		@SubscribeEvent
		public static void attachCapabilitiesEntity(final AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof LivingEntity) {

				LivingEntity entity = (LivingEntity) event.getObject();
				ResourceLocation rl = new ResourceLocation(entity.getType().getRegistryName().getNamespace(), "elementalproperties/entities/" + entity.getType().getRegistryName().getPath());
				EntityData entityData = ElementalCombat.DATAMANAGER.getEntityDataFromLocation(rl);

				// rewrite set to mapping
				Set<AttackFormat> attackFormatSet = entityData.getAttackSet();
				HashMap<String, Integer> attackMap = new HashMap<String, Integer>();
				attackFormatSet.forEach((attack) ->
				{
					Integer value = attack.getValue();
					if (value <= 0){
						ElementalCombat.LOGGER.info("Elemental damage value of " + attack.getName() + " for " + entity.getName().toString() + " is <= 0. Using 1 instead.");
						value = 1;
					}
					attackMap.put(attack.getName(), value);
				});

				final ElementalAttack elemAtck = new ElementalAttack(attackMap);
				event.addCapability(ID, createProvider(elemAtck));
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

			// rewrite set to mapping
			Set<AttackFormat> attackFormatSet = itemData.getAttackSet();
			HashMap<String, Integer> attackMap = new HashMap<String, Integer>();
			attackFormatSet.forEach((attack) ->
			{
				Integer value = attack.getValue();
				if (value <= 0){
					ElementalCombat.LOGGER.info("Elemental damage value of " + attack.getName() + " for " + item.getItem().getName().toString() + " is <= 0. Using 1 instead.");
					value = 1;
				}
				attackMap.put(attack.getName(), value);
			});

			final ElementalAttack elemAtck = new ElementalAttack(attackMap);
			event.addCapability(ID, createProvider(elemAtck));
		}
	}
}