package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.registry.ModEnchantments;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiConsumer;

public class EnchantmentList {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Constants.MOD_ID);

    private static void register() {
        BiConsumer<String, Enchantment> registerConsumer = (name, enchantment) -> {
            ENCHANTMENTS.register(name, () -> enchantment);
        };
        ModEnchantments.register(registerConsumer);
    }
}
