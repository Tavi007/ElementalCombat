package Tavi007.ElementalCombat.common.network;

import Tavi007.ElementalCombat.common.capabilities.CapabilitiesAccessors;
import Tavi007.ElementalCombat.common.data.capabilities.ImmersionData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class DisableDamageRenderPacket extends AbstractPacket {

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

//    @Override
//    public void handle(Context context) {
//        LogicalSide sideReceived = context.getDirection().getReceptionSide();
//        context.enqueueWork(() -> {
//            if (!isValid()) {
//                return;
//            }
//
//            Optional<Level> level = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
//            if (!level.isPresent()) {
//                ElementalCombat.LOGGER.warn(" context could not provide a ClientLevel.");
//                return;
//            }
//
//            processMessage(level.get());
//            context.setPacketHandled(true);
//        });
//    }

    @Override
    public boolean isValid() {
        return id != null;
    }

    @Override
    public void processPacket(Level level) {
        Entity entity = level.getEntity(id);
        if (entity instanceof LivingEntity livingEntity) {
            ImmersionData data = CapabilitiesAccessors.getImmersionData(livingEntity);
            data.disableFlag = true;
        }
    }

}
