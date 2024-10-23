package Tavi007.ElementalCombat.server;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeServerConfig {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ForgeServerConfig SERVER;

    static {
        Pair<ForgeServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ForgeServerConfig::new);

        CONFIG_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    ForgeServerConfig(ForgeConfigSpec.Builder builder) {
        ServerConfig.init(builder
                        .comment("The maximal combat factor. See vanilla Enchantment Protection Factor.")
                        .defineInRange("maxFactor", 100, 1, 200),
                builder
                        .comment("Scaling for (element) protection enchantments. See vanilla Enchantment Protection Factor.")
                        .defineInRange("enchantmentScaling", 5, 1, 15),
                builder
                        .comment("Scaling for (style) protection enchantments. See vanilla Enchantment Protection Factor.")
                        .defineInRange("enchantmentScaling", 5, 1, 15),
                builder
                        .comment("Scaling for potion effects. Resistance (or Weakness) gained through potion effects will be scaled with this factor")
                        .defineInRange("potionScaling", 50, 1, 100),
                builder
                        .comment(
                                "Weight for the essence item to spawn depending on the attack element of the mob. 0 disables this features. 1 is normal spawning rate.")
                        .defineInRange("essenceSpawnWeight", 0.5, 0, 1)
        );
    }


}
