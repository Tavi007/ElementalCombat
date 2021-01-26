package Tavi007.ElementalCombat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class RenderHelper {

	private static int ticks = 0;
	private static int counter = 0;
	
	public static List<ITextComponent> getDisplayText(AttackData atckData) {
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		list.add(new StringTextComponent("Attack:"));
		list.add(new StringTextComponent("" + TextFormatting.GRAY + " - " + ElementalCombatAPI.getMappedString(atckData.getElement()) + " " + 
										ElementalCombatAPI.getMappedString(atckData.getStyle()) + TextFormatting.RESET));
		return list;
	}

	public static List<ITextComponent> getDisplayText(DefenseData defData) {
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		list.add(new StringTextComponent("Defense:"));
		list.addAll(toDisplayText(defData.getStyleFactor()));
		list.addAll(toDisplayText(defData.getElementFactor()));
		return list;
	}

	public static List<ITextComponent> getIteratingDisplayText(DefenseData defData) {
		ticks++;
		if(ticks>20) { // Is 1 second enough?
			ticks = 0;
			counter++;
		}
		if(counter>100) {
			counter = 0;
		}
		
		List<ITextComponent> list = new ArrayList<ITextComponent>();
		list.add(new StringTextComponent("Defense:"));
		
		Object[] elementKeys = defData.getElementFactor().keySet().toArray();
		if(elementKeys.length > 0) {
			String key = (String) elementKeys[counter % elementKeys.length];
			list.add(new StringTextComponent(toPercentageString(key, defData.getElementFactor().get(key) )));
		}
		Object[] styleKeys = defData.getStyleFactor().keySet().toArray();
		if(styleKeys.length > 0) {
			String key = (String) styleKeys[counter % styleKeys.length];
			list.add(new StringTextComponent(toPercentageString(key, defData.getStyleFactor().get(key) )));
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
