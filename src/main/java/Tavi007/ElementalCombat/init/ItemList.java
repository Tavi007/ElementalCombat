package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.items.LensItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemList {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ElementalCombat.MOD_ID);

    private static Properties fullStack = new Item.Properties().stacksTo(64);

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

    public static final RegistryObject<Item> LENS = ITEMS.register("combat_data_lens", () -> new LensItem(new Item.Properties().stacksTo(1)));

    @SubscribeEvent
    public static void onRegisterCreativeTabEvent(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(ElementalCombat.MOD_ID, "items"),
            builder -> builder

                .title(Component.translatable("itemGroup.elementalcombat"))
                .icon(() -> new ItemStack(ItemList.ESSENCE_FIRE.get()))
                .displayItems((tab, output) -> {
                    output.accept(ItemList.ESSENCE_FIRE.get());
                    output.accept(ItemList.ESSENCE_ICE.get());
                    output.accept(ItemList.ESSENCE_WATER.get());
                    output.accept(ItemList.ESSENCE_THUNDER.get());
                    output.accept(ItemList.ESSENCE_DARKNESS.get());
                    output.accept(ItemList.ESSENCE_LIGHT.get());
                    output.accept(ItemList.ESSENCE_EARTH.get());
                    output.accept(ItemList.ESSENCE_WIND.get());
                    output.accept(ItemList.ESSENCE_FLORA.get());

                    output.accept(BlockList.ESSENCE_BLOCK_FIRE_ITEM.get());
                    output.accept(BlockList.ESSENCE_BLOCK_ICE_ITEM.get());
                    output.accept(BlockList.ESSENCE_BLOCK_WATER_ITEM.get());
                    output.accept(BlockList.ESSENCE_BLOCK_THUNDER_ITEM.get());
                    output.accept(BlockList.ESSENCE_BLOCK_DARKNESS_ITEM.get());
                    output.accept(BlockList.ESSENCE_BLOCK_LIGHT_ITEM.get());
                    output.accept(BlockList.ESSENCE_BLOCK_EARTH_ITEM.get());
                    output.accept(BlockList.ESSENCE_BLOCK_WIND_ITEM.get());
                    output.accept(BlockList.ESSENCE_BLOCK_FLORA_ITEM.get());

                    output.accept(ItemList.LENS.get());
                }));
    }
}
