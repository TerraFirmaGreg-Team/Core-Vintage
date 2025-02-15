package net.dries007.tfc.objects.items.itemblock;

import su.terrafirmagreg.modules.core.capabilities.fluid.CapabilityProviderFluid;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHandlerHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.metal.BlockMetalLamp;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemBlockMetalLamp extends ItemBlockTFC implements ICapabilityMetal {

  private static final Map<Metal, ItemBlockMetalLamp> TABLE = new HashMap<>();
  public static int CAPACITY;

  public ItemBlockMetalLamp(Metal metal) {
    super(BlockMetalLamp.get(metal));
    CAPACITY = ConfigTFC.Devices.LAMP.tank;
    if (!TABLE.containsKey(metal)) {TABLE.put(metal, this);}

    // In the interest of not writing a joint heat / fluid capability that extends ICapabilityProvider, I think this is justified
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(this), () -> new CapabilityProviderHeat(null, metal.getSpecificHeat(), metal.getMeltTemp()));
    OreDictionaryHelper.register(this, "lamp");
  }

  public static Set<Fluid> getValidFluids() {
    String[] fluidNames = ConfigTFC.Devices.LAMP.fuels;
    Set<Fluid> validFluids = new HashSet<>();
    for (String fluidName : fluidNames) {
      validFluids.add(FluidRegistry.getFluid(fluidName));
    }
    return validFluids;
  }

  public static Item get(Metal metal) {
    return TABLE.get(metal);
  }

  @Override
  public boolean canStack(@Nonnull ItemStack stack) {
    IFluidHandler lampCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (lampCap != null) {
      return lampCap.drain(CAPACITY, false) == null;
    }
    return true;
  }

  @Override
  @Nonnull
  public String getItemStackDisplayName(@Nonnull ItemStack stack) {
    IFluidHandler fluidCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    if (fluidCap != null) {
      FluidStack fluidStack = fluidCap.drain(CAPACITY, false);
      if (fluidStack != null) {
        String fluidName = fluidStack.getLocalizedName();
        return new TextComponentTranslation(getTranslationKey() + ".filled.name", fluidName).getFormattedText();
      }
    }
    return super.getItemStackDisplayName(stack);
  }

  @Override
  public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new CapabilityProviderFluid.WhitelistComplex(stack, CAPACITY, getValidFluids());
  }

  @Override
  public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
    if (isInCreativeTab(tab)) {
      items.add(new ItemStack(this));
      for (Fluid fluid : getValidFluids()) {
        ItemStack stack = new ItemStack(this);
        IFluidHandlerItem cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (cap != null) {
          cap.fill(new FluidStack(fluid, CAPACITY), true);
        }
        items.add(stack);
      }
    }
  }

  //no need for @Override itemRightClick to fill or place since fluidhandler interactions and placement are handled before it is called

  /**
   * @param stack the item stack. This can assume that it is of the right item type and do casts without checking
   * @return the metal of the stack
   */
  @Nullable
  @Override
  public Metal getMetal(ItemStack stack) {
    return ((BlockMetalLamp) (super.block)).getMetal();
  }

  /**
   * @param stack The item stack
   * @return the amount of liquid metal that this item will create (in TFC units or mB: 1 unit = 1 mB)
   */
  @Override
  public int getSmeltAmount(ItemStack stack) {
    return 144;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addMetalInfo(ItemStack stack, List<String> text) // shamelessly co-opted to show liquid too
  {
    IFluidHandler fluidCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
    boolean spacer = false;
    if (fluidCap != null) {
      FluidStack fluidStack = fluidCap.drain(CAPACITY, false);
      if (fluidStack != null) {
        spacer = true;
        text.add("");
        String fluidName = fluidStack.getLocalizedName();
        text.add(I18n.format("tfc.tooltip.barrel_fluid", fluidStack.amount, fluidName));
      }
    }
    Metal metal = getMetal(stack);
    if (metal != null) {
      if (!spacer) {
        text.add("");
      }
      text.add(I18n.format("tfc.tooltip.metal", I18n.format(Helpers.getTypeName(metal))));
      text.add(I18n.format("tfc.tooltip.units", getSmeltAmount(stack)));
      text.add(I18n.format(Helpers.getEnumName(metal.getTier())));
    }
  }
}
