package Tavi007.ElementalCombat.util;

import java.util.HashMap;
import java.util.Set;

import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.attack.AttackLayer;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseLayer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class ElementalCombatNBTHelper {

    /**
     * Writes the attack-combat data {@link AttackData} to the {@link CompoundNBT}.
     * 
     * @param nbt
     *            The CompoundNBT.
     * @param data
     *            The AttackData.
     */
    public static void writeAttackDataToNBT(CompoundNBT nbt, AttackData data) {
        nbt.put("elementalcombat_attack", fromAttackLayersToNBT(data.getLayers()));
    }

    /**
     * Reads the attack-combat data {@link AttackData} from the {@link CompoundNBT}.
     * Note: It will read whatever was written into nbt by {@link ElementalCombatNBTHelper#writeAttackDataToNBT}.
     * 
     * @param nbt
     *            The CompoundNBT.
     * @return The AttackData. Default is an empty instance.
     */
    public static AttackData readAttackDataFromNBT(CompoundNBT nbt) {
        AttackData data = new AttackData();
        fromNBTToAttackLayers(nbt.getCompound("elementalcombat_attack")).forEach((rl, layer) -> {
            data.putLayer(rl, layer);
        });
        return data;
    }

    /**
     * Writes the defense-combat data {@link DefenseData} to the {@link CompoundNBT}.
     * 
     * @param nbt
     *            The CompoundNBT.
     * @param data
     *            The DefenseData.
     */
    public static void writeDefenseDataToNBT(CompoundNBT nbt, DefenseData data) {
        nbt.put("elementalcombat_defense", fromDefenseLayersToNBT(data.getLayers()));
    }

    /**
     * Reads the defense-combat data {@link DefenseData} from the {@link CompoundNBT}.
     * Note: It will read whatever was written into nbt by {@link ElementalCombatNBTHelper#writeDefenseDataToNBT}.
     * 
     * @param nbt
     *            The CompoundNBT.
     * @return The DefenseData. Default is an empty instance.
     */
    public static DefenseData readDefenseDataFromNBT(CompoundNBT nbt) {
        DefenseData data = new DefenseData();
        fromNBTToDefenseLayers(nbt.getCompound("elementalcombat_defense")).forEach((rl, layer) -> {
            data.putLayer(rl, layer);
        });
        return data;
    }

    // read defense data from nbt helper methods
    private static HashMap<ResourceLocation, AttackLayer> fromNBTToAttackLayers(CompoundNBT nbtCompound) {
        HashMap<ResourceLocation, AttackLayer> map = new HashMap<>();
        if (nbtCompound != null) {
            Set<String> keySet = nbtCompound.getAllKeys();
            for (String key : keySet) {
                map.put(new ResourceLocation(key), fromNBTToAttackLayer((CompoundNBT) nbtCompound.get(key)));
            }
        }
        return map;
    }

    private static HashMap<ResourceLocation, DefenseLayer> fromNBTToDefenseLayers(CompoundNBT nbtCompound) {
        HashMap<ResourceLocation, DefenseLayer> map = new HashMap<>();
        if (nbtCompound != null) {
            Set<String> keySet = nbtCompound.getAllKeys();
            for (String key : keySet) {
                map.put(new ResourceLocation(key), fromNBTToDefenseLayer((CompoundNBT) nbtCompound.get(key)));
            }
        }
        return map;
    }

    private static AttackLayer fromNBTToAttackLayer(CompoundNBT nbtCompound) {
        AttackLayer layer = new AttackLayer();
        if (nbtCompound != null) {
            layer.setStyle(nbtCompound.getString("style"));
            layer.setElement(nbtCompound.getString("element"));
        }
        return layer;
    }

    private static DefenseLayer fromNBTToDefenseLayer(CompoundNBT nbtCompound) {
        DefenseLayer layer = new DefenseLayer();
        if (nbtCompound != null) {
            layer.addStyle(fromNBTToDefenseMap((CompoundNBT) nbtCompound.get("style")));
            layer.addElement(fromNBTToDefenseMap((CompoundNBT) nbtCompound.get("element")));
        }
        return layer;
    }

    private static HashMap<String, Integer> fromNBTToDefenseMap(CompoundNBT nbtCompound) {
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
    private static CompoundNBT fromAttackLayersToNBT(HashMap<ResourceLocation, AttackLayer> layers) {
        CompoundNBT nbt = new CompoundNBT();
        if (layers != null) {
            layers.forEach((rl, layer) -> {
                nbt.put(rl.toString(), fromAttackLayerToNBT(layer));
            });
        }
        return nbt;
    }

    private static CompoundNBT fromDefenseLayersToNBT(HashMap<ResourceLocation, DefenseLayer> layers) {
        CompoundNBT nbt = new CompoundNBT();
        if (layers != null) {
            layers.forEach((rl, layer) -> {
                nbt.put(rl.toString(), fromDefenseLayerToNBT(layer));
            });
        }
        return nbt;
    }

    private static CompoundNBT fromAttackLayerToNBT(AttackLayer layer) {
        CompoundNBT nbt = new CompoundNBT();
        if (layer != null) {
            nbt.putString("style", layer.getStyle());
            nbt.putString("element", layer.getElement());
        }
        return nbt;
    }

    private static CompoundNBT fromDefenseLayerToNBT(DefenseLayer layer) {
        CompoundNBT nbt = new CompoundNBT();
        if (layer != null) {
            nbt.put("style", fromDefenseMapToNBT(layer.getStyleFactor()));
            nbt.put("element", fromDefenseMapToNBT(layer.getElementFactor()));
        }
        return nbt;
    }

    private static CompoundNBT fromDefenseMapToNBT(HashMap<String, Integer> map) {
        CompoundNBT nbt = new CompoundNBT();
        if (map != null) {
            map.forEach((elemString, value) -> {
                nbt.putInt(elemString, value);
            });
        }
        return nbt;
    }

}
