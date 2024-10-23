package Tavi007.ElementalCombat.common.capabilities;

import Tavi007.ElementalCombat.common.api.AttackDataAPI;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CapabilitiesHelper {

    static private final ResourceLocation mainHandLocation = new ResourceLocation("mainhand");

    public static void updateMainHandItemLayer(LivingEntity entity) {
        if (entity == null) {
            return;
        }
        AttackData attackDataItem = CapabilitiesAccessors.getAttackData(entity.getMainHandItem());
        AttackDataAPI.putLayer(entity, attackDataItem.toLayer(), mainHandLocation);
    }
}
