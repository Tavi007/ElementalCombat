package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.registry.ModCreativeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;

public class CreativeTabList {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    static {
        ModCreativeTabs.TAB_MAP.forEach(TABS::register);
    }
}