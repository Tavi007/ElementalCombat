package Tavi007.ElementalCombat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ServerConfig;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class RenderHelper {

	public static List<ITextComponent> getDisplayText(AttackData atckData) {
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		if(!atckData.isEmpty()) {
			list.add(new StringTextComponent("Attack:"));
			if(!atckData.getStyle().equals(ServerConfig.getDefaultStyle())) {
				list.add(new StringTextComponent("" + TextFormatting.GRAY + " - " + ElementalCombatAPI.getMappedString(atckData.getStyle()) + TextFormatting.RESET));
			}
			if(!atckData.getElement().equals(ServerConfig.getDefaultElement())) {
				list.add(new StringTextComponent("" + TextFormatting.GRAY + " - " + ElementalCombatAPI.getMappedString(atckData.getElement()) + TextFormatting.RESET));
			}
		}
		return list;
	}

	public static List<ITextComponent> getDisplayText(DefenseData defData) {
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		if (!defData.isEmpty()) {
			list.add(new StringTextComponent("Defense:"));
			list.addAll(toDisplayText(defData.getStyleFactor()));
			list.addAll(toDisplayText(defData.getElementFactor()));
		}
		return list;
	}
	
	private static List<ITextComponent> toDisplayText(HashMap<String, Integer> map){
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		map.forEach((key, factor) -> {
			list.add(new StringTextComponent(toPercentageString(key, factor)));
		});
		return list;
	}
	
	/**
	 * A Helper-function for constructing a formatted string. Used for tooltips and hud. (See {@link Tavi007.ElementalCombat.events.RenderEvents})
	 * @param key The Name of the value. The key can be from the element or style defense mapping.
	 * @param factor The corresponding value to the key from the element or style defense mapping.
	 * @return A formatted String ready to be displayed.
	 */
	private static String toPercentageString(String key, Integer factor) {
		//get color
		Integer percentage = Math.round(DefenseDataHelper.getPercentage(factor)*100);
		TextFormatting textFormatting = TextFormatting.GRAY;
		if (percentage < 0) {textFormatting = TextFormatting.RED;}
		if (percentage > 0 && percentage < 100) {textFormatting = TextFormatting.BLUE;}
		if (percentage == 100) {textFormatting = TextFormatting.YELLOW;}
		if (percentage > 100) {textFormatting = TextFormatting.GREEN;}

		//make string
		return "" + TextFormatting.GRAY + " - " + ElementalCombatAPI.getMappedString(key) + " " + textFormatting + String.valueOf(percentage)+ "%" + TextFormatting.RESET;
	}
}
