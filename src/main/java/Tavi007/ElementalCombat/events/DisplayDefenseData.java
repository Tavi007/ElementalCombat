package Tavi007.ElementalCombat.events;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

import Tavi007.ElementalCombat.Configuration;
import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.loading.EntityCombatProperties;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class DisplayDefenseData {
	
	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGameOverlayEvent.Text event)
	{
		Minecraft mc = Minecraft.getInstance();
		if(mc.player != null) {
			DefenseData defData = ElementalCombatAPI.getDefenseData(mc.player);
			HashMap<String, Integer> styleMap = defData.getStyleFactor();
			HashMap<String, Integer> elementMap = defData.getElementFactor();
			
			double scale = Configuration.scale();
			RenderSystem.pushMatrix();
			RenderSystem.scaled(scale, scale, scale);
			styleMap.forEach((key, factor) -> {
				mc.fontRenderer.func_238418_a_(new StringTextComponent(DefenseDataHelper.toPercentageString(key, factor)), Configuration.posX(), Configuration.posY(), 0x000000, Integer.MAX_VALUE);
			});

			elementMap.forEach((key, factor) -> {
				mc.fontRenderer.func_238418_a_(new StringTextComponent(DefenseDataHelper.toPercentageString(key, factor)), Configuration.posX(), Configuration.posY(), 0x000000, Integer.MAX_VALUE);
			});
			RenderSystem.popMatrix();
		}
	}

}
