package net.dries007.tfc.objects.items;

import su.terrafirmagreg.api.registry.provider.IProviderModel;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemJug;
import net.dries007.caffeineaddon.CaffeineAddon;
import net.dries007.caffeineaddon.init.ModItems;

import org.jetbrains.annotations.NotNull;

public class ItemMug extends ItemJug implements IProviderModel {

  public ItemMug(String name) {
    super();
    this.setTranslationKey(name);
    this.setRegistryName(name);
    this.setCreativeTab(CreativeTabsTFC.CT_FOOD);

    ModItems.ITEMS.add(this);
  }

  //TODO: this is hardcoded as Mug should fix
  @Override
  @NotNull
  public String getItemStackDisplayName(@NotNull ItemStack stack) {
    IFluidHandler bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (bucketCap != null) {
      FluidStack fluidStack = bucketCap.drain(100, false);
      if (fluidStack != null) {
        String fluidname = fluidStack.getLocalizedName();
        return fluidname + " Mug";
      }
    }
    return super.getItemStackDisplayName(stack);
  }

  @Override
  public void onModelRegister() {
    CaffeineAddon.proxy.registerItemRenderer(this, 0, "inventory");
  }

}
