package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BlockList {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    static {
        BiConsumer<String, Supplier<Block>> registerConsumer = (name, block) -> {
            BLOCKS.register(name, block);
        };
        ModBlocks.register(registerConsumer);
    }
}
