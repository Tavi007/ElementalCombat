package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockList {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ElementalCombat.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalCombat.MOD_ID);

	private static Properties standardItemProperties = new Item.Properties().group(ElementalCombat.ELEMENTAL_COMBAT_GROUP).maxStackSize(64);
	
	public static final RegistryObject<Block> ESSENCE_BLOCK_FIRE 		= BLOCKS.register("essence_block_fire", () -> new Block(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> ESSENCE_BLOCK_ICE 		= BLOCKS.register("essence_block_ice", () -> new Block(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> ESSENCE_BLOCK_WATER 		= BLOCKS.register("essence_block_water", () -> new Block(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> ESSENCE_BLOCK_THUNDER 	= BLOCKS.register("essence_block_thunder", () -> new Block(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> ESSENCE_BLOCK_DARKNESS 	= BLOCKS.register("essence_block_darkness", () -> new Block(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> ESSENCE_BLOCK_LIGHT 		= BLOCKS.register("essence_block_light", () -> new Block(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> ESSENCE_BLOCK_EARTH 		= BLOCKS.register("essence_block_earth", () -> new Block(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> ESSENCE_BLOCK_WIND 		= BLOCKS.register("essence_block_wind", () -> new Block(Block.Properties.create(Material.IRON)));
	public static final RegistryObject<Block> ESSENCE_BLOCK_FLORA 		= BLOCKS.register("essence_block_flora", () -> new Block(Block.Properties.create(Material.IRON)));
	
	public static final RegistryObject<Item>  ESSENCE_BLOCK_FIRE_ITEM  		= ITEMS.register("essence_block_fire", () -> new BlockItem(ESSENCE_BLOCK_FIRE.get(), standardItemProperties));
	public static final RegistryObject<Item>  ESSENCE_BLOCK_ICE_ITEM  		= ITEMS.register("essence_block_ice", () -> new BlockItem(ESSENCE_BLOCK_ICE.get(), standardItemProperties));
	public static final RegistryObject<Item>  ESSENCE_BLOCK_WATER_ITEM  	= ITEMS.register("essence_block_water", () -> new BlockItem(ESSENCE_BLOCK_WATER.get(), standardItemProperties));
	public static final RegistryObject<Item>  ESSENCE_BLOCK_THUNDER_ITEM  	= ITEMS.register("essence_block_thunder", () -> new BlockItem(ESSENCE_BLOCK_THUNDER.get(), standardItemProperties));
	public static final RegistryObject<Item>  ESSENCE_BLOCK_DARKNESS_ITEM  	= ITEMS.register("essence_block_darkness", () -> new BlockItem(ESSENCE_BLOCK_DARKNESS.get(), standardItemProperties));
	public static final RegistryObject<Item>  ESSENCE_BLOCK_LIGHT_ITEM  	= ITEMS.register("essence_block_light", () -> new BlockItem(ESSENCE_BLOCK_LIGHT.get(), standardItemProperties));
	public static final RegistryObject<Item>  ESSENCE_BLOCK_EARTH_ITEM  	= ITEMS.register("essence_block_earth", () -> new BlockItem(ESSENCE_BLOCK_EARTH.get(), standardItemProperties));
	public static final RegistryObject<Item>  ESSENCE_BLOCK_WIND_ITEM  		= ITEMS.register("essence_block_wind", () -> new BlockItem(ESSENCE_BLOCK_WIND.get(), standardItemProperties));
	public static final RegistryObject<Item>  ESSENCE_BLOCK_FLORA_ITEM  	= ITEMS.register("essence_block_flora", () -> new BlockItem(ESSENCE_BLOCK_FLORA.get(), standardItemProperties));
}
