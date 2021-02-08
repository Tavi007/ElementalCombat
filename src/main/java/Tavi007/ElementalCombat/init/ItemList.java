package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.items.FireAndIceSword;
import Tavi007.ElementalCombat.items.HalberdItem;
import Tavi007.ElementalCombat.items.ArmorMaterial;
import Tavi007.ElementalCombat.items.ClockChestplate;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemList {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalCombat.MOD_ID);

	private static Properties singleStack = new Item.Properties().group(ElementalCombat.ELEMENTAL_COMBAT_GROUP).maxStackSize(1);
	private static Properties fullStack = new Item.Properties().group(ElementalCombat.ELEMENTAL_COMBAT_GROUP).maxStackSize(64);
	
	//stuff
	public static final RegistryObject<Item> ESSENCE_FIRE 		= ITEMS.register("essence_fire", () -> new Item(fullStack));
	public static final RegistryObject<Item> ESSENCE_ICE 		= ITEMS.register("essence_ice", () -> new Item(fullStack));
	public static final RegistryObject<Item> ESSENCE_WATER 		= ITEMS.register("essence_water", () -> new Item(fullStack));
	public static final RegistryObject<Item> ESSENCE_THUNDER 	= ITEMS.register("essence_thunder", () -> new Item(fullStack));
	public static final RegistryObject<Item> ESSENCE_DARKNESS 	= ITEMS.register("essence_darkness", () -> new Item(fullStack));
	public static final RegistryObject<Item> ESSENCE_LIGHT 		= ITEMS.register("essence_light", () -> new Item(fullStack));
	public static final RegistryObject<Item> ESSENCE_EARTH 		= ITEMS.register("essence_earth", () -> new Item(fullStack));
	public static final RegistryObject<Item> ESSENCE_WIND 		= ITEMS.register("essence_wind", () -> new Item(fullStack));
	public static final RegistryObject<Item> ESSENCE_FLORA 		= ITEMS.register("essence_flora", () -> new Item(fullStack));

	//weapons
	public static final RegistryObject<Item> WOODEN_SPEAR 	= ITEMS.register("wooden_spear", () -> new SwordItem(ItemTier.WOOD, 3, -2.4F, singleStack));
	public static final RegistryObject<Item> STONE_SPEAR 	= ITEMS.register("stone_spear", () -> new SwordItem(ItemTier.STONE, 3, -2.4F, singleStack));
	public static final RegistryObject<Item> IRON_SPEAR 	= ITEMS.register("iron_spear", () -> new SwordItem(ItemTier.IRON, 3, -2.4F, singleStack));
	public static final RegistryObject<Item> GOLDEN_SPEAR 	= ITEMS.register("golden_spear", () -> new SwordItem(ItemTier.GOLD, 3, -2.4F, singleStack));
	public static final RegistryObject<Item> DIAMOND_SPEAR 	= ITEMS.register("diamond_spear", () -> new SwordItem(ItemTier.DIAMOND, 3, -2.4F, singleStack));
	
	public static final RegistryObject<Item> WOODEN_HALBERD 	= ITEMS.register("wooden_halberd", () -> new HalberdItem(ItemTier.WOOD, 5, -2.8F, singleStack));
	public static final RegistryObject<Item> STONE_HALBERD 		= ITEMS.register("stone_halberd", () -> new HalberdItem(ItemTier.STONE, 5, -2.8F, singleStack));
	public static final RegistryObject<Item> IRON_HALBERD 		= ITEMS.register("iron_halberd", () -> new HalberdItem(ItemTier.IRON, 5, -2.8F, singleStack));
	public static final RegistryObject<Item> GOLDEN_HALBERD 	= ITEMS.register("golden_halberd", () -> new HalberdItem(ItemTier.GOLD, 5, -2.8F, singleStack));
	public static final RegistryObject<Item> DIAMOND_HALBERD 	= ITEMS.register("diamond_halberd", () -> new HalberdItem(ItemTier.DIAMOND, 5, -2.8F, singleStack));
	
	public static final RegistryObject<Item> WATER_STAFF 		= ITEMS.register("water_staff", () -> new ToolItem(6, -1.5F, ItemTier.DIAMOND, null, singleStack));
	public static final RegistryObject<Item> QUAKE_HAMMER 		= ITEMS.register("quake_hammer", () -> new ToolItem(14, -3.0F, ItemTier.DIAMOND, null, singleStack));
	public static final RegistryObject<Item> THORN_CLUB 		= ITEMS.register("thorn_club", () -> new ToolItem(14, -3.0F, ItemTier.DIAMOND, null, singleStack));
	public static final RegistryObject<Item> GUST_SWORD 		= ITEMS.register("gust_sword", () -> new ToolItem(7, -1.2F, ItemTier.DIAMOND, null, singleStack));
	public static final RegistryObject<Item> LIGHTNING_DAGGER 	= ITEMS.register("lightning_dagger", () -> new ToolItem(3, -0.6F, ItemTier.DIAMOND, null, singleStack));
	public static final RegistryObject<Item> VOID_AXE 			= ITEMS.register("void_axe", () -> new AxeItem(ItemTier.DIAMOND, 7, -1.2F, singleStack));
	public static final RegistryObject<Item> LIGHT_BOW 			= ITEMS.register("light_bow", () -> new BowItem(singleStack));
	
	public static final RegistryObject<Item> FIREANDICE_SWORD = ITEMS.register("fire_and_ice_sword", () -> new FireAndIceSword(ItemTier.IRON, 3, -2.4F, singleStack));
	
	//armor
	public static final RegistryObject<Item> CREEPER_CHESTPLATE = ITEMS.register("creeper_chestplate", () -> new ArmorItem(ArmorMaterial.CREEPER, EquipmentSlotType.CHEST, singleStack));
	public static final RegistryObject<Item> CREEPER_LEGGINS 	= ITEMS.register("creeper_leggings", () -> new ArmorItem(ArmorMaterial.CREEPER, EquipmentSlotType.LEGS, singleStack));
	public static final RegistryObject<Item> CREEPER_BOOTS 		= ITEMS.register("creeper_boots", () -> new ArmorItem(ArmorMaterial.CREEPER, EquipmentSlotType.FEET, singleStack));
	
	public static final RegistryObject<Item> CLOCK_CHESTPLATE = ITEMS.register("clock_chestplate", () -> new ClockChestplate(ArmorMaterial.CLOCK, EquipmentSlotType.CHEST, singleStack));
}
