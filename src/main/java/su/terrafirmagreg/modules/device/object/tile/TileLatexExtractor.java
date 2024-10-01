package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTile;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.device.network.SCPacketLatex;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.TechItems;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.Nullable;

import static net.minecraftforge.fluids.Fluid.BUCKET_VOLUME;

public class TileLatexExtractor extends BaseTile implements ITickable {

  public static final int MAX_FLUID = BUCKET_VOLUME;

  private int flowTicks = -1, serverUpdate = 0; //-1 No cut, 0 stopped, >= 1 still flowing
  private int fluid = 0;
  private boolean pot = false;
  private boolean base = false;

  public TileLatexExtractor() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    flowTicks = nbt.getInteger("flowTicks");
    fluid = nbt.getInteger("fluid");
    pot = nbt.getBoolean("pot");
    base = nbt.getBoolean("base");
    super.readFromNBT(nbt);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setInteger("flowTicks", flowTicks);
    nbt.setInteger("fluid", fluid);
    nbt.setBoolean("pot", pot);
    nbt.setBoolean("base", base);
    return super.writeToNBT(nbt);
  }

  public int getFluidAmount() {
    return !hasFluid() ? 0 : Math.min(MAX_FLUID, fluid);
  }

  public boolean hasFluid() {
    return fluid > 0 && hasPot();
  }

  public boolean hasPot() {
    return pot;
  }

  /**
   * the cut state to determine blockstate
   *
   * @return 0 - Not Cut, 1 - Not flowing, 2 - Flowing
   */
  public int cutState() {
    if (flowTicks < 0) {
      return 0;
    } else {
      if (flowTicks > 0 && hasPot()) {
        return 2;
      } else {
        return 1;
      }
    }
  }

  @Nullable
  public IBlockState getBlockState() {
    if (!this.hasWorld()) {
      return null;
    }
    return world.getBlockState(pos);
  }

  public boolean makeCut() {
    if (flowTicks < 1 && hasPot() && hasBase()) {
      flowTicks =
        ICalendar.TICKS_IN_DAY / 2 + MathConstants.RNG.nextInt(ICalendar.TICKS_IN_DAY * 2);
      return true;
    }
    return false;
  }

  public boolean hasBase() {
    return base;
  }

  public void onBreakBlock() {
    if (hasPot()) {
      ItemStack pot = removePot();
      if (pot != ItemStack.EMPTY) {
        StackUtils.spawnItemStack(world, pos, pot);
      }
    }
    if (hasBase()) {
      ItemStack base = removeBase();
      if (base != ItemStack.EMPTY) {
        StackUtils.spawnItemStack(world, pos, base);
      }
    }
    StackUtils.spawnItemStack(world, pos, new ItemStack(TechItems.IRON_GROOVE));
  }

  public ItemStack removePot() {
    ItemStack stack = new ItemStack(TechItems.FLUID_BOWL);
    IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY,
                                            null);
    if (cap != null && hasFluid()) {
      cap.fill(new FluidStack(FluidsTFC.LATEX.get(), fluid), true);
    }
    if (flowTicks > -1) {
      flowTicks = 0;
      fluid = 0;
    }
    pot = false;
    return stack;
  }

  public ItemStack removeBase() {
    if (!hasPot()) {
      base = false;
      return new ItemStack(TechItems.IRON_BOWL_MOUNT);
    }
    return ItemStack.EMPTY;
  }

  public boolean addPot(ItemStack stack) {
    if (!hasPot() && hasBase() && isValidPot(stack)) {
      IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY,
                                              null);
      if (cap != null) {
        FluidStack fluidStack = cap.drain(MAX_FLUID, false);
        if (fluidStack != null) {
          if (fluidStack.getFluid() != FluidsTFC.LATEX.get()) {
            return false;
          } else {
            fluid = Math.min(fluidStack.amount, MAX_FLUID);
          }
        }
      }
      pot = true;
      return true;
    }
    return false;
  }

  public boolean isValidPot(ItemStack pot) {
    return pot.getItem() == TechItems.FLUID_BOWL;
  }

  public boolean addBase(ItemStack stack) {
    if (!hasBase() && isValidBase(stack)) {
      base = true;
      return true;
    }
    return false;
  }

  public boolean isValidBase(ItemStack base) {
    return base.getItem() == TechItems.IRON_BOWL_MOUNT;
  }

  @Override
  public void update() {
    if (!world.isRemote) {
      if (flowTicks > 0) {
        if (--flowTicks % 40 == 0) {
          fluid++;
          if (fluid >= MAX_FLUID) {
            fluid = MAX_FLUID;
            flowTicks = 0;
          }
        }
      }
      if (++serverUpdate % 40 == 0) {
        serverUpdate = 0;
        ModuleDevice.PACKET_SERVICE.sendToAllAround(new SCPacketLatex(this), world.provider.getDimension(), pos, 64);
      }
    }
  }

  /**
   * Update client TE for gui purposes only!
   */
  @SideOnly(Side.CLIENT)
  public void updateClient(int cutState, int fluid, boolean pot, boolean base) {
    this.flowTicks = cutState - 1;
    this.fluid = fluid;
    this.pot = pot;
    this.base = base;
  }
}
