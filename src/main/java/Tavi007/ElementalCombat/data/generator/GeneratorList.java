package Tavi007.ElementalCombat.data.generator;

import com.google.common.eventbus.Subscribe;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class GeneratorList {

    @Subscribe
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(new ItemPropertiesProvider(generator));
    }

}
