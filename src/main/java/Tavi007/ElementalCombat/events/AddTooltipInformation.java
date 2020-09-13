package Tavi007.ElementalCombat.events;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, bus = Bus.FORGE)
public class AddTooltipInformation {

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
				defStyle.forEach((key, factor) -> {
					toolTip.add(new StringTextComponent(toPercentageString(key, factor)));
				});
			}
			HashMap<String, Integer> defElement = defCap.getElementFactor();
			if(!defElement.isEmpty()) {
				String textDefenseElement = "" + TextFormatting.GRAY + "Elemental Resistance:" + TextFormatting.RESET;
				toolTip.add(new StringTextComponent(textDefenseElement));
				defElement.forEach((key, factor) -> {
					toolTip.add(new StringTextComponent(toPercentageString(key, factor)));
				});
			}
		}
	}
	
	private static String toPercentageString(String key, Integer factor) {
		//get color
		Integer percentage = Math.round(DefenseDataHelper.getPercentage(factor)*100);
		TextFormatting textFormatting = TextFormatting.GRAY;
		if (percentage < 0) {textFormatting = TextFormatting.RED;}
		if (percentage > 0 && percentage < 100) {textFormatting = TextFormatting.BLUE;}
		if (percentage == 100) {textFormatting = TextFormatting.YELLOW;}
		if (percentage > 100) {textFormatting = TextFormatting.GREEN;}
		
		//make string
		return "" + TextFormatting.GRAY + " - " + WordUtils.capitalize(key) + " " + textFormatting + String.valueOf(percentage)+ "%" + TextFormatting.RESET;
	}
}
