package tfcflorae.objects.items.ceramics;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import tfcflorae.client.GuiHandler;
import tfcflorae.objects.items.ItemTFCF;
import tfcflorae.util.OreDictionaryHelper;

public class ItemClayStoneware extends ItemTFCF implements IItemSize {
	private final Size size;
	private final Weight weight;

	public ItemClayStoneware(Size size, Weight weight, Object... oreNameParts) {
		this(size, weight);

		for (Object obj : oreNameParts) {
			if (obj instanceof Object[])
				OreDictionaryHelper.register(this, (Object[]) obj);
			else
				OreDictionaryHelper.register(this, obj);
		}
	}

	public ItemClayStoneware(Size size, Weight weight) {
		this.size = size;
		this.weight = weight;
	}


	@Override
	public @NotNull Size getSize(@NotNull ItemStack stack) {
		return size;
	}


	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack stack) {
		return weight;
	}

	@Override
	@NotNull
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @NotNull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && !player.isSneaking() && stack.getCount() > 4) {
			GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.STONEWARE_CLAY);
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	@NotNull
	public void onRightClick(PlayerInteractEvent.RightClickItem event) {
		EnumHand hand = event.getHand();
		if (OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "clayStoneware") && hand == EnumHand.MAIN_HAND) {
			EntityPlayer player = event.getEntityPlayer();
			World world = event.getWorld();
			ItemStack stack = player.getHeldItem(hand);
			if (!world.isRemote && !player.isSneaking() && stack.getCount() > 4) {
				GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.STONEWARE_CLAY);
			}
		}
	}
}
