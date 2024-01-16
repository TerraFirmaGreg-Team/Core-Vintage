package tfcflorae.objects.items.groundcover;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tfcflorae.objects.blocks.groundcover.BlockSurfaceSeashells;
import tfcflorae.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemSeashell extends ItemBlockTFC {
	public ItemSeashell(BlockSurfaceSeashells block) {
		super(block);
		OreDictionaryHelper.register(this, "seashell");
		OreDictionaryHelper.register(this, "seashells");
	}

	@Nonnull
	@Override
	public @NotNull Size getSize(ItemStack stack) {
		return Size.SMALL;
	}

	@Nonnull
	@Override
	public @NotNull Weight getWeight(ItemStack stack) {
		return Weight.LIGHT;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return getStackSize(stack);
	}
}
