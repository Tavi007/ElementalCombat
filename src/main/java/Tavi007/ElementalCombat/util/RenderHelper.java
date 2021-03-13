package Tavi007.ElementalCombat.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class RenderHelper {

	private static final FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
	public static final int iconSize = 8;
	public static final int widthAttack = fontRenderer.getStringWidth("Attack: ");
	public static final int widthDefense = fontRenderer.getStringWidth("Defense: ");
	public static final int maxLineHeight = fontRenderer.FONT_HEIGHT;
	public static final int maxLineWidth = fontRenderer.getStringWidth("Defense: -999%") + iconSize + 2;

	private static int iteratorCounter=0;

	public static void tickIteratorCounter() {
		iteratorCounter++;

		if(iteratorCounter == Integer.MAX_VALUE || iteratorCounter < 0) {
			iteratorCounter = 0;
		}
	}

	public static int getInteratorCounter() {
		return iteratorCounter;
	}

	public static void render(AttackData attackData, MatrixStack matrixStack, float posX, float posY) {
		Minecraft mc = Minecraft.getInstance();

		// caption
		if(ClientConfig.textShadow()) {
			mc.fontRenderer.drawStringWithShadow(matrixStack, "Attack:", posX, posY, TextFormatting.GRAY.getColor());
		}
		else {
			mc.fontRenderer.drawStringWithShadow(matrixStack, "Attack:", posX, posY, TextFormatting.GRAY.getColor());
		}
		posX += mc.fontRenderer.getStringWidth("Attack: ");

		// icon element
		renderIcon(attackData.getElement(), matrixStack, posX, posY);
		posX += iconSize + 1;

		// icon style
		renderIcon(attackData.getStyle(), matrixStack, posX, posY);
	}

	public static void render(DefenseData defData, MatrixStack matrixStack, float posX, float posY) {
		Minecraft mc = Minecraft.getInstance();

		// caption
		if(ClientConfig.textShadow()) {
			mc.fontRenderer.drawStringWithShadow(matrixStack, "Defense:", posX, posY, TextFormatting.GRAY.getColor());
		}
		else {
			mc.fontRenderer.drawStringWithShadow(matrixStack, "Defense:", posX, posY, TextFormatting.GRAY.getColor());
		}
		posX += mc.fontRenderer.getStringWidth("Defense: ");

		// icon
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.putAll(defData.getElementFactor());
		map.putAll(defData.getStyleFactor());
		String key = (new ArrayList<String>(map.keySet())).get(iteratorCounter % map.size());
		renderIcon(key, matrixStack, posX, posY);
		// value
		posX -= mc.fontRenderer.getStringWidth("Defense: ");
		renderPercentage(map.get(key), matrixStack, posX, posY);
	}
	
	public static void renderIcon(String name, MatrixStack matrixStack, float posX, float posY) {
		Minecraft mc = Minecraft.getInstance();
		mc.getTextureManager().bindTexture(new ResourceLocation(ElementalCombat.MOD_ID, "textures/icons/" + name + ".png"));
		AbstractGui.blit(matrixStack, (int) posX, (int) posY, 0, 0, iconSize, iconSize, iconSize, iconSize);
		mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
	}

	/**
	 * A Helper-function for constructing a formatted string. Used for tooltips and hud. (See {@link Tavi007.ElementalCombat.events.RenderEvents})
	 * @param key The Name of the value. The key can be from the element or style defense mapping.
	 * @param factor The corresponding value to the key from the element or style defense mapping.
	 * @return A formatted String ready to be displayed.
	 */
	private static void renderPercentage(Integer factor, MatrixStack matrixStack, float posX, float posY) {
		//get color
		Integer percentage = Math.round(DefenseDataHelper.getPercentage(factor)*100);
		TextFormatting textFormatting = TextFormatting.GRAY;
		if (percentage < 0) {textFormatting = TextFormatting.RED;}
		if (percentage > 0 && percentage < 100) {textFormatting = TextFormatting.BLUE;}
		if (percentage == 100) {textFormatting = TextFormatting.YELLOW;}
		if (percentage > 100) {textFormatting = TextFormatting.GREEN;}

		// bound to the right side
		Minecraft mc = Minecraft.getInstance();
		String percentageString = String.valueOf(percentage) + "%";
		posX += maxLineWidth - mc.fontRenderer.getStringWidth(percentageString);
		
		// write text
		if(ClientConfig.textShadow()) {
			mc.fontRenderer.drawStringWithShadow(matrixStack,  percentageString, posX, posY, textFormatting.getColor());
		}
		else {
			mc.fontRenderer.drawStringWithShadow(matrixStack, percentageString, posX, posY, textFormatting.getColor());
		}
	}
	
	public static String getPercentageStringTooltip(Integer factor) {
		Integer percentage = Math.round(DefenseDataHelper.getPercentage(factor)*100);
		TextFormatting textFormatting = TextFormatting.GRAY;
		if (percentage < 0) {textFormatting = TextFormatting.RED;}
		if (percentage > 0 && percentage < 100) {textFormatting = TextFormatting.BLUE;}
		if (percentage == 100) {textFormatting = TextFormatting.YELLOW;}
		if (percentage > 100) {textFormatting = TextFormatting.GREEN;}

		return "-        " + textFormatting + String.valueOf(percentage) + "%" + TextFormatting.RESET;
	}
}
