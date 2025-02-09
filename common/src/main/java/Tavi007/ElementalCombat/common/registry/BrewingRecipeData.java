package Tavi007.ElementalCombat.common.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Supplier;

public class BrewingRecipeData {
    private final Potion from;
    private final Item with;
    private final Potion to;

    public BrewingRecipeData(Potion from, Supplier<Item> with, Potion to) {
        this.from = from;
        this.with = with.get();
        this.to = to;
    }

    public BrewingRecipeData(Potion from, Item with, Potion to) {
        this.from = from;
        this.with = with;
        this.to = to;
    }

    public Potion getFrom() {
        return from;
    }

    public Item getWith() {
        return with;
    }

    public Potion getTo() {
        return to;
    }
}
