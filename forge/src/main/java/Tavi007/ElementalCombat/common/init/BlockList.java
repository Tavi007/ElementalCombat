package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockList {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final RegistryObject<Block> ESSENCE_BLOCK_FIRE = BLOCKS.register(Constants.ESSENCE_BLOCK_FIRE,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final RegistryObject<Block> ESSENCE_BLOCK_ICE = BLOCKS.register(Constants.ESSENCE_BLOCK_ICE,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final RegistryObject<Block> ESSENCE_BLOCK_WATER = BLOCKS.register(Constants.ESSENCE_BLOCK_WATER,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final RegistryObject<Block> ESSENCE_BLOCK_THUNDER = BLOCKS.register(Constants.ESSENCE_BLOCK_THUNDER,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final RegistryObject<Block> ESSENCE_BLOCK_DARKNESS = BLOCKS.register(Constants.ESSENCE_BLOCK_DARKNESS,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final RegistryObject<Block> ESSENCE_BLOCK_LIGHT = BLOCKS.register(Constants.ESSENCE_BLOCK_LIGHT,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final RegistryObject<Block> ESSENCE_BLOCK_EARTH = BLOCKS.register(Constants.ESSENCE_BLOCK_EARTH,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final RegistryObject<Block> ESSENCE_BLOCK_WIND = BLOCKS.register(Constants.ESSENCE_BLOCK_WIND,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final RegistryObject<Block> ESSENCE_BLOCK_FLORA = BLOCKS.register(Constants.ESSENCE_BLOCK_FLORA,
            () -> new GlazedTerracottaBlock(Block.Properties.of()));
    private static final Properties standardItemProperties = new Item.Properties().stacksTo(64);
    public static final RegistryObject<Item> ESSENCE_BLOCK_FIRE_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_FIRE,
            () -> new BlockItem(ESSENCE_BLOCK_FIRE.get(), standardItemProperties)));
    public static final RegistryObject<Item> ESSENCE_BLOCK_ICE_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_ICE,
            () -> new BlockItem(ESSENCE_BLOCK_ICE.get(), standardItemProperties)));
    public static final RegistryObject<Item> ESSENCE_BLOCK_WATER_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_WATER,
            () -> new BlockItem(ESSENCE_BLOCK_WATER.get(), standardItemProperties)));
    public static final RegistryObject<Item> ESSENCE_BLOCK_THUNDER_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_THUNDER,
            () -> new BlockItem(ESSENCE_BLOCK_THUNDER.get(), standardItemProperties)));
    public static final RegistryObject<Item> ESSENCE_BLOCK_DARKNESS_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_DARKNESS,
            () -> new BlockItem(ESSENCE_BLOCK_DARKNESS.get(), standardItemProperties)));
    public static final RegistryObject<Item> ESSENCE_BLOCK_LIGHT_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_LIGHT,
            () -> new BlockItem(ESSENCE_BLOCK_LIGHT.get(), standardItemProperties)));
    public static final RegistryObject<Item> ESSENCE_BLOCK_EARTH_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_EARTH,
            () -> new BlockItem(ESSENCE_BLOCK_EARTH.get(), standardItemProperties)));
    public static final RegistryObject<Item> ESSENCE_BLOCK_WIND_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_WIND,
            () -> new BlockItem(ESSENCE_BLOCK_WIND.get(), standardItemProperties)));
    public static final RegistryObject<Item> ESSENCE_BLOCK_FLORA_ITEM = CreativeTabList.addToTab(ITEMS.register(Constants.ESSENCE_BLOCK_FLORA,
            () -> new BlockItem(ESSENCE_BLOCK_FLORA.get(), standardItemProperties)));
}
