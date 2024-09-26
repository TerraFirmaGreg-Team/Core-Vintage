package net.dries007.tfc.objects.items.ceramics;

import su.terrafirmagreg.modules.device.object.tile.TileLatexExtractor;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemFluidBowl extends ItemPottery {

  public ItemFluidBowl() {
  }

  @Override
  public boolean canStack(@NotNull ItemStack stack) {
    return false;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (cap != null) {
      FluidStack fluidStack = cap.drain(TileLatexExtractor.MAX_FLUID, false);
      if (fluidStack != null) {
        String fluidname = fluidStack.getLocalizedName();
        int fluidAmount = fluidStack.amount;
        tooltip.add((new TextComponentTranslation("tooltip.tfctech.ceramics.fluid_bowl.fluid", fluidAmount, fluidname)).getFormattedText());
      }
    }
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }

  @Override
  @NotNull
  public String getItemStackDisplayName(@NotNull ItemStack stack) {
    IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (cap != null) {
      FluidStack fluidStack = cap.drain(1, false);
      if (fluidStack != null) {
        String fluidname = fluidStack.getLocalizedName();
        return (new TextComponentTranslation("item.tfctech.ceramics.fluid_bowl.filled", fluidname)).getFormattedText();
      }
    }

    return super.getItemStackDisplayName(stack);
  }

  @Override
  public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new FluidHandlerItemStack(stack, TileLatexExtractor.MAX_FLUID);
  }
}
