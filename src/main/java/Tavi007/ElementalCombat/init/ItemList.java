package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.items.ElementalChestplate;
import Tavi007.ElementalCombat.items.FireAndIceSword;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalCombat.MOD_ID);

	public static final RegistryObject<Item> ELEMENTAL_CHESTPLATE = ITEMS.register("elemental_chestplate", () -> new ElementalChestplate(ArmorMaterial.IRON, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT)));
	public static final RegistryObject<Item> ELEMENTAL_SWORD = ITEMS.register("fire_and_ice_sword", () -> new FireAndIceSword(ItemTier.IRON, 5, 2.0f, new Item.Properties().group(ItemGroup.COMBAT)));

}
