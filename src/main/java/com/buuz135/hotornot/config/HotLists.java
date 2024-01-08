package com.buuz135.hotornot.config;

import com.buuz135.hotornot.network.PacketServerSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public final class HotLists {

	private static final List<Item> HOT_LIST = new ArrayList<>();
	private static final List<Item> COLD_LIST = new ArrayList<>();
	private static final List<Item> GASEOUS_LIST = new ArrayList<>();
	private static final List<Item> EXEMPTION_LIST = new ArrayList<>();

	static {
		Arrays.stream(HotConfig.MANUAL_ENTRIES.itemRemovals)
		      .map(itemRemoval -> Item.REGISTRY.getObject(new ResourceLocation(itemRemoval)))
		      .filter(Objects::nonNull)
		      .forEach(EXEMPTION_LIST::add);
		Arrays.stream(HotConfig.MANUAL_ENTRIES.hotItemAdditions)
		      .map(itemRegistryName -> Item.REGISTRY.getObject(new ResourceLocation(itemRegistryName)))
		      .filter(Objects::nonNull)
		      .forEach(HOT_LIST::add);
		Arrays.stream(HotConfig.MANUAL_ENTRIES.coldItemAdditions)
		      .map(itemRegistryName -> Item.REGISTRY.getObject(new ResourceLocation(itemRegistryName)))
		      .filter(Objects::nonNull)
		      .forEach(COLD_LIST::add);
		Arrays.stream(HotConfig.MANUAL_ENTRIES.gaseousItemAdditions)
		      .map(itemRegistryName -> Item.REGISTRY.getObject(new ResourceLocation(itemRegistryName)))
		      .filter(Objects::nonNull)
		      .forEach(GASEOUS_LIST::add);
	}

	/**
	 * Checks if the stack is exempt from our checks
	 *
	 * @param itemStack The item stack to check
	 * @return If the item stack is exempt from the checks
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean isExempt(final ItemStack itemStack) {
		return EXEMPTION_LIST.contains(itemStack.getItem());
	}

	/**
	 * Checks if an item is considered to be hot
	 *
	 * @param itemStack The item stack to check
	 * @return If the item is considered hot
	 */
	public static boolean isHot(final ItemStack itemStack) {
		return !isExempt(itemStack) && HOT_LIST.contains(itemStack.getItem());
	}

	/**
	 * Checks if an item is considered to be cold
	 *
	 * @param itemStack The item stack to check
	 * @return If the item is considered cold
	 */
	public static boolean isCold(final ItemStack itemStack) {
		return !isExempt(itemStack) && COLD_LIST.contains(itemStack.getItem());
	}

	/**
	 * Checks if an item is considered to be gaseous
	 *
	 * @param itemStack The item stack to check
	 * @return If the item is considered gaseous
	 */
	public static boolean isGaseous(final ItemStack itemStack) {
		return !isExempt(itemStack) && GASEOUS_LIST.contains(itemStack.getItem());
	}

	/**
	 * @return A packet containing the server settings to be sent to a client
	 */
	public static PacketServerSettings getServerConfigPacket() {
		return new PacketServerSettings(HOT_LIST, COLD_LIST, GASEOUS_LIST, EXEMPTION_LIST, HotConfig.TEMPERATURE_VALUES.hotItemTemp,
				HotConfig.TEMPERATURE_VALUES.hotFluidTemp, HotConfig.TEMPERATURE_VALUES.coldFluidTemp);
	}

	/**
	 * Clears then fills the lists with the items provided, used to ensure the client tooltips are accurate
	 */
	@SideOnly(Side.CLIENT)
	public static void overRideListsTo(final List<Item> exemptionList, final List<Item> hotList, final List<Item> coldList,
	                                   final List<Item> gaseousList) {
		EXEMPTION_LIST.clear();
		EXEMPTION_LIST.addAll(exemptionList);
		HOT_LIST.clear();
		HOT_LIST.addAll(hotList);
		COLD_LIST.clear();
		COLD_LIST.addAll(coldList);
		GASEOUS_LIST.clear();
		GASEOUS_LIST.addAll(gaseousList);
	}
}
