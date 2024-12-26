package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlazedTerracottaBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModBlocks {
    private static final Map<String, Supplier<Block>> registry = new HashMap<>();
    public static final Supplier<Block> ESSENCE_BLOCK_FIRE = register(Constants.ESSENCE_BLOCK_FIRE, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_ICE = register(Constants.ESSENCE_BLOCK_ICE, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_WATER = register(Constants.ESSENCE_BLOCK_WATER, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_THUNDER = register(Constants.ESSENCE_BLOCK_THUNDER, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_DARKNESS = register(Constants.ESSENCE_BLOCK_DARKNESS, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_LIGHT = register(Constants.ESSENCE_BLOCK_LIGHT, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_EARTH = register(Constants.ESSENCE_BLOCK_EARTH, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_WIND = register(Constants.ESSENCE_BLOCK_WIND, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_FLORA = register(Constants.ESSENCE_BLOCK_FLORA, () -> new GlazedTerracottaBlock(Block.Properties.of()));

    private static Supplier<Block> register(String name, Supplier<Block> block) {
        registry.put(name, block);
        return block;
    }

    public static void register(BiConsumer<String, Supplier<Block>> registerConsumer) {
        registry.forEach(registerConsumer);
    }
}
