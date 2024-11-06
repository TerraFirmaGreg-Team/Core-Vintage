package su.terrafirmagreg.core.mixin.cellars;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import net.dries007.tfc.objects.inventory.capability.IItemHandlerSidedCallback;
import net.dries007.tfc.objects.items.ceramics.ItemSmallVessel;
import net.dries007.tfc.objects.te.TEInventory;
import net.sharkbark.cellars.blocks.tileentity.TECellarShelf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TECellarShelf.class, remap = false)
public abstract class TECellarShelfMixin extends TEInventory implements IItemHandlerSidedCallback, ITickable {

  protected TECellarShelfMixin(int inventorySize) {
    super(inventorySize);
  }

  @Inject(method = "canInsert", at = @At(value = "HEAD"), remap = false, cancellable = true)
  public void onCanInsert(int i, ItemStack itemStack, EnumFacing enumFacing, CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue((itemStack.getItem() instanceof ItemFood) || (itemStack.getItem() instanceof ItemSmallVessel));
  }
}
