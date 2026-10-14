package Tavi007.ElementalCombat.common.registry;

import Tavi007.ElementalCombat.common.Constants;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlazedTerracottaBlock;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
    public static final Map<String, Supplier<Block>> BLOCKS = new HashMap<>();

    public static final Supplier<Block> ESSENCE_BLOCK_FIRE = register(Constants.ESSENCE_BLOCK_FIRE, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_ICE = register(Constants.ESSENCE_BLOCK_ICE, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_WATER = register(Constants.ESSENCE_BLOCK_WATER, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_THUNDER = register(Constants.ESSENCE_BLOCK_THUNDER, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_DARKNESS = register(Constants.ESSENCE_BLOCK_DARKNESS, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_LIGHT = register(Constants.ESSENCE_BLOCK_LIGHT, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_EARTH = register(Constants.ESSENCE_BLOCK_EARTH, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_WIND = register(Constants.ESSENCE_BLOCK_WIND, () -> new GlazedTerracottaBlock(Block.Properties.of()));
    public static final Supplier<Block> ESSENCE_BLOCK_FLORA = register(Constants.ESSENCE_BLOCK_FLORA, () -> new GlazedTerracottaBlock(Block.Properties.of()));

    private static Supplier<Block> register(String name, Supplier<Block> supplier) {
        Supplier<Block> memoize = Suppliers.memoize(supplier);
        BLOCKS.put(name, memoize);
        return memoize;
    }
}
