package tfctech.network;

import io.netty.buffer.ByteBuf;
import net.dries007.tfc.TerraFirmaCraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTileEntityUpdate implements IMessage {
	private NBTTagCompound tileEntity;
	private BlockPos pos;

	@SuppressWarnings("unused")
	@Deprecated
	public PacketTileEntityUpdate() {}

	public PacketTileEntityUpdate(TileEntity te) {
		pos = te.getPos();
		tileEntity = te.serializeNBT();
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		pos = BlockPos.fromLong(byteBuf.readLong());
		tileEntity = ByteBufUtils.readTag(byteBuf);
	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.writeLong(pos.toLong());
		ByteBufUtils.writeTag(byteBuf, tileEntity);
	}

	public static class Handler implements IMessageHandler<PacketTileEntityUpdate, IMessage> {
		@Override
		public IMessage onMessage(PacketTileEntityUpdate message, MessageContext ctx) {
			EntityPlayer player = TerraFirmaCraft.getProxy().getPlayer(ctx);
			if (player != null) {
				World world = player.getEntityWorld();
				TileEntity te = world.getTileEntity(message.pos);
				if (te != null) {
					te.readFromNBT(message.tileEntity);
				}
			}
			return null;
		}
	}
}
