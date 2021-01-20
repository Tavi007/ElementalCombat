package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.items.FireAndIceSword;
import Tavi007.ElementalCombat.items.ClockChestplate;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalCombat.MOD_ID);

	private static Properties singleStack = new Item.Properties().group(ElementalCombat.ELEMENTAL_COMBAT_GROUP).maxStackSize(1);
	
	//special items
	public static final RegistryObject<Item> ELEMENTAL_CHESTPLATE = ITEMS.register("clock_chestplate", () -> new ClockChestplate(ArmorMaterial.IRON, EquipmentSlotType.CHEST, singleStack));
	public static final RegistryObject<Item> FIREANDICE_SWORD = ITEMS.register("fire_and_ice_sword", () -> new FireAndIceSword(ItemTier.IRON, 5, 2.0f, singleStack));

	//normal weapons
	
	//normal armor
}