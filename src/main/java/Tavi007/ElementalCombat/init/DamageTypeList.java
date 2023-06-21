package Tavi007.ElementalCombat.init;

import Tavi007.ElementalCombat.ElementalCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypeList {

    public static final ResourceKey<DamageType> FIRE_MAGIC = register("fire_magic");
    public static final ResourceKey<DamageType> ICE_MAGIC = register("ice_magic");
    public static final ResourceKey<DamageType> WATER_MAGIC = register("water_magic");
    public static final ResourceKey<DamageType> THUNDER_MAGIC = register("thunder_magic");
    public static final ResourceKey<DamageType> DARKNESS_MAGIC = register("darkness_magic");
    public static final ResourceKey<DamageType> LIGHT_MAGIC = register("light_magic");
    public static final ResourceKey<DamageType> WIND_MAGIC = register("wind_magic");
    public static final ResourceKey<DamageType> EARTH_MAGIC = register("earth_magic");
    public static final ResourceKey<DamageType> FLORA_MAGIC = register("flora_magic");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ElementalCombat.MOD_ID, name));
    }

    protected static void bootstrap(BootstapContext<DamageType> context) {
        context.register(FIRE_MAGIC, new DamageType("fire_magic", 0.1F));
        context.register(ICE_MAGIC, new DamageType("ice_magic", 0.1F));
        context.register(WATER_MAGIC, new DamageType("water_magic", 0.1F));
        context.register(THUNDER_MAGIC, new DamageType("thunder_magic", 0.1F));
        context.register(DARKNESS_MAGIC, new DamageType("darkness_magic", 0.1F));
        context.register(LIGHT_MAGIC, new DamageType("light_magic", 0.1F));
        context.register(WIND_MAGIC, new DamageType("wind_magic", 0.1F));
        context.register(EARTH_MAGIC, new DamageType("earth_magic", 0.1F));
        context.register(FLORA_MAGIC, new DamageType("flora_magic", 0.1F));
    }
}
