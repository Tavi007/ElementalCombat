package Tavi007.ElementalCombat.common.init;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.common.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiConsumer;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    
    private static void register() {
        BiConsumer<String, Item> registerConsumer = (name, item) -> {
            CreativeTabList.addToTab(ITEMS.register(name, () -> item));
        };
        ModItems.register(registerConsumer);
    }

}
