package net.dries007.tfc.module.core.network;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.food.Nutrient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.dries007.tfc.TerraFirmaCraft;

public class SCPacketFoodStatsUpdate implements IMessage, IMessageHandler<SCPacketFoodStatsUpdate, IMessage> {
    private final float[] nutrients;
    private float thirst;

    @SuppressWarnings("unused")
    public SCPacketFoodStatsUpdate() {
        this.nutrients = new float[Nutrient.TOTAL];
    }

    public SCPacketFoodStatsUpdate(float[] nutrients, float thirst) {
        this.nutrients = nutrients;
        this.thirst = thirst;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        thirst = buf.readFloat();
        for (int i = 0; i < nutrients.length; i++) {
            nutrients[i] = buf.readFloat();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(thirst);
        for (float f : nutrients) {
            buf.writeFloat(f);
        }
    }

    @Override
    public IMessage onMessage(SCPacketFoodStatsUpdate message, MessageContext ctx) {
        TerraFirmaCraft.getProxy().getThreadListener(ctx).addScheduledTask(() -> {
            final EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
            if (player != null) {
                FoodStats foodStats = player.getFoodStats();
                if (foodStats instanceof FoodStatsTFC) {
                    ((FoodStatsTFC) foodStats).onReceivePacket(message.nutrients, message.thirst);
                }
            }
        });
        return null;
    }
}
