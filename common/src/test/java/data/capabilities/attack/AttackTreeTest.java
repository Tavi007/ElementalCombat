package data.capabilities.attack;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackBranch;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackTree;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackTreeTest {

    private static final ResourceLocation BASE1 = new ResourceLocation("base1");
    private static final ResourceLocation BASE2 = new ResourceLocation("base2");

    private AttackTree getSinglePropertyTree() {
        AttackTree tree = new AttackTree();
        tree.put(BASE1, getPropertyBranch());
        return tree;
    }

    private AttackBranch getPropertyBranch() {
        AttackProperty property = new AttackProperty("style","element");
        return new AttackBranch(property, 1);
    }

    private AttackTree getWithInnerTree() {
        AttackTree tree = new AttackTree();
        tree.put(BASE1, getTreeBranch());
        return tree;
    }

    private AttackBranch getTreeBranch() {
        AttackTree innerTree = getSinglePropertyTree();
        return new AttackBranch(innerTree, 1);
    }

    private AttackTree getMixedTree() {
        AttackTree tree = new AttackTree();
        tree.put(BASE1, getPropertyBranch());
        tree.put(BASE2, getTreeBranch());
        return tree;
    }

    @Test
    void testTagSingleProperty() {
        AttackTree before = getSinglePropertyTree();
        CompoundTag tag = AttackTree.writeTag(before);
        AttackTree after = AttackTree.readTag(tag);
        assertEquals(before, after);
    }
    @Test
    void testTagInnerTree() {
        AttackTree before = getWithInnerTree();
        CompoundTag tag = AttackTree.writeTag(before);
        AttackTree after = AttackTree.readTag(tag);
        assertEquals(before, after);
    }
    @Test
    void testTagMixed() {
        AttackTree before = getMixedTree();
        CompoundTag tag = AttackTree.writeTag(before);
        AttackTree after = AttackTree.readTag(tag);
        assertEquals(before, after);
    }
}
