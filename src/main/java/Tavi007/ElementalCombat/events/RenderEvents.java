package Tavi007.ElementalCombat.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.Configuration;
import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class RenderEvents {

	@SubscribeEvent
	public static void addTooltipInformation(ItemTooltipEvent event) {
		ItemStack item = event.getItemStack();
		List<ITextComponent> toolTip = event.getToolTip();
		if (item != null) {
			//attack
			AttackData atckCap = ElementalCombatAPI.getAttackDataWithEnchantment(item);
			if (!atckCap.getStyle().equals(ElementalCombat.DEFAULT_STYLE)) {
				String textAttackStyle = "" + TextFormatting.GRAY + "Attack Style: " + WordUtils.capitalize(atckCap.getStyle()) + TextFormatting.RESET;
				toolTip.add(new StringTextComponent(textAttackStyle));
			}
			if (!atckCap.getElement().equals(ElementalCombat.DEFAULT_ELEMENT)) {
				String textAttackElement = "" + TextFormatting.GRAY + "Elemental Attack: " + WordUtils.capitalize(atckCap.getElement()) + TextFormatting.RESET;
				toolTip.add(new StringTextComponent(textAttackElement));
			}

			//defense
			DefenseData defCap = ElementalCombatAPI.getDefenseDataWithEnchantment(item);
			HashMap<String, Integer> defStyle = defCap.getStyleFactor();
			if(!defStyle.isEmpty()) {
				String textDefenseStyle = "" + TextFormatting.GRAY + "Style Resistance: " + TextFormatting.RESET;
				toolTip.add(new StringTextComponent(textDefenseStyle));
				toolTip.addAll(toDisplayText(defStyle));
			}
			HashMap<String, Integer> defElement = defCap.getElementFactor();
			if(!defElement.isEmpty()) {
				String textDefenseElement = "" + TextFormatting.GRAY + "Elemental Resistance:" + TextFormatting.RESET;
				toolTip.add(new StringTextComponent(textDefenseElement));
				toolTip.addAll(toDisplayText(defElement));
			}
		}
	}

	@SubscribeEvent
	public static void displayDefenseData(RenderGameOverlayEvent.Post event)
	{
		// see Screen#renderToolTips in client.gui.screen

		Minecraft mc = Minecraft.getInstance();
		if(mc.player != null) {
			DefenseData defData = ElementalCombatAPI.getDefenseData(mc.player);
			if (!defData.isEmpty()) {
				HashMap<String, Integer> styleMap = defData.getStyleFactor();
				HashMap<String, Integer> elementMap = defData.getElementFactor();

				List<ITextComponent> list = new ArrayList<ITextComponent>();
				list.addAll(toDisplayText(styleMap));
				list.addAll(toDisplayText(elementMap));

				MatrixStack matrixStack = event.getMatrixStack();
				float scale = (float) Configuration.scale();
				matrixStack.scale(scale, scale, scale);

				float posX = Configuration.posX();
				float posY = Configuration.posY();

				if(mc.currentScreen != null) {
					mc.currentScreen.func_243308_b(matrixStack, list, (int) posX, (int) posY);
				}
			}
		}
	}

	private static List<ITextComponent> toDisplayText(HashMap<String, Integer> map){
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		map.forEach((key, factor) -> {
			list.add(new StringTextComponent(DefenseDataHelper.toPercentageString(key, factor)));
		});
		return list;
	}
}
