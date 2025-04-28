package su.terrafirmagreg.modules.device.object.tile;

import net.dries007.tfc.api.recipes.SmelteryRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.base.object.tile.api.ITileFields;
import su.terrafirmagreg.api.base.object.tile.spi.BaseTileTickableInventory;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderContainer;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.fluid.CapabilityProviderFluid;
import su.terrafirmagreg.modules.core.capabilities.fluid.IFluidHandlerSidedCallback;
import su.terrafirmagreg.modules.core.capabilities.fluid.IFluidTankCallback;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierTile;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderTile;
import su.terrafirmagreg.modules.device.client.gui.GuiSmelteryCauldron;
import su.terrafirmagreg.modules.device.object.container.ContainerSmelteryCauldron;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;

public class TileSmelteryCauldron extends BaseTileTickableInventory
        implements IFluidHandlerSidedCallback, IFluidTankCallback, ITileFields, IAmbientalProviderTile,
        IProviderContainer<ContainerSmelteryCauldron, GuiSmelteryCauldron> {

    public static final int FLUID_CAPACITY = 4000;
    private final FluidTank tank = new CapabilityProviderFluid.Callback(this, 0, FLUID_CAPACITY);
    private float temp = 0;
    private int reload;

    public TileSmelteryCauldron() {
        super(8);
        reload = 0;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (++reload >= 10) {
                reload = 0;
                TileUtils.getTile(world, pos.down(), TileSmelteryFirebox.class).ifPresent(tile -> {
                    temp = tile.getTemperature();
                    IBlockState state = world.getBlockState(pos);
                    if (temp > 0 && !state.getValue(LIT)) {
                        world.setBlockState(pos, state.withProperty(LIT, true));
                    } else if (temp <= 0 && state.getValue(LIT)) {
                        world.setBlockState(pos, state.withProperty(LIT, false));
                    }
                    List<ItemStack> input = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        input.add(inventory.extractItem(i, 64, true));
                    }
                    // Do Smeltery Recipes
                    SmelteryRecipe recipe = SmelteryRecipe.get(input.toArray(new ItemStack[0]));
                    if (recipe != null) {
                        FluidStack output = recipe.getOutput();
                        if (recipe.getMeltTemp() <= temp && tank.fill(output, false) >= output.amount) {
                            recipe.consumeInputs(input);
                            for (int i = 0; i < 8; i++) {
                                inventory.setStackInSlot(i, input.get(i));
                            }
                            tank.fillInternal(output, true);
                            temp -= (float) (ConfigCore.MISC.HEAT.heatingModifier * 150);
                            tile.setTemperature(temp);
                        }
                    }
                });

            }
        }
    }

    public float getTemp() {
        return temp;
    }

    @Override
    public boolean canFill(FluidStack fluidStack, EnumFacing enumFacing) {
        return false;
    }

    @Override
    public boolean canDrain(EnumFacing enumFacing) {
        return !isSolidified();
    }

    public boolean isSolidified() {
        FluidStack fluid = getFluid();
        return fluid != null && temp + 273 < fluid.getFluid().getTemperature();
    }

    /*
     * For visuals only
     */
    public FluidStack getFluid() {
        return tank.drain(FLUID_CAPACITY, false);
    }

    @Override
    public void setAndUpdateFluidTank(int i) {
        BlockUtils.notifyBlockUpdate(world, pos);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tank.readFromNBT(nbt.getCompoundTag("tank"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) || super.hasCapability(
                capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            //noinspection unchecked
            return (T) new CapabilityProviderFluid.Sided(this, tank, facing);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public int getFieldCount() {
        return 1;
    }

    @Override
    public void setField(int index, int value) {
        temp = value;
    }

    @Override
    public int getField(int index) {
        return (int) temp;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public Optional<ModifierTile> getModifier(EntityPlayer player, TileEntity tile) {
        float change = temp / 120f;
        float potency = temp / 370f;
        if (ModifierTile.hasProtection(player)) {
            change = change * 0.3f; //change = 1.0F;
        }
        return ModifierTile.defined(this.getBlockType().getRegistryName().getPath(), change, potency);
    }

    @Override
    public ContainerSmelteryCauldron getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new ContainerSmelteryCauldron(inventoryPlayer, this);
    }

    @Override
    public GuiSmelteryCauldron getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new GuiSmelteryCauldron(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
    }


}
