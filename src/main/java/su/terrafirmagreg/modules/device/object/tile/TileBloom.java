package su.terrafirmagreg.modules.device.object.tile;

import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.te.TEInventory;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TileBloom extends TEInventory {

  public TileBloom() {
    super(1);
  }

  public void setBloom(ItemStack stack) {
    inventory.setStackInSlot(0, stack);
  }
}
