package Tavi007.ElementalCombat.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabList {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ElementalCombat.MOD_ID);

    public static final List<Supplier<? extends ItemLike>> TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.elementalcombat"))
            .icon(ItemList.ESSENCE_FIRE.get()::getDefaultInstance)
            .displayItems((displayParams, output) -> TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
            .withSearchBar()
            .build());

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        TAB_ITEMS.add(itemLike);
        return itemLike;
    }

    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == TAB.getKey()) {
            event.accept(ItemList.ESSENCE_FIRE.get());
            event.accept(ItemList.ESSENCE_ICE.get());
            event.accept(ItemList.ESSENCE_WATER.get());
            event.accept(ItemList.ESSENCE_THUNDER.get());
            event.accept(ItemList.ESSENCE_DARKNESS.get());
            event.accept(ItemList.ESSENCE_LIGHT.get());
            event.accept(ItemList.ESSENCE_EARTH.get());
            event.accept(ItemList.ESSENCE_WIND.get());
            event.accept(ItemList.ESSENCE_FLORA.get());

            event.accept(BlockList.ESSENCE_BLOCK_FIRE_ITEM.get());
            event.accept(BlockList.ESSENCE_BLOCK_ICE_ITEM.get());
            event.accept(BlockList.ESSENCE_BLOCK_WATER_ITEM.get());
            event.accept(BlockList.ESSENCE_BLOCK_THUNDER_ITEM.get());
            event.accept(BlockList.ESSENCE_BLOCK_DARKNESS_ITEM.get());
            event.accept(BlockList.ESSENCE_BLOCK_LIGHT_ITEM.get());
            event.accept(BlockList.ESSENCE_BLOCK_EARTH_ITEM.get());
            event.accept(BlockList.ESSENCE_BLOCK_WIND_ITEM.get());
            event.accept(BlockList.ESSENCE_BLOCK_FLORA_ITEM.get());
        }
    }
}
