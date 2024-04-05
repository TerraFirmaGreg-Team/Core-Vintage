package net.dries007.tfc.objects.items.wood;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.wood.BlockDoorTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


@MethodsReturnNonnullByDefault
public class ItemDoorTFC extends ItemDoor implements IItemSize {
	private static final Map<Tree, ItemDoorTFC> MAP = new HashMap<>();
	public final Tree wood;

	public ItemDoorTFC(BlockDoorTFC block) {
		super(block);
		if (MAP.put(block.wood, this) != null) throw new IllegalStateException("There can only be one.");
		wood = block.wood;
		OreDictionaryHelper.register(this, "door", "wood");
		//noinspection ConstantConditions
		OreDictionaryHelper.register(this, "door", "wood", wood.getRegistryName().getPath());
	}

	public static ItemDoorTFC get(Tree wood) {
		return MAP.get(wood);
	}


	@Override
	public @NotNull Size getSize(ItemStack stack) {
		return Size.VERY_LARGE; // Can't be stored
	}


	@Override
	public @NotNull Weight getWeight(ItemStack stack) {
		return Weight.HEAVY; // Stacksize = 4
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return getStackSize(stack);
	}
}
