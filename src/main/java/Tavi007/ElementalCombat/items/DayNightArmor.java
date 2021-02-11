package Tavi007.ElementalCombat.items;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DayNightArmor extends  ArmorItem {
	
	public DayNightArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties p_i48534_3_) {
		super(materialIn, slot, p_i48534_3_);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		float time = (float) world.getDayTime();
		// time: 18000 = midnight; 6000 = noon; 12000 and 0 (or 24000) halfway points
		int lightFactor = Math.round((float) Math.sin(time/12000 * Math.PI) * 75) ;
		DefenseData data = ElementalCombatAPI.getDefenseData(stack);
		HashMap<String,Integer> elemFactors = data.getElementFactor();
		if(lightFactor>=0) {
			elemFactors.put("light", lightFactor);
			elemFactors.put("darkness", -lightFactor/2);
		}
		else {
			elemFactors.put("light", lightFactor/2);
			elemFactors.put("darkness", -lightFactor);
		}
	}

    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        ElementalCombatNBTHelper.writeDefenseDataToNBT(nbt, ElementalCombatAPI.getDefenseData(stack));
        return nbt;
    }

    @Override
	public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.setTag(nbt);
        ElementalCombatAPI.getDefenseData(stack).set(ElementalCombatNBTHelper.readDefenseDataFromNBT(nbt));
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    	tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Elemental defense depends on world time." + TextFormatting.RESET));
    }

//    @Nullable
//    @Override
//    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
//        return ElementalCombat.MOD_ID + ":textures/models/armor/clock_chestplate.png";
//    }
}
