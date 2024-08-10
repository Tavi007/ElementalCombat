package Tavi007.ElementalCombat;

import Tavi007.ElementalCombat.common.Constants;
import Tavi007.ElementalCombat.compatibility.curios.HandleCuriosInventory;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.data.CombatPropertiesManager;
import Tavi007.ElementalCombat.init.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("elementalcombat")
public class ElementalCombat {

    public static ElementalCombat instance;
    public static CombatPropertiesManager COMBAT_PROPERTIES_MANGER = new CombatPropertiesManager();
    public static IEventBus MOD_EVENT_BUS;

    @SuppressWarnings("deprecation")
    public ElementalCombat() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();

        // config (cause they must be in the main class)
        ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CONFIG_SPEC, Constants.MOD_ID + "-client.toml");
        ModLoadingContext.get().registerConfig(Type.SERVER, ServerConfig.CONFIG_SPEC, Constants.MOD_ID + "-server.toml");

        // register
        ParticleList.PARTICLES.register(ElementalCombat.MOD_EVENT_BUS);

        EnchantmentList.ENCHANTMENTS.register(ElementalCombat.MOD_EVENT_BUS);
        MOD_EVENT_BUS.register(EnchantmentList.class);

        PotionList.POTION_EFFECTS.register(ElementalCombat.MOD_EVENT_BUS);
        PotionList.POTION_TYPES.register(ElementalCombat.MOD_EVENT_BUS);
        MOD_EVENT_BUS.register(PotionList.class);

        ItemList.ITEMS.register(ElementalCombat.MOD_EVENT_BUS);

        BlockList.ITEMS.register(ElementalCombat.MOD_EVENT_BUS);
        BlockList.BLOCKS.register(ElementalCombat.MOD_EVENT_BUS);

        CreativeTabList.TABS.register(ElementalCombat.MOD_EVENT_BUS);

        // register common stuff
        MOD_EVENT_BUS.register(StartupCommon.class);
        if (ModList.get().isLoaded("curios")) {
            MinecraftForge.EVENT_BUS.register(HandleCuriosInventory.class);
        }

        // register client only stuff
        DistExecutor.runWhenOn(Dist.CLIENT, () -> ElementalCombat::registerClientOnly);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void registerClientOnly() {
        MOD_EVENT_BUS.register(StartupClientOnly.class);
    }
}
