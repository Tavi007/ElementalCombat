package Tavi007.ElementalCombat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tavi007.ElementalCombat.loading.CombatPropertiesManager;
import Tavi007.ElementalCombat.particle.ParticleList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod("elementalcombat")
public class ElementalCombat 
{
	public static ElementalCombat instance;
	public static final String MOD_ID = "elementalcombat";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	public static CombatPropertiesManager COMBAT_PROPERTIES_MANGER = new CombatPropertiesManager();
    public static IEventBus MOD_EVENT_BUS;
    
	public static final ResourceLocation simpleChannelRL = new ResourceLocation(MOD_ID, "channel");
	public static SimpleChannel simpleChannel;    // used to transmit your network messages
    
    public static final String DEFAULT_ELEMENT = "natural";
    public static final String DEFAULT_STYLE = "basic";
    public static final Integer MAX_FACTOR = 25;
	public static final Integer SCALE_ENCHANTMENT = 2;
       
    
	@SuppressWarnings("deprecation")
	public ElementalCombat()
	{
		MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		
		//register everything
		ParticleList.PARTICLES.register(ElementalCombat.MOD_EVENT_BUS);
		
		MOD_EVENT_BUS.register(StartupCommon.class);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> ElementalCombat::registerClientOnlyEvents);
        
		instance = this;
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	public static void registerClientOnlyEvents() {
		MOD_EVENT_BUS.register(StartupClientOnly.class);
	}
}
