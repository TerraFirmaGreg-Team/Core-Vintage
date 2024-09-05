package su.terrafirmagreg.modules.metal.objects.itemblock;

import su.terrafirmagreg.api.base.item.BaseItemBlock;
import su.terrafirmagreg.modules.metal.ConfigMetal;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;


import net.dries007.tfc.api.capability.fluid.FluidWhitelistHandlerComplex;

import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class ItemBlockMetalLamp
    extends BaseItemBlock {

  public static int CAPACITY;

  public ItemBlockMetalLamp(Block block) {
    super(block);
    CAPACITY = ConfigMetal.BLOCKS.LAMP.tank;

  }

  public static Set<Fluid> getValidFluids() {
    String[] fluidNames = ConfigMetal.BLOCKS.LAMP.fuels;
    Set<Fluid> validFluids = new HashSet<>();
    for (String fluidName : fluidNames) {
      validFluids.add(FluidRegistry.getFluid(fluidName));
    }
    return validFluids;
  }

  @Override
  public boolean canStack(ItemStack stack) {
    IFluidHandler lampCap = stack.getCapability(
        CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (lampCap != null) {
      return lampCap.drain(CAPACITY, false) == null;
    }
    return true;
  }

  @Override
  public String getItemStackDisplayName(ItemStack stack) {
    IFluidHandler fluidCap = stack.getCapability(
        CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (fluidCap != null) {
      FluidStack fluidStack = fluidCap.drain(CAPACITY, false);
      if (fluidStack != null) {
        String fluidName = fluidStack.getLocalizedName();
        return new TextComponentTranslation(getTranslationKey() + ".filled.name",
            fluidName).getFormattedText();
      }
    }
    return super.getItemStackDisplayName(stack);
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new FluidWhitelistHandlerComplex(stack, CAPACITY, getValidFluids());
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (isInCreativeTab(tab)) {
      items.add(new ItemStack(this));
      for (Fluid fluid : getValidFluids()) {
        ItemStack stack = new ItemStack(this);
        IFluidHandlerItem cap = stack.getCapability(
            CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (cap != null) {
          cap.fill(new FluidStack(fluid, CAPACITY), true);
        }
        items.add(stack);
      }
    }
  }

  //no need for @Override itemRightClick to fill or place since fluidhandler interactions and placement are handled before it is called

}
