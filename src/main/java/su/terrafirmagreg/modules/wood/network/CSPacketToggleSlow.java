package su.terrafirmagreg.modules.wood.network;

import de.mennomax.astikorcarts.capabilities.PullProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

public class CSPacketToggleSlow implements IMessage, IMessageHandler<CSPacketToggleSlow, IMessage> {

	public CSPacketToggleSlow() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	@Override
	public IMessage onMessage(CSPacketToggleSlow message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
		if (player.isRiding()) {
			Entity ridden = player.getRidingEntity();
			if (ridden instanceof EntityLivingBase && ridden.hasCapability(PullProvider.PULL, null)) {
				if (ridden.getCapability(PullProvider.PULL, null).getDrawn() != null) {
					if (((EntityLivingBase) ridden).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
							.hasModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER)) {
						((EntityLivingBase) ridden).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
								.removeModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER);
					} else {
						((EntityLivingBase) ridden).getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
								.applyModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER);
					}
				}
			}
		}
		return null;
	}
}
