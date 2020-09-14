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
			//currently the ClientPlayerEntity doesn't have data, cause it isn't in sync with the ServerPlayerEntity
			DefenseData defData = ElementalCombatAPI.getDefenseData(mc.player);
			HashMap<String, Integer> styleMap = defData.getStyleFactor();
			HashMap<String, Integer> elementMap = defData.getElementFactor();

			// Testing
			ResourceLocation rlProperties = new ResourceLocation(ElementalCombat.MOD_ID, "entities/minecraft/zombie");
			EntityCombatProperties entityProperties = ElementalCombat.COMBAT_PROPERTIES_MANGER.getEntityDataFromLocation(rlProperties);
			styleMap = new HashMap<String, Integer>(entityProperties.getDefenseStyle());
			elementMap = new HashMap<String, Integer>(entityProperties.getDefenseElement());
			
			double scale = Configuration.scale();
			RenderSystem.pushMatrix();
			RenderSystem.scaled(scale, scale, scale);
			styleMap.forEach((key, factor) -> {
				mc.fontRenderer.func_238418_a_(new StringTextComponent(DefenseDataHelper.toPercentageString(key, factor)), Configuration.posX(), Configuration.posY(), 0x000000, Integer.MAX_VALUE);
			});

			elementMap.forEach((key, factor) -> {
				mc.fontRenderer.func_238418_a_(new StringTextComponent(DefenseDataHelper.toPercentageString(key, factor)), Configuration.posX(), Configuration.posY(), 0x000000, Integer.MAX_VALUE);
			});
			mc.fontRenderer.func_238418_a_(new StringTextComponent("Test"), 20, 20, 0x000000, Integer.MAX_VALUE);
			RenderSystem.popMatrix();
		}
	}

}
