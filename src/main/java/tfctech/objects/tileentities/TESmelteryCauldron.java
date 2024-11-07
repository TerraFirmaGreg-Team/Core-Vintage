package tfctech.objects.tileentities;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.fluids.capability.FluidHandlerSided;
import net.dries007.tfc.objects.fluids.capability.FluidTankCallback;
import net.dries007.tfc.objects.fluids.capability.IFluidHandlerSidedCallback;
import net.dries007.tfc.objects.fluids.capability.IFluidTankCallback;
import net.dries007.tfc.objects.te.ITileFields;
import net.dries007.tfc.objects.te.TEInventory;
import net.dries007.tfc.util.Helpers;
import tfctech.api.recipes.SmelteryRecipe;

import static tfctech.objects.blocks.devices.BlockSmelteryCauldron.LIT;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
public class TESmelteryCauldron extends TEInventory implements ITickable, IFluidHandlerSidedCallback, IFluidTankCallback, ITileFields
{
    public static final int FLUID_CAPACITY = 4000;
    private final FluidTank tank = new FluidTankCallback(this, 0, FLUID_CAPACITY);
    private float temp;
    private int reload;

    public TESmelteryCauldron()
    {
        super(8);
        reload = 0;
    }

    @Override
    public void update()
    {
        if (!world.isRemote)
        {
            if (++reload >= 10)
            {
                reload = 0;
                TESmelteryFirebox firebox = Helpers.getTE(world, pos.down(), TESmelteryFirebox.class);
                if (firebox != null)
                {
                    temp = firebox.getTemperature();
                }
                else
                {
                    temp = 0;
                }
                IBlockState state = world.getBlockState(pos);
                if (temp > 0 && !state.getValue(LIT))
                {
                    world.setBlockState(pos, state.withProperty(LIT, true));
                }
                else if (temp <= 0 && state.getValue(LIT))
                {
                    world.setBlockState(pos, state.withProperty(LIT, false));
                }
                List<ItemStack> input = new ArrayList<>();
                for (int i = 0; i < 8; i++)
                {
                    input.add(inventory.extractItem(i, 64, true));
                }
                // Do Smeltery Recipes
                SmelteryRecipe recipe = SmelteryRecipe.get(input.toArray(new ItemStack[0]));
                if (recipe != null)
                {
                    FluidStack output = recipe.getOutput();
                    if (recipe.getMeltTemp() <= temp && tank.fill(output, false) >= output.amount)
                    {
                        recipe.consumeInputs(input);
                        for (int i = 0; i < 8; i++)
                        {
                            inventory.setStackInSlot(i, input.get(i));
                        }
                        tank.fillInternal(output, true);
                        temp -= ConfigTFC.Devices.TEMPERATURE.heatingModifier * 150;
                        if (firebox != null)
                        {
                            firebox.setTemperature(temp);
                        }
                    }
                }
            }
        }
    }

    /*
     * For visuals only
     */
    public FluidStack getFluid()
    {
        return tank.drain(FLUID_CAPACITY, false);
    }

    public float getTemp()
    {
        return temp;
    }

    public boolean isSolidified()
    {
        FluidStack fluid = getFluid();
        return fluid != null && temp + 273 < fluid.getFluid().getTemperature();
    }

    @Override
    public boolean canFill(FluidStack fluidStack, EnumFacing enumFacing)
    {
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing enumFacing)
    {
        return !isSolidified();
    }

    @Override
    public void setAndUpdateFluidTank(int i)
    {
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        tank.readFromNBT(nbt.getCompoundTag("tank"));
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            //noinspection unchecked
            return (T) new FluidHandlerSided(this, tank, facing);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public int getFieldCount()
    {
        return 1;
    }

    @Override
    public void setField(int index, int value)
    {
        temp = value;
    }

    @Override
    public int getField(int index)
    {
        return (int) temp;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack)
    {
        return true;
    }
}
