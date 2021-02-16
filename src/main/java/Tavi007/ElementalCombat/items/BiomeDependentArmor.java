package Tavi007.ElementalCombat.items;

import java.util.List;

import javax.annotation.Nullable;

import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.loading.BiomeCombatProperties;
import Tavi007.ElementalCombat.util.ElementalCombatNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BiomeDependentArmor extends  ArmorItem {
	
	int tickCounter = 0;
	
	public BiomeDependentArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties p_i48534_3_) {
		super(materialIn, slot, p_i48534_3_);
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		tickCounter++;
		// for performance
		if (tickCounter > 10) {
			Biome biome = world.getBiome(player.getPosition());
			BiomeCombatProperties defaultProperties = ElementalCombatAPI.getDefaultProperties(biome);
			DefenseData data = ElementalCombatAPI.getDefenseData(stack);
			data.setElementFactor(defaultProperties.getDefenseElement());
			tickCounter = 0;
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
    	tooltip.add(new StringTextComponent("" + TextFormatting.GRAY + "Elemental defense depends on current biome." + TextFormatting.RESET));
    }

//    @Nullable
//    @Override
//    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
//        return ElementalCombat.MOD_ID + ":textures/models/armor/clock_chestplate.png";
//    }
}