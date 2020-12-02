package Tavi007.ElementalCombat.capabilities;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.ElementalCombatAPI;
import Tavi007.ElementalCombat.capabilities.attack.AttackData;
import Tavi007.ElementalCombat.capabilities.defense.DefenseData;
import Tavi007.ElementalCombat.network.ItemMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.network.PacketDistributor;

public class CapabilityContainerListener implements IContainerListener {

	private final ServerPlayerEntity player;
	
	public CapabilityContainerListener(ServerPlayerEntity player) {
		this.player = player;
	}
	
	@Override
	public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
		// create BulkItemMessage and send it. 
		// or send each item separately with sendSlotContents()
//		int size=itemsList.size();
//		for(int i=0; i<size; i++) {
//			sendSlotContents(containerToSend, i, itemsList.get(i));
//		}
	}

	@Override
	public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
		// create a single ItemMessage and send it.
		AttackData atckData = ElementalCombatAPI.getAttackData(stack);
		DefenseData defData = ElementalCombatAPI.getDefenseData(stack);
		ItemMessage message = new ItemMessage(atckData, defData, containerToSend.windowId, slotInd);
		ElementalCombat.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), message);
	}

	@Override
	public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
		// No-op
	}

}
