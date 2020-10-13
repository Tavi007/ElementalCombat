package Tavi007.ElementalCombat.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.DefenseDataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Matrix4f;
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
		if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)) {
			if(ClientConfig.isHUDEnabled()) {
				// see Screen#renderToolTips in client.gui.screen
				Minecraft mc = Minecraft.getInstance();
				if(mc.player != null) {
					DefenseData defData = ElementalCombatAPI.getDefenseData(mc.player);
					if (!defData.isEmpty()) {
						List<ITextComponent> list = new ArrayList<ITextComponent>();
						list.add(new StringTextComponent("Defense:"));
						list.addAll(toDisplayText(defData.getStyleFactor()));
						list.addAll(toDisplayText(defData.getElementFactor()));

						if (!list.isEmpty()) {
							MatrixStack matrixStack = event.getMatrixStack();

							//TODO: include scaling
							
							
							List<? extends IReorderingProcessor> orderedList = Lists.transform(list, ITextComponent::func_241878_f);
							// computes the width of the widest line.
							int listWidth = 0;
							for(IReorderingProcessor ireorderingprocessor : orderedList) {
								int textWidth = mc.fontRenderer.func_243245_a(ireorderingprocessor);
								listWidth = Math.max(textWidth, listWidth);
							}

							// computes the height of the list
							int listHeight = 8;
							if (orderedList.size() > 1) {
								listHeight += 2 + (orderedList.size() - 1) * (mc.fontRenderer.FONT_HEIGHT+1);
							}

							// moves the coords so the text and box appear correct
							int posX = 12;
							int posY = 12;
							if(!ClientConfig.isTop()) {
								int screenHeight = event.getWindow().getScaledHeight();
								posY = Math.max(12, screenHeight - listHeight - 12);
							}
							if(!ClientConfig.isLeft()) {
								int screenWidth = event.getWindow().getScaledWidth();
								posX = Math.max(12, screenWidth - listWidth - 12);
							}

							matrixStack.push();
//							int l = -267386864;
//							int i1 = 1347420415;
//							int j1 = 1344798847;
//							int k1 = 400;
							Tessellator tessellator = Tessellator.getInstance();
							BufferBuilder bufferbuilder = tessellator.getBuffer();
							bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
							Matrix4f matrix4f = matrixStack.getLast().getMatrix();
							// draw background box
							func_238462_a_(matrix4f, bufferbuilder, posX - 3, posY - 4, posX + listWidth + 3, posY - 3, 400, -267386864, -267386864);
							func_238462_a_(matrix4f, bufferbuilder, posX - 3, posY + listHeight + 3, posX + listWidth + 3, posY + listHeight + 4, 400, -267386864, -267386864);
							func_238462_a_(matrix4f, bufferbuilder, posX - 3, posY - 3, posX + listWidth + 3, posY + listHeight + 3, 400, -267386864, -267386864);
							func_238462_a_(matrix4f, bufferbuilder, posX - 4, posY - 3, posX - 3, posY + listHeight + 3, 400, -267386864, -267386864);
							func_238462_a_(matrix4f, bufferbuilder, posX + listWidth + 3, posY - 3, posX + listWidth + 4, posY + listHeight + 3, 400, -267386864, -267386864);
							func_238462_a_(matrix4f, bufferbuilder, posX - 3, posY - 3 + 1, posX - 3 + 1, posY + listHeight + 3 - 1, 400, 1347420415, 1344798847);
							func_238462_a_(matrix4f, bufferbuilder, posX + listWidth + 2, posY - 3 + 1, posX + listWidth + 3, posY + listHeight + 3 - 1, 400, 1347420415, 1344798847);
							func_238462_a_(matrix4f, bufferbuilder, posX - 3, posY - 3, posX + listWidth + 3, posY - 3 + 1, 400, 1347420415, 1347420415);
							func_238462_a_(matrix4f, bufferbuilder, posX - 3, posY + listHeight + 2, posX + listWidth + 3, posY + listHeight + 3, 400, 1344798847, 1344798847);
							RenderSystem.enableDepthTest();
							RenderSystem.disableTexture();
							RenderSystem.enableBlend();
							RenderSystem.defaultBlendFunc();
							RenderSystem.shadeModel(7425);
							bufferbuilder.finishDrawing();
							WorldVertexBufferUploader.draw(bufferbuilder);
							RenderSystem.shadeModel(7424);
							RenderSystem.disableBlend();
							RenderSystem.enableTexture();
							IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
							matrixStack.translate(0.0D, 0.0D, 400.0D);

							// write the list on top of the background
							for(int i = 0; i < orderedList.size(); ++i) {
								IReorderingProcessor ireorderingprocessor1 = orderedList.get(i);
								if (ireorderingprocessor1 != null) {
									mc.fontRenderer.func_238416_a_(ireorderingprocessor1, (float)posX, (float)posY, -1, ClientConfig.textShadow(), matrix4f, irendertypebuffer$impl, false, 0, 15728880);
								}
								// first line is caption. add a little bit space to the next line
								if (i == 0) {
									posY += 2;
								}
								//next line
								posY += 10;
							}
							irendertypebuffer$impl.finish();
							matrixStack.pop();
						}
					}
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

	//copied from Screen
	protected static void func_238462_a_(Matrix4f p_238462_0_, BufferBuilder p_238462_1_, int p_238462_2_, int p_238462_3_, int p_238462_4_, int p_238462_5_, int p_238462_6_, int p_238462_7_, int p_238462_8_) {
		float f = (float)(p_238462_7_ >> 24 & 255) / 255.0F;
		float f1 = (float)(p_238462_7_ >> 16 & 255) / 255.0F;
		float f2 = (float)(p_238462_7_ >> 8 & 255) / 255.0F;
		float f3 = (float)(p_238462_7_ & 255) / 255.0F;
		float f4 = (float)(p_238462_8_ >> 24 & 255) / 255.0F;
		float f5 = (float)(p_238462_8_ >> 16 & 255) / 255.0F;
		float f6 = (float)(p_238462_8_ >> 8 & 255) / 255.0F;
		float f7 = (float)(p_238462_8_ & 255) / 255.0F;
		p_238462_1_.pos(p_238462_0_, (float)p_238462_4_, (float)p_238462_3_, (float)p_238462_6_).color(f1, f2, f3, f).endVertex();
		p_238462_1_.pos(p_238462_0_, (float)p_238462_2_, (float)p_238462_3_, (float)p_238462_6_).color(f1, f2, f3, f).endVertex();
		p_238462_1_.pos(p_238462_0_, (float)p_238462_2_, (float)p_238462_5_, (float)p_238462_6_).color(f5, f6, f7, f4).endVertex();
		p_238462_1_.pos(p_238462_0_, (float)p_238462_4_, (float)p_238462_5_, (float)p_238462_6_).color(f5, f6, f7, f4).endVertex();
	}
}
