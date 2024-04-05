package tfcflorae.objects.items.groundcover;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tfcflorae.objects.blocks.groundcover.BlockSurfaceBones;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;


@MethodsReturnNonnullByDefault
public class ItemBone extends ItemBlockTFC {
	public ItemBone(BlockSurfaceBones block) {
		super(block);
		OreDictionaryHelper.register(this, "bone");
		OreDictionaryHelper.register(this, "bones");
		OreDictionaryHelper.register(this, "dye_white");
	}


	@Override
	public @NotNull Size getSize(ItemStack stack) {
		return Size.SMALL;
	}


	@Override
	public @NotNull Weight getWeight(ItemStack stack) {
		return Weight.LIGHT;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return getStackSize(stack);
	}
}
