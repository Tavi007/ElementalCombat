package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {

    public static class ElementalCombatItemGroup extends CreativeModeTab {

        public ElementalCombatItemGroup(String name) {
            super(name);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemList.ESSENCE_FIRE.get());
        }
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalCombat.MOD_ID);

    private static Properties fullStack = new Item.Properties().tab(ElementalCombat.ELEMENTAL_COMBAT_GROUP).stacksTo(64);

    // essence
    public static final RegistryObject<Item> ESSENCE_FIRE = ITEMS.register("essence_fire", () -> new Item(fullStack));
    public static final RegistryObject<Item> ESSENCE_ICE = ITEMS.register("essence_ice", () -> new Item(fullStack));
    public static final RegistryObject<Item> ESSENCE_WATER = ITEMS.register("essence_water", () -> new Item(fullStack));
    public static final RegistryObject<Item> ESSENCE_THUNDER = ITEMS.register("essence_thunder", () -> new Item(fullStack));
    public static final RegistryObject<Item> ESSENCE_DARKNESS = ITEMS.register("essence_darkness", () -> new Item(fullStack));
    public static final RegistryObject<Item> ESSENCE_LIGHT = ITEMS.register("essence_light", () -> new Item(fullStack));
    public static final RegistryObject<Item> ESSENCE_EARTH = ITEMS.register("essence_earth", () -> new Item(fullStack));
    public static final RegistryObject<Item> ESSENCE_WIND = ITEMS.register("essence_wind", () -> new Item(fullStack));
    public static final RegistryObject<Item> ESSENCE_FLORA = ITEMS.register("essence_flora", () -> new Item(fullStack));

}
