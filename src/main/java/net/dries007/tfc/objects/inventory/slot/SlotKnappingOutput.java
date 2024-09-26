package net.dries007.tfc.objects.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import org.jetbrains.annotations.NotNull;

public class SlotKnappingOutput extends SlotItemHandler {

  private final Runnable onSlotTake;

  public SlotKnappingOutput(IItemHandler inventory, int idx, int x, int y, Runnable onSlotTake) {
    super(inventory, idx, x, y);
    this.onSlotTake = onSlotTake;
  }

  @Override
  @NotNull
  public ItemStack onTake(EntityPlayer thePlayer, @NotNull ItemStack stack) {
    onSlotTake.run();
    return super.onTake(thePlayer, stack);
  }

  @Override
  public boolean isItemValid(@NotNull ItemStack stack) {
    return false;
  }
}
