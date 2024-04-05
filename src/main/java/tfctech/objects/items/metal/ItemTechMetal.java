package tfctech.objects.items.metal;

import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Since TFC has Metal.ItemType we can't reuse {@link net.dries007.tfc.objects.items.metal.ItemMetal} directly
 */
public class ItemTechMetal extends ItemTFC implements IMetalItem {
	private static final Map<Metal, EnumMap<ItemType, ItemTechMetal>> TABLE = new HashMap<>();
	protected final Metal metal;
	protected final ItemType type;

	public ItemTechMetal(Metal metal, ItemType type) {
		this.metal = metal;
		this.type = type;
		if (!TABLE.containsKey(metal)) {
			TABLE.put(metal, new EnumMap<>(ItemType.class));
		}
		TABLE.get(metal).put(type, this);
		setNoRepair();

	}

	@Nullable
	public static ItemTechMetal get(Metal metal, ItemTechMetal.ItemType type) {
		return TABLE.get(metal).get(type);
	}

	public ItemType getType() {
		return type;
	}


	@Override
	public @NotNull Size getSize(@NotNull ItemStack itemStack) {
		switch (type) {
			case WIRE:
			case BOWL_MOUNT:
				return Size.LARGE;
			case GEAR:
			case RACKWHEEL:
				return Size.NORMAL;
			default:
				return Size.SMALL;
		}
	}


	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
		switch (type) {
			case RACKWHEEL:
			case GEAR:
				return Weight.HEAVY;
			case GROOVE:
			case WIRE:
			case SLEEVE:
			case STRIP:
				return Weight.LIGHT;
			default:
				return Weight.MEDIUM;
		}
	}

	@Override
	@NotNull
	public String getItemStackDisplayName(@NotNull ItemStack stack) {
		//noinspection ConstantConditions
		String metalName = (new TextComponentTranslation("tfc.types.metal." + metal.getRegistryName()
		                                                                           .getPath()
		                                                                           .toLowerCase())).getFormattedText();
		return (new TextComponentTranslation("item.tfctech.metalitem." + type.name()
		                                                                     .toLowerCase() + ".name", metalName)).getFormattedText();
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
	}

	@NotNull
	@Override
	public Metal getMetal(ItemStack itemStack) {
		return metal;
	}

	@Override
	public int getSmeltAmount(ItemStack itemStack) {
		return type.getSmeltAmount();
	}

	public enum ItemType {
		BOWL_MOUNT(100),
		DRAW_PLATE(100),
		GROOVE(50, false, ItemGroove::new),
		INDUCTOR(200),
		TONGS(100),
		STRIP(50),
		LONG_ROD(100),
		ROD(50),
		BOLT(25),
		SCREW(25),
		SLEEVE(100, true),
		RACKWHEEL_PIECE(100, true),
		RACKWHEEL(400),
		GEAR(400, false, ItemGear::new),
		WIRE(50, false, ItemWire::new);

		private final int smeltAmount;
		private final boolean hasMold;
		private final BiFunction<Metal, ItemType, Item> supplier;

		ItemType(int smeltAmount) {
			this(smeltAmount, false);
		}

		ItemType(int smeltAmount, boolean hasMold) {
			this(smeltAmount, hasMold, ItemTechMetal::new);
		}

		ItemType(int smeltAmount, boolean hasMold, @NotNull BiFunction<Metal, ItemType, Item> supplier) {
			this.smeltAmount = smeltAmount;
			this.hasMold = hasMold;
			this.supplier = supplier;
		}

		public static Item create(Metal metal, ItemType type) {
			return type.supplier.apply(metal, type);
		}

		public int getSmeltAmount() {
			return smeltAmount;
		}

		public boolean hasMold() {
			return hasMold;
		}
	}
}
