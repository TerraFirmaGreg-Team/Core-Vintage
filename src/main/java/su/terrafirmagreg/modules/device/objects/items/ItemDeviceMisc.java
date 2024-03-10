package su.terrafirmagreg.modules.device.objects.items;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.item.ItemBase;
import su.terrafirmagreg.api.util.OreDictUtils;

import javax.annotation.Nonnull;

public class ItemDeviceMisc extends ItemBase {
	private final Size size;
	private final Weight weight;

	@Getter
	private final String name;

	public ItemDeviceMisc(Builder builder) {

		this.name = builder.name;
		this.size = builder.size;
		this.weight = builder.weight;


		if (builder.oreNameParts != null)
			OreDictUtils.register(this, builder.oreNameParts);
	}

	@Nonnull
	@Override
	public @NotNull Size getSize(@Nonnull ItemStack stack) {
		return size != null ? size : super.getSize(stack);
	}

	@Nonnull
	@Override
	public @NotNull Weight getWeight(@Nonnull ItemStack stack) {
		return weight != null ? weight : super.getWeight(stack);
	}

	public static class Builder {
		private Size size;
		private Weight weight;
		private @NotNull Object[] oreNameParts;
		private final String name;

		public Builder(String name) {
			this.name = "device/" + name;
		}

		public Builder setSize(Size size) {
			this.size = size;
			return this;
		}

		public Builder setWeight(Weight weight) {
			this.weight = weight;
			return this;
		}

		public Builder ore(@NotNull Object... oreNameParts) {
			this.oreNameParts = oreNameParts;
			return this;
		}

		public ItemDeviceMisc build() {
			return new ItemDeviceMisc(this);
		}
	}


}
