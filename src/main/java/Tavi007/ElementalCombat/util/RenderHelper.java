package Tavi007.ElementalCombat.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class RenderHelper {

	private static final FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
	private static final TextureManager textureManager = Minecraft.getInstance().textureManager;
	
	private static final String textAttack = "Attack: ";
	private static final String textDefense = "Defense: ";
	private static final int iconSize = 8;
	
	private static final int widthAttack = fontRenderer.getStringWidth(textAttack);
	private static final int widthDefense = fontRenderer.getStringWidth(textDefense);
	public static final int maxLineHeight = fontRenderer.FONT_HEIGHT + 1;
	public static final int maxLineWidth = fontRenderer.getStringWidth(textDefense + " -999%") + iconSize + 2;

	private static int iteratorCounter=0;

	public static void tickIteratorCounter() {
		iteratorCounter++;
		if(iteratorCounter == Integer.MAX_VALUE) {
			iteratorCounter = 0;
		}
	}

	private static String getCurrentDefenseName(DefenseData data) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.putAll(data.getElementFactor());
		map.putAll(data.getStyleFactor());
		return (new ArrayList<String>(map.keySet())).get(iteratorCounter % map.size());
	}

	private static int getCurrentDefenseFactor(DefenseData data) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.putAll(data.getElementFactor());
		map.putAll(data.getStyleFactor());
		return (new ArrayList<Integer>(map.values())).get(iteratorCounter % map.size());
	}
	
	// Tooltip stuff
	public static void renderTooltip(List<ITextComponent> tooltip, MatrixStack matrixStack, int x, int y) {
		for(int i=0; i<tooltip.size(); i++) {
			fontRenderer.drawStringWithShadow(matrixStack, tooltip.get(i).getString(), x, y + i*RenderHelper.maxLineHeight, TextFormatting.GRAY.getColor());
		}
		
	}
	
	public static void addTooltip(List<ITextComponent> tooltip, @Nullable AttackData attackData, @Nullable DefenseData defenseData) {
		if (attackData != null) {
			tooltip.add(new StringTextComponent(textAttack));
		}
		if (defenseData != null && !defenseData.isEmpty()) {
			int factor = getCurrentDefenseFactor(defenseData);
			tooltip.add(new StringTextComponent(textDefense + "   " + getPercentage(factor)));
		}
	}
	
	public static String getPercentage(Integer factor) {
		Integer percentage = Math.round(DefenseDataHelper.getPercentage(factor)*100);
		TextFormatting textFormatting = TextFormatting.GRAY;
		if (percentage < 0) {textFormatting = TextFormatting.RED;}
		if (percentage > 0 && percentage < 100) {textFormatting = TextFormatting.BLUE;}
		if (percentage == 100) {textFormatting = TextFormatting.YELLOW;}
		if (percentage > 100) {textFormatting = TextFormatting.GREEN;}
		
		if (percentage < 0) {
			return textFormatting + String.valueOf(percentage) + "%" + TextFormatting.RESET;
		}
		else {
			return textFormatting + "+" + String.valueOf(percentage) + "%" + TextFormatting.RESET;
		}
	}
	
	// icon render stuff
	// posX and posY are the start coords of the "Attack: "- String. Calculation will be relative from there on out.
	public static void renderAttackIcons(AttackData data, MatrixStack matrixStack, int posX, int posY) {
		renderIcon(data.getElement(), matrixStack, posX + widthAttack, posY);
		renderIcon(data.getStyle(), matrixStack, posX + widthAttack + iconSize + 2, posY);
	}

	// posX and posY are the start coords of the "Defense: "- String. Calculation will be relative from there on out.
	public static void renderDefenseIcons(DefenseData data, MatrixStack matrixStack, int posX, int posY) {
		renderIcon(getCurrentDefenseName(data), matrixStack, posX + widthDefense, posY);
	}
	
	public static void renderIcon(String name, MatrixStack matrixStack, int posX, int posY) {
		textureManager.bindTexture(new ResourceLocation(ElementalCombat.MOD_ID, "textures/icons/" + name + ".png"));
		AbstractGui.blit(matrixStack, posX, posY, 0, 0, iconSize, iconSize, iconSize, iconSize);
		textureManager.bindTexture(AbstractGui.GUI_ICONS_LOCATION);
	}
}
