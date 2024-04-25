package su.terrafirmagreg.modules.wood.network;

import su.terrafirmagreg.modules.core.api.capabilities.pull.PullCapability;
import su.terrafirmagreg.modules.wood.ModuleWood;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


import io.netty.buffer.ByteBuf;

public class CSPacketToggleSlow implements IMessage, IMessageHandler<CSPacketToggleSlow, IMessage> {

    public CSPacketToggleSlow() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(CSPacketToggleSlow message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        if (player.isRiding()) {
            Entity ridden = player.getRidingEntity();
            if (ridden instanceof EntityLivingBase entityLivingBase && ridden.hasCapability(PullCapability.PULL_CAPABILITY, null)) {
                if (ridden.getCapability(PullCapability.PULL_CAPABILITY, null).getDrawn() != null) {
                    ModuleWood.LOGGER.info("toggle slow");

                    if (entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                            .hasModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER)) {
                        entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                                .removeModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER);
                    } else {
                        entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                                .applyModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER);
                    }
                }
            }
        }
        return null;
    }
}
