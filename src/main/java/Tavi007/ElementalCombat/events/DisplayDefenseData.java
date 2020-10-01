package Tavi007.ElementalCombat.events;

import java.util.HashMap;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.Configuration;
import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.client.Minecraft;
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
			
			MatrixStack matrixStack = event.getMatrixStack();
			float scale = (float) Configuration.scale();
			matrixStack.scale(scale, scale, scale);
			
			float posX = Configuration.posX();
			float posY = Configuration.posY();
			float textHeight = mc.fontRenderer.FONT_HEIGHT;

			displayMap(styleMap, matrixStack, posX, posY);
			displayMap(elementMap, matrixStack, posX, posY + styleMap.size()*textHeight);
		}
	}
	
	private static void displayMap(HashMap<String, Integer> map, MatrixStack matrixStack, float posX, float posY) {
		Minecraft mc = Minecraft.getInstance();

		float textHeight = mc.fontRenderer.FONT_HEIGHT;
		final int[] i = {0};
		map.forEach ( (key, factor) -> {
			mc.fontRenderer.func_243248_b(matrixStack, new StringTextComponent(DefenseDataHelper.toPercentageString(key, factor)), posX, posY + i[0]*textHeight, Integer.MAX_VALUE);
			i[0] += 1;
		});	
	}
}
