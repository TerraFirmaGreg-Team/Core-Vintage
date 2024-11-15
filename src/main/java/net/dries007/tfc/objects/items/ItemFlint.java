package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.dries007.tfcflorae.client.GuiHandler;
import net.dries007.tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

public class ItemFlint extends ItemTFCF implements ICapabilitySize {

  private final Size size;
  private final Weight weight;

  public ItemFlint(Size size, Weight weight, Object... oreNameParts) {
    this(size, weight);

    for (Object obj : oreNameParts) {
      if (obj instanceof Object[]) {
        OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        OreDictionaryHelper.register(this, obj);
      }
    }
  }

  public ItemFlint(Size size, Weight weight) {
    this.size = size;
    this.weight = weight;
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return weight;
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return size;
  }

  @Override
  @NotNull
  public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @NotNull EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!world.isRemote && !player.isSneaking() && stack.getCount() > 1) {
      GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.FLINT);
    }
    return new ActionResult<>(EnumActionResult.SUCCESS, stack);
  }

  public void onRightClick(PlayerInteractEvent.RightClickItem event) {
    EnumHand hand = event.getHand();
    if (OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "flint") && hand == EnumHand.MAIN_HAND) {
      EntityPlayer player = event.getEntityPlayer();
      World world = event.getWorld();
      ItemStack stack = player.getHeldItem(hand);
      if (!world.isRemote && !player.isSneaking() && stack.getCount() > 1) {
        GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.FLINT);
      }
    }
  }
}
