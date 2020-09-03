package Tavi007.ElementalCombat.capabilities.attack;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.SerializableCapabilityProvider;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.loading.ItemCombatProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class AttackDataCapability {

	@CapabilityInject(IAttackData.class)
	public static final Capability<IAttackData> ELEMENTAL_ATTACK_CAPABILITY = null;

	/**
	 * The default {@link Direction} to use for this capability.
	 */
	public static final Direction DEFAULT_FACING = null;

	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(ElementalCombat.MOD_ID, "attack_data");

	public static void register() {
		CapabilityManager.INSTANCE.register(IAttackData.class, new Capability.IStorage<IAttackData>() {

			@Override
			public INBT writeNBT(final Capability<IAttackData> capability, final IAttackData instance, final Direction side) {

				String style = instance.getStyle();
				String element = instance.getElement();

				//fill nbt with data
				CompoundNBT nbt = new CompoundNBT();
				nbt.put("attack_element", StringNBT.valueOf(element));
				nbt.put("attack_style", StringNBT.valueOf(style));
				return nbt;
			}

			@Override
			public void readNBT(final Capability<IAttackData> capability, final IAttackData instance, final Direction side, final INBT nbt) {

				StringNBT styleNBT = (StringNBT) ((CompoundNBT) nbt).get("attack_style");
				StringNBT elementNBT = (StringNBT) ((CompoundNBT) nbt).get("attack_element");
				//fill list with data
				instance.setStyle(styleNBT.getString());
				instance.setElement(elementNBT.getString());
			}
		}, () -> new AttackData());
	}

	public static ICapabilityProvider createProvider(final IAttackData atck) {
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
			if(!event.getObject().getEntityWorld().isRemote()) {
				if (event.getObject() instanceof LivingEntity) {
					ResourceLocation rlEntity = ((LivingEntity) event.getObject()).getType().getRegistryName();
					ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "entities/" + rlEntity.getNamespace() + "/" + rlEntity.getPath());
					EntityCombatProperties entityProperties = ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties);
					final AttackData atck = new AttackData(entityProperties.getAttackStyle(), entityProperties.getAttackElement());
					event.addCapability(ID, createProvider(atck));
				}
				else if (event.getObject() instanceof ProjectileEntity) {
					final AttackData atck = new AttackData();
					event.addCapability(ID, createProvider(atck));
				}
			}
		}

		@SubscribeEvent
		public static void attachCapabilitiesItem(final AttachCapabilitiesEvent<ItemStack> event) {
			ResourceLocation rlItem = event.getObject().getItem().getRegistryName();
			ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "items/" + rlItem.getNamespace() + "/" + rlItem.getPath());
			ItemCombatProperties itemProperties = ElementalCombat.COMBAT_PROPERTIES_MANGER.getItemDataFromLocation(rlProperties);
			final AttackData atck = new AttackData(itemProperties.getAttackStyle(), itemProperties.getAttackElement());
			event.addCapability(ID, createProvider(atck));
		}
	}
}