package Tavi007.ElementalCombat.events;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.api.AttackDataAPI;
import Tavi007.ElementalCombat.api.DefenseDataAPI;
import Tavi007.ElementalCombat.api.attack.AttackData;
import Tavi007.ElementalCombat.api.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.immersion.ImmersionData;
import Tavi007.ElementalCombat.capabilities.immersion.ImmersionDataCapability;
import Tavi007.ElementalCombat.config.ClientConfig;
import Tavi007.ElementalCombat.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = ElementalCombat.MOD_ID, value = Dist.CLIENT, bus = Bus.FORGE)
public class RenderEvents {

	@SubscribeEvent
	public static void onRenderLivingEventPre(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
		LivingEntity entityIn = event.getEntity();
		ImmersionData data = (ImmersionData) entityIn.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
		if(entityIn.hurtTime > 0) {
			if (data.disableFlag) {
				data.setHurtTime(entityIn.hurtTime);
				entityIn.hurtTime = 0; //desync client and server hurtTime.
			}
		}
		else {
			data.disableFlag = false;
		}
	}

	@SubscribeEvent
	public static void onRenderLivingEventPost(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
		LivingEntity entityIn = event.getEntity();
		ImmersionData data = (ImmersionData) entityIn.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
		if (data.disableFlag && data.getHurtTime() > 0) {
			entityIn.hurtTime = data.getHurtTime();
			data.setHurtTime(0);
		}
	}

