package net.dries007.tfc.objects.items.ceramics;

import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class ItemPottery extends ItemTFC {
	private final Size size;
	private final Weight weight;

	public ItemPottery() {
		this(Size.NORMAL, Weight.LIGHT);
	}

	public ItemPottery(Size size, Weight weight) {
		this.size = size;
		this.weight = weight;
	}


	@Override
	public @NotNull Size getSize(ItemStack stack) {
		return size;
	}


	@Override
	public @NotNull Weight getWeight(ItemStack stack) {
		return weight;
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		// Heat capability, as pottery needs to be able to be fired, or survive despite not having a heat capability
		return new ItemHeatHandler(nbt, 1.0f, 1599f);
	}
}
