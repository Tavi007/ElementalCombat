package data.capabilities.attack;

import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackBranch;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackProperty;
import Tavi007.ElementalCombat.common.data.capabilities.attack.AttackTree;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttackTreeTest {

    private static final ResourceLocation BASE1 = new ResourceLocation("base1");
    private static final ResourceLocation BASE2 = new ResourceLocation("base2");
    private static final ResourceLocation BASE3 = new ResourceLocation("base3");

    private AttackTree getSinglePropertyTree() {
        AttackTree tree = new AttackTree();
        tree.put(BASE1, getPropertyBranch());
        return tree;
    }

    private AttackBranch getPropertyBranch() {
        AttackProperty property = new AttackProperty("style", "element");
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

    @Test
    void testBufferSingleProperty() {
        AttackTree before = getSinglePropertyTree();
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        AttackTree.write(buffer, before);
        AttackTree after = AttackTree.read(buffer);
        assertEquals(before, after);
    }

    @Test
    void testBufferInnerTree() {
        AttackTree before = getWithInnerTree();
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        AttackTree.write(buffer, before);
        AttackTree after = AttackTree.read(buffer);
        assertEquals(before, after);
    }

    @Test
    void testBufferMixed() {
        AttackTree before = getMixedTree();
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        AttackTree.write(buffer, before);
        AttackTree after = AttackTree.read(buffer);
        assertEquals(before, after);
    }

    @Test
    void testQueuePutNew() {
        AttackTree tree = new AttackTree();
        Queue<ResourceLocation> locations = new LinkedList<>();
        locations.add(BASE1);

        tree.put(locations, getPropertyBranch());

        // should be empty now
        locations.add(BASE1);
        assertEquals(tree.get(locations).get(), getPropertyBranch());
    }

    @Test
    void testQueuePutInnerNew() {
        AttackTree tree = new AttackTree();
        Queue<ResourceLocation> locations = new LinkedList<>();
        locations.add(BASE1);
        locations.add(BASE2);

        tree.put(locations, getPropertyBranch());

        // should be empty now
        locations.add(BASE1);
        locations.add(BASE2);
        assertEquals(tree.get(locations).get(), getPropertyBranch());
    }

    @Test
    void testQueueRemoveInnerTreeLeaf() {
        AttackTree tree = getWithInnerTree();
        Queue<ResourceLocation> locations = new LinkedList<>();
        locations.add(BASE1);
        locations.add(BASE1);

        tree.remove(locations);

        // should be empty now
        locations.add(BASE1);
        locations.add(BASE1);
        Optional<AttackBranch> branch = tree.get(locations);
        assert (branch.isEmpty());
    }
}
