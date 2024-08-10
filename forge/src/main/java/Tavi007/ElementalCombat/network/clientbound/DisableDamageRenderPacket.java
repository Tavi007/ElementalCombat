package Tavi007.ElementalCombat.network.clientbound;

import Tavi007.ElementalCombat.ElementalCombat;
import Tavi007.ElementalCombat.capabilities.ImmersionDataCapability;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import Tavi007.ElementalCombat.network.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.Optional;

public class DisableDamageRenderPacket extends Packet {

    private Integer id;

    public DisableDamageRenderPacket(Integer id) {
        this.id = id;
    }

    public DisableDamageRenderPacket(FriendlyByteBuf buf) {
        this.setId(buf.readInt());
    }

    public Integer getId() {
        return this.id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
    }

    @Override
    public void handle(Context context) {
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.enqueueWork(() -> {
            if (!isValid()) {
                return;
            }

            Optional<Level> level = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
            if (!level.isPresent()) {
                ElementalCombat.LOGGER.warn(" context could not provide a ClientLevel.");
                return;
            }

            processMessage(level.get());
            context.setPacketHandled(true);
        });
    }

    private boolean isValid() {
        return id != null;
    }

    private void processMessage(Level level) {
        Entity entity = level.getEntity(id);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            ImmersionData data = (ImmersionData) livingEntity.getCapability(ImmersionDataCapability.IMMERSION_DATA_CAPABILITY, null)
                    .orElse(new ImmersionData());
            data.disableFlag = true;
        }
    }

}
