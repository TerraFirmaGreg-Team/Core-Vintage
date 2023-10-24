package com.buuz135.hotornot.network;

import com.buuz135.hotornot.HotOrNot;
import com.buuz135.hotornot.config.HotConfig;
import com.buuz135.hotornot.config.HotLists;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class PacketServerSettings implements IMessage {

	private List<Item> hotList;
	private List<Item> coldList;
	private List<Item> gaseousList;
	private List<Item> exemptionList;
	private int hotItemTemp;
	private int hotFluidTemp;
	private int coldFluidTemp;

	@Deprecated
	public PacketServerSettings() {
	}

	public PacketServerSettings(final List<Item> hotList, final List<Item> coldList, final List<Item> gaseousList, final List<Item> exemptionList,
			final int hotItemTemp, final int hotFluidTemp, final int coldFluidTemp) {
		this.hotList = hotList;
		this.coldList = coldList;
		this.gaseousList = gaseousList;
		this.exemptionList = exemptionList;
		this.hotItemTemp = hotItemTemp;
		this.hotFluidTemp = hotFluidTemp;
		this.coldFluidTemp = coldFluidTemp;
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		final IForgeRegistry<Item> itemRegistry = GameRegistry.findRegistry(Item.class);
		exemptionList = ByteBufUtils.readRegistryEntries(buf, itemRegistry);
		hotList = ByteBufUtils.readRegistryEntries(buf, itemRegistry);
		coldList = ByteBufUtils.readRegistryEntries(buf, itemRegistry);
		gaseousList = ByteBufUtils.readRegistryEntries(buf, itemRegistry);
		hotItemTemp = buf.readInt();
		hotFluidTemp = buf.readInt();
		coldFluidTemp = buf.readInt();
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		ByteBufUtils.writeRegistryEntries(buf, exemptionList);
		ByteBufUtils.writeRegistryEntries(buf, hotList);
		ByteBufUtils.writeRegistryEntries(buf, coldList);
		ByteBufUtils.writeRegistryEntries(buf, gaseousList);
		buf.writeInt(hotItemTemp);
		buf.writeInt(hotFluidTemp);
		buf.writeInt(coldFluidTemp);
	}

	public static class Handler implements IMessageHandler<PacketServerSettings, IMessage> {

		@Nullable
		@Override
		public IMessage onMessage(final PacketServerSettings message, final MessageContext ctx) {
			HotLists.overRideListsTo(message.exemptionList, message.hotList, message.coldList, message.gaseousList);
			// TODO This clobbers the client config
			HotConfig.TEMPERATURE_VALUES.hotItemTemp = message.hotItemTemp;
			HotConfig.TEMPERATURE_VALUES.hotFluidTemp = message.hotFluidTemp;
			HotConfig.TEMPERATURE_VALUES.coldFluidTemp = message.coldFluidTemp;

			HotOrNot.getLog().info("Synced client config server settings");
			return new PacketClientSettings(HotConfig.EFFECT_HANDLING.replaceBrokenHotHolder);
		}
	}
}
