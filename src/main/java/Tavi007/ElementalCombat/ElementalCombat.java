package Tavi007.ElementalCombat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.data.CombatPropertiesManager;
import Tavi007.ElementalCombat.init.BlockList;
import Tavi007.ElementalCombat.init.EnchantmentList;
import Tavi007.ElementalCombat.init.ItemList;
import Tavi007.ElementalCombat.init.ParticleList;
import Tavi007.ElementalCombat.init.PotionList;
import Tavi007.ElementalCombat.init.StartupClientOnly;
import Tavi007.ElementalCombat.init.StartupCommon;
import Tavi007.ElementalCombat.interaction.HandleCuriosInventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

@Mod("elementalcombat")
public class ElementalCombat {

    public static ElementalCombat instance;
    public static final String MOD_ID = "elementalcombat";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static CombatPropertiesManager COMBAT_PROPERTIES_MANGER = new CombatPropertiesManager();
    public static IEventBus MOD_EVENT_BUS;
    public static final CreativeModeTab ELEMENTAL_COMBAT_GROUP = new ItemList.ElementalCombatItemGroup(MOD_ID);

    public static final ResourceLocation simpleChannelRL = new ResourceLocation(MOD_ID, "channel");
    public static SimpleChannel simpleChannel; // used to transmit your network messages

    @SuppressWarnings("deprecation")
    public ElementalCombat() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();

        // config (cause they must be in the main class)
        ModLoadingContext.get().registerConfig(Type.CLIENT, ClientConfig.CONFIG_SPEC, ElementalCombat.MOD_ID + "-client.toml");
        ModLoadingContext.get().registerConfig(Type.SERVER, ServerConfig.CONFIG_SPEC, ElementalCombat.MOD_ID + "-server.toml");

        // register
        ParticleList.PARTICLES.register(ElementalCombat.MOD_EVENT_BUS);

        EnchantmentList.ENCHANTMENTS.register(ElementalCombat.MOD_EVENT_BUS);
        MOD_EVENT_BUS.register(EnchantmentList.class);

        PotionList.POTION_EFFECTS.register(ElementalCombat.MOD_EVENT_BUS);
        PotionList.POTION_TYPES.register(ElementalCombat.MOD_EVENT_BUS);
        MOD_EVENT_BUS.register(PotionList.class);

        ItemList.ITEMS.register(ElementalCombat.MOD_EVENT_BUS);
        MOD_EVENT_BUS.register(ItemList.class);

        BlockList.ITEMS.register(ElementalCombat.MOD_EVENT_BUS);
        BlockList.BLOCKS.register(ElementalCombat.MOD_EVENT_BUS);
        MOD_EVENT_BUS.register(BlockList.class);

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
