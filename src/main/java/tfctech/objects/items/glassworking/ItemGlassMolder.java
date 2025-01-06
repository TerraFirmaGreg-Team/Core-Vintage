package tfctech.objects.items.glassworking;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.spi.Heat;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Sets;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.fluids.capability.FluidWhitelistHandler;
import tfctech.objects.items.ItemMiscTech;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemGlassMolder extends ItemMiscTech {

  public static final int BLOWPIPE_TANK = 250;
  public static final int PANE_TANK = 375;
  public static final int BLOCK_TANK = 1000;

  private final int capacity;

  public ItemGlassMolder(int capacity) {
    super(Size.LARGE, Weight.LIGHT);
    setMaxStackSize(1);
    this.capacity = capacity;
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new GlassMolderCapability(stack, capacity, nbt);
  }

  public static class GlassMolderCapability extends CapabilityProviderHeat implements ICapabilityProvider, IFluidHandlerItem {

    private final FluidWhitelistHandler tank;
    private final int capacity;

    GlassMolderCapability(ItemStack stack, int capacity, @Nullable NBTTagCompound nbt) {
      this.capacity = capacity;
      this.heatCapacity = 1;
      this.meltTemp = Heat.maxVisibleTemperature();
      this.tank = new FluidWhitelistHandler(stack, capacity, Sets.newHashSet(FluidsCore.GLASS.get()));
      deserializeNBT(nbt);
    }

    public boolean canWork() {
      // Used for glass molding / blowpipe
      FluidStack fluidStack = getFluid();
      return fluidStack != null && getTemperature() + 273 >= fluidStack.getFluid().getTemperature();
    }

    public boolean isSolidified() {
      // Used for unmolding
      FluidStack fluidStack = getFluid();
      return fluidStack != null && getTemperature() + 273 < fluidStack.getFluid().getTemperature();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
      return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY
             || capability == CapabilityHeat.CAPABILITY;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
      return hasCapability(capability, facing) ? (T) this : null;
    }

    @Nonnull
    @Override
    public NBTTagCompound serializeNBT() {
      NBTTagCompound nbt = super.serializeNBT();
      FluidStack fluidStack = tank.drain(capacity, false);
      if (fluidStack != null) {
        nbt.setTag("tank", fluidStack.writeToNBT(new NBTTagCompound()));
      }
      return nbt;
    }

    @Override
    public void deserializeNBT(@Nullable NBTTagCompound nbt) {
      super.deserializeNBT(nbt);
      if (nbt != null) {
        tank.fill(FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("tank")), true);
      }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addHeatInfo(@Nonnull ItemStack stack, @Nonnull List<String> tooltip) {
      FluidStack fluid = tank.drain(capacity, false);
      if (fluid != null) {
        String fluidDesc = TextFormatting.DARK_GREEN + fluid.getLocalizedName() + TextFormatting.WHITE;
        if (isSolidified()) {
          fluidDesc += I18n.format(Mods.Names.TFC + ".tooltip.solid");
        } else if (canWork()) {
          fluidDesc += I18n.format(Mods.Names.TFC + ".tooltip.liquid");
        }
        tooltip.add(fluidDesc);
      }
      super.addHeatInfo(stack, tooltip);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
      return new FluidTankProperties[]{new FluidTankProperties(tank.drain(capacity, false), capacity)};
    }

    @Override
    public int fill(FluidStack fluidStack, boolean doFill) {
      if (fluidStack.amount < this.capacity) {
        return 0; // Like buckets, the tank capacity or nothing
      }
      int value = tank.fill(fluidStack, doFill);
      if (doFill && value > 0) {
        this.setTemperature(fluidStack.getFluid().getTemperature()); // giving 273 heat so player has time to craft.
      }
      return value;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack fluidStack, boolean doDrain) {
      return null; // Never drain
    }

    @Nullable
    @Override
    public FluidStack drain(int maxAmount, boolean doDrain) {
      return null; // Never drain
    }

    @Nullable
    public FluidStack getFluid() {
      return tank.drain(capacity, false);
    }

    @Nonnull
    @Override
    public ItemStack getContainer() {
      return this.tank.getContainer();
    }

    public void empty() {
      tank.drain(capacity, true);
    }
  }
}