	@SubscribeEvent
	public static void onEntityViewRenderEvent(CameraSetup event) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.player != null) {
			if (mc.player.hurtTime > 0) {
				ImmersionData data = (ImmersionData) mc.player.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
				if(data.disableFlag) {
					// Use the same calculation as in GameRenderer#hurtCameraEffect.
					float f = (float) (mc.player.hurtTime - event.getRenderPartialTicks());
					f = f / (float) mc.player.maxHurtTime;
					f = MathHelper.sin(f * f * f * f * (float)Math.PI);
					event.setRoll(f * 14.0F); // counter acts the screen shake. Only the hand is moving now.
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlaySoundEvent(PlaySoundEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if(mc.player != null) {
			if (mc.player.hurtTime == mc.player.maxHurtTime) {
				ImmersionData data = (ImmersionData) mc.player.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null).orElse(new ImmersionData());
				if(data.disableFlag) {
					//What if other mods implements their own version of an hurt sound?
					//Also what if the on_fire sound gets disabled, even so I still took fire damage?
					if( event.getSound().getSoundLocation().equals(SoundEvents.ENTITY_PLAYER_HURT.getRegistryName()) || 
							event.getSound().getSoundLocation().equals(SoundEvents.ENTITY_PLAYER_HURT_DROWN.getRegistryName()) ||
							event.getSound().getSoundLocation().equals(SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE.getRegistryName()) ||
							event.getSound().getSoundLocation().equals(SoundEvents.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH.getRegistryName()) ) {
						event.setResult(null); 
					}
				}
			}
		}
	}
	
	// fires before RenderTooltipEvent.PostText
	// add all the text to tooltip
	static int tooltipIndexAttack;
	static int tooltipIndexDefense;
	@SubscribeEvent
	public static void ontTooltip(ItemTooltipEvent event) {
		List<ITextComponent> tooltip = event.getToolTip();
		ItemStack stack = event.getItemStack();
		AttackData attackData = AttackDataAPI.get(stack);
		if(!attackData.isEmpty()) {
			tooltipIndexAttack = tooltip.size();
			RenderHelper.addTooltip(tooltip, false, attackData, null);
		}
		DefenseData defenseData = DefenseDataAPI.get(stack);
		if(!defenseData.isEmpty()) {
			tooltipIndexDefense = tooltip.size();
			RenderHelper.addTooltip(tooltip, ClientConfig.isDoubleRowDefenseTooltip(), null, defenseData);
		}
	}
	
	// fires after ItemTooltipEvent
	// render only icons here (because strings wont't get rendered anymore)
	@SubscribeEvent
	public static void onTooltipRenderPost(RenderTooltipEvent.PostText event) {
		MatrixStack matrixStack = event.getMatrixStack();
		ItemStack stack = event.getStack();
		AttackData attackData = AttackDataAPI.get(stack);
		DefenseData defenseData = DefenseDataAPI.get(stack);
		
		if(!attackData.isEmpty()) {
			RenderHelper.renderAttackIcons(attackData, matrixStack, event.getX(), event.getY() + 2 + tooltipIndexAttack*RenderHelper.maxLineHeight);
		}
		if(!defenseData.isEmpty()) {
			RenderHelper.renderDefenseIcons(defenseData, ClientConfig.isDoubleRowDefenseTooltip(), matrixStack, event.getX(), event.getY() + 2 + tooltipIndexDefense*RenderHelper.maxLineHeight);
		}
	}

	static int ticks = 0;
	@SubscribeEvent
	public static void displayData(RenderGameOverlayEvent.Post event) {
		if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)) {
			ticks++;
			if(ticks >= ClientConfig.iterationSpeed()*2.5) {  
				ticks = 0;
				RenderHelper.tickIteratorCounter();
			}
			if(ClientConfig.isHUDEnabled()) {
				// see Screen#renderToolTips in client.gui.screen
				Minecraft mc = Minecraft.getInstance();
				if(mc.player != null) {
					MatrixStack matrixStack = event.getMatrixStack();
					float scale = (float) ClientConfig.scale();
					AttackData attackData = AttackDataAPI.getWithActiveItem(mc.player);
					DefenseData defenseData = DefenseDataAPI.get(mc.player);

					// the width of the box.
					int listWidth = RenderHelper.maxLineWidth;

					// computes the height of the list
					int listHeight = RenderHelper.maxLineHeight;
					if(!defenseData.isEmpty()) {
						listHeight += RenderHelper.maxLineHeight;
						if(ClientConfig.isDoubleRowDefenseHUD() && !defenseData.getElementFactor().isEmpty() && !defenseData.getStyleFactor().isEmpty()) {
							listHeight += RenderHelper.maxLineHeight;
						}
					}

					// moves the coords so the text and box appear correct
					int posX = 12;
					int posY = 12;
					if(!ClientConfig.isTop()) {
						int screenHeight = (int) (event.getWindow().getScaledHeight()/scale);
						posY = Math.max(12, screenHeight - listHeight - 12);
					}
					if(!ClientConfig.isLeft()) {
						int screenWidth = (int) (event.getWindow().getScaledWidth()/scale);
						posX = Math.max(12, screenWidth - listWidth - 12);
					}


					matrixStack.push();
					matrixStack.scale(scale, scale, scale);

					// draw background box
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();
					bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
					Matrix4f matrix4f = matrixStack.getLast().getMatrix();
					func_238462_a_(matrix4f, bufferbuilder, posX - 3, 				posY - 4, 				posX + listWidth + 3, 	posY - 3, 					400, -267386864, -267386864);
					func_238462_a_(matrix4f, bufferbuilder, posX - 3,			 	posY + listHeight + 1, 	posX + listWidth + 3, 	posY + listHeight + 2, 		400, -267386864, -267386864);
					func_238462_a_(matrix4f, bufferbuilder, posX - 3, 				posY - 3, 				posX + listWidth + 3, 	posY + listHeight + 1, 		400, -267386864, -267386864);
					func_238462_a_(matrix4f, bufferbuilder, posX - 4, 				posY - 3, 				posX - 3, 				posY + listHeight + 1, 		400, -267386864, -267386864);
					func_238462_a_(matrix4f, bufferbuilder, posX + listWidth + 3, 	posY - 3, 				posX + listWidth + 4, 	posY + listHeight + 1, 		400, -267386864, -267386864);
					func_238462_a_(matrix4f, bufferbuilder, posX - 3, 				posY - 3 + 1, 			posX - 3 + 1, 			posY + listHeight,		 	400, 1347420415, 1344798847);
					func_238462_a_(matrix4f, bufferbuilder, posX + listWidth + 2, 	posY - 3 + 1, 			posX + listWidth + 3, 	posY + listHeight,		 	400, 1347420415, 1344798847);
					func_238462_a_(matrix4f, bufferbuilder, posX - 3, 				posY - 3, 				posX + listWidth + 3, 	posY - 3 + 1, 				400, 1347420415, 1347420415);
					func_238462_a_(matrix4f, bufferbuilder, posX - 3, 				posY + listHeight,	 	posX + listWidth + 3, 	posY + listHeight + 1, 		400, 1344798847, 1344798847);
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
					irendertypebuffer$impl.finish();

					//fill and render tooltip
					List<ITextComponent> tooltip = new ArrayList<ITextComponent>();
					RenderHelper.addTooltip(tooltip, ClientConfig.isDoubleRowDefenseHUD(), attackData, defenseData);
					RenderHelper.renderTooltip(tooltip, matrixStack, posX, posY);
					
					// render attackData icons
					RenderHelper.renderAttackIcons(attackData, matrixStack, posX, posY);

					// render defenseData icons
					if(!defenseData.isEmpty()) {
						posY += RenderHelper.maxLineHeight;
						RenderHelper.renderDefenseIcons(defenseData, ClientConfig.isDoubleRowDefenseHUD(), matrixStack, posX, posY);
					}
					matrixStack.pop();
				}
			}
		}
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
