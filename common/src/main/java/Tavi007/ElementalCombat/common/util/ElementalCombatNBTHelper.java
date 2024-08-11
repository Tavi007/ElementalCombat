package Tavi007.ElementalCombat.common.util;

import Tavi007.ElementalCombat.common.api.data.AttackLayer;
import Tavi007.ElementalCombat.common.api.data.DefenseLayer;
import Tavi007.ElementalCombat.common.data.capabilities.AttackData;
import Tavi007.ElementalCombat.common.data.capabilities.DefenseData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ElementalCombatNBTHelper {

    /**
     * Writes the attack-combat data {@link AttackData} to the {@link CompoundTag}.
     *
     * @param nbt  The CompoundTag.
     * @param data The AttackData.
     */
    public static void writeAttackDataToNBT(CompoundTag nbt, AttackData data) {
        nbt.put("elementalcombat_attack", fromAttackLayersToNBT(data.getLayers()));
    }

    /**
     * Reads the attack-combat data {@link AttackData} from the {@link CompoundTag}.
     * Note: It will read whatever was written into nbt by {@link ElementalCombatNBTHelper#writeAttackDataToNBT}.
     *
     * @param nbt The CompoundTag.
     * @return The AttackData. Default is an empty instance.
     */
    public static AttackData readAttackDataFromNBT(CompoundTag nbt) {
        AttackData data = new AttackData();
        fromNBTToAttackLayers(nbt.getCompound("elementalcombat_attack")).forEach((rl, layer) -> {
            data.putLayer(rl, layer);
        });
        return data;
    }

    /**
     * Writes the defense-combat data {@link DefenseData} to the {@link CompoundTag}.
     *
     * @param nbt  The CompoundTag.
     * @param data The DefenseData.
     */
    public static void writeDefenseDataToNBT(CompoundTag nbt, DefenseData data) {
        nbt.put("elementalcombat_defense", fromDefenseLayersToNBT(data.getLayers()));
    }

    /**
     * Reads the defense-combat data {@link DefenseData} from the {@link CompoundTag}.
     * Note: It will read whatever was written into nbt by {@link ElementalCombatNBTHelper#writeDefenseDataToNBT}.
     *
     * @param nbt The CompoundTag.
     * @return The DefenseData. Default is an empty instance.
     */
    public static DefenseData readDefenseDataFromNBT(CompoundTag nbt) {
        DefenseData data = new DefenseData();
        fromNBTToDefenseLayers(nbt.getCompound("elementalcombat_defense")).forEach((rl, layer) -> {
            data.putLayer(rl, layer);
        });
        return data;
    }

    // read defense data from nbt helper methods
    private static LinkedHashMap<ResourceLocation, AttackLayer> fromNBTToAttackLayers(CompoundTag nbtCompound) {
        LinkedHashMap<ResourceLocation, AttackLayer> map = new LinkedHashMap<>();
        if (nbtCompound != null) {
            Set<String> keySet = nbtCompound.getAllKeys();
            for (String key : keySet) {
                map.put(new ResourceLocation(key), fromNBTToAttackLayer((CompoundTag) nbtCompound.get(key)));
            }
        }
        return map;
    }

    private static HashMap<ResourceLocation, DefenseLayer> fromNBTToDefenseLayers(CompoundTag nbtCompound) {
        HashMap<ResourceLocation, DefenseLayer> map = new HashMap<>();
        if (nbtCompound != null) {
            Set<String> keySet = nbtCompound.getAllKeys();
            for (String key : keySet) {
                map.put(new ResourceLocation(key), fromNBTToDefenseLayer((CompoundTag) nbtCompound.get(key)));
            }
        }
        return map;
    }

    private static AttackLayer fromNBTToAttackLayer(CompoundTag nbtCompound) {
        AttackLayer layer = new AttackLayer();
        if (nbtCompound != null) {
            layer.setStyle(nbtCompound.getString("style"));
            layer.setElement(nbtCompound.getString("element"));
        }
        return layer;
    }

    private static DefenseLayer fromNBTToDefenseLayer(CompoundTag nbtCompound) {
        DefenseLayer layer = new DefenseLayer();
        if (nbtCompound != null) {
            layer.addStyles(fromNBTToDefenseMap((CompoundTag) nbtCompound.get("styles")));
            layer.addElements(fromNBTToDefenseMap((CompoundTag) nbtCompound.get("elements")));
        }
        return layer;
    }

    private static HashMap<String, Integer> fromNBTToDefenseMap(CompoundTag nbtCompound) {
        HashMap<String, Integer> map = new HashMap<>();
        if (nbtCompound != null) {
            Set<String> keySet = nbtCompound.getAllKeys();
            for (String key : keySet) {
                Integer value = nbtCompound.getInt(key);
                map.put(key, value);
            }
        }
        return map;
    }

    // write to nbt
    private static CompoundTag fromAttackLayersToNBT(HashMap<ResourceLocation, AttackLayer> layers) {
        CompoundTag nbt = new CompoundTag();
        if (layers != null) {
            layers.forEach((rl, layer) -> {
                nbt.put(rl.toString(), fromAttackLayerToNBT(layer));
            });
        }
        return nbt;
    }

    private static CompoundTag fromDefenseLayersToNBT(HashMap<ResourceLocation, DefenseLayer> layers) {
        CompoundTag nbt = new CompoundTag();
        if (layers != null) {
            layers.forEach((rl, layer) -> {
                nbt.put(rl.toString(), fromDefenseLayerToNBT(layer));
            });
        }
        return nbt;
    }

    private static CompoundTag fromAttackLayerToNBT(AttackLayer layer) {
        CompoundTag nbt = new CompoundTag();
        if (layer != null) {
            nbt.putString("style", layer.getStyle());
            nbt.putString("element", layer.getElement());
        }
        return nbt;
    }

    private static CompoundTag fromDefenseLayerToNBT(DefenseLayer layer) {
        CompoundTag nbt = new CompoundTag();
        if (layer != null) {
            nbt.put("styles", fromDefenseMapToNBT(layer.getStyles()));
            nbt.put("elements", fromDefenseMapToNBT(layer.getElements()));
        }
        return nbt;
    }

    private static CompoundTag fromDefenseMapToNBT(Map<String, Integer> map) {
        CompoundTag nbt = new CompoundTag();
        if (map != null) {
            map.forEach((elemString, value) -> {
                nbt.putInt(elemString, value);
            });
        }
        return nbt;
    }

}
