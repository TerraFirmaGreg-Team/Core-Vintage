package tfctech.objects.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import gregtech.api.capability.GregtechCapabilities;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.objects.te.TEInventory;
import net.dries007.tfc.util.Helpers;
import tfctech.TFCTech;
import tfctech.TechConfig;
import tfctech.client.TechSounds;
import tfctech.network.PacketFridgeUpdate;
import tfctech.network.PacketTileEntityUpdate;
import tfctech.objects.blocks.devices.BlockFridge;
import tfctech.objects.storage.MachineEnergyContainer;
import tfctech.registry.TechFoodTraits;

import static tfctech.objects.blocks.devices.BlockFridge.UPPER;

@SuppressWarnings("WeakerAccess")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2")
public class TEFridge extends TEInventory implements ITickable, IEnergySink
{
    private static final float MAX_DEGREE = 90F;
    private static final float DOOR_SPEED = 6F;
    private final MachineEnergyContainer energyContainer;
    private float open = 0.0F;
    private float lastOpen = 0.0F;
    private int openingState = 0;
    private float efficiency = 0.0F;
    private int applyTrait = 0;
    private int serverUpdate;

    private boolean addedToIc2Network = false;
    private int mainBlock = 0; // 0 - not initialized, 1 = main block, -1 not main block

    public TEFridge()
    {
        super(8);
        energyContainer = new MachineEnergyContainer(TechConfig.DEVICES.fridgeEnergyCapacity, TechConfig.DEVICES.fridgeEnergyCapacity, 0);
    }

    public float getEfficiency()
    {
        return efficiency;
    }

    /**
     * Update client's info for waila tooltips
     */
    public void updateClient(float efficiency)
    {
        this.efficiency = efficiency;
    }

    public int getEnergyCapacity()
    {
        return energyContainer.getMaxEnergyStored();
    }

    public int getEnergyStored()
    {
        return energyContainer.getEnergyStored();
    }

    public boolean isMainBlock()
    {
        if (mainBlock == 0)
        {
            if (!hasWorld() || world.isAirBlock(pos))
            {
                return false;
            }
            if (world.getBlockState(pos).getValue(UPPER))
            {
                mainBlock = 1;
            }
            else
            {
                mainBlock = -1;
            }
        }
        return mainBlock == 1;
    }

    public ItemStack insertItem(int slot, ItemStack stack)
    {
        ItemStack output = inventory.insertItem(slot, stack, false);
        setAndUpdateSlots(slot);
        return output;
    }

    public ItemStack extractItem(int slot)
    {
        ItemStack stack = inventory.extractItem(slot, 64, false);
        IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
        if (cap != null)
        {
            CapabilityFood.removeTrait(cap, TechFoodTraits.FROZEN);
            CapabilityFood.removeTrait(cap, TechFoodTraits.COLD);
        }
        setAndUpdateSlots(slot);
        return stack;
    }

    @Override
    public void setAndUpdateSlots(int slot)
    {
        TFCTech.getNetwork().sendToAllTracking(new PacketTileEntityUpdate(this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
        super.setAndUpdateSlots(slot);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        open = nbt.getFloat("open");
        lastOpen = open;
        openingState = nbt.getInteger("openingState");
        efficiency = nbt.getFloat("efficiency");
        energyContainer.deserializeNBT(nbt.getCompoundTag("energyContainer"));
        super.readFromNBT(nbt);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setFloat("open", open);
        nbt.setInteger("openingState", openingState);
        nbt.setFloat("efficiency", efficiency);
        nbt.setTag("energyContainer", energyContainer.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (!isMainBlock())
        {
            TEFridge te = Helpers.getTE(world, pos.up(), TEFridge.class);
            if (te != null)
            {
                return te.hasCapability(capability, facing);
            }
            else
            {
                return false;
            }
        }
        if (facing == null || facing == this.getRotation().getOpposite())
        {
            if (TechConfig.DEVICES.acceptFE && capability == CapabilityEnergy.ENERGY)
            {
                return true;
            }
            else if (TechConfig.DEVICES.acceptGTCEEU && Loader.isModLoaded("gregtech") && capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER)
            {
                return true;
            }
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (!isMainBlock())
        {
            TEFridge te = Helpers.getTE(world, pos.up(), TEFridge.class);
            if (te != null)
            {
                return te.getCapability(capability, facing);
            }
            else
            {
                return null;
            }
        }
        if (facing == null || facing == this.getRotation().getOpposite())
        {
            if (TechConfig.DEVICES.acceptFE && capability == CapabilityEnergy.ENERGY)
            {
                return (T) this.energyContainer;
            }
            else if (TechConfig.DEVICES.acceptGTCEEU && Loader.isModLoaded("gregtech") && capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER)
            {
                return (T) this.energyContainer.getGTCEHandler();
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void onBreakBlock(World world, BlockPos pos, IBlockState state)
    {
        if (Loader.isModLoaded("ic2"))
        {
            ic2Unload();
            if (isMainBlock())
            {
                TEFridge child = Helpers.getTE(world, pos.down(), TEFridge.class);
                if (child != null)
                {
                    child.ic2Unload();
                }
            }
            else
            {
                TEFridge main = Helpers.getTE(world, pos.up(), TEFridge.class);
                if (main != null)
                {
                    main.ic2Unload();
                }
            }
        }
        super.onBreakBlock(world, pos, state);
    }

    @Override
    public void onChunkUnload()
    {
        super.onChunkUnload();
        if (Loader.isModLoaded("ic2"))
        {
            ic2Unload();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public double getDemandedEnergy()
    {
        if (!isMainBlock())
        {
            TEFridge te = Helpers.getTE(world, pos.up(), TEFridge.class);
            if (te != null && te.addedToIc2Network)
            {
                return Math.ceil(te.energyContainer.receiveEnergy(Integer.MAX_VALUE, true) / (double) TechConfig.DEVICES.ratioIc2);
            }
            return 0;
        }
        return Math.ceil(energyContainer.receiveEnergy(Integer.MAX_VALUE, true) / (double) TechConfig.DEVICES.ratioIc2);
    }

    @Override
    public int getSinkTier()
    {
        return TechConfig.DEVICES.ic2Voltage;
    }

    @Override
    public double injectEnergy(EnumFacing facing, double amount, double voltage)
    {
        if (!isMainBlock())
        {
            TEFridge te = Helpers.getTE(world, pos.up(), TEFridge.class);
            if (te != null && te.addedToIc2Network)
            {
                te.energyContainer.receiveEnergy((int) Math.ceil(amount) * TechConfig.DEVICES.ratioIc2, false);
            }
            return 0;
        }
        energyContainer.receiveEnergy((int) Math.ceil(amount) * TechConfig.DEVICES.ratioIc2, false);
        return 0;
    }

    @Optional.Method(modid = "ic2")
    public void ic2Unload()
    {
        if (!world.isRemote && addedToIc2Network)
        {
            MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileUnloadEvent(this));
            addedToIc2Network = false;
        }
    }

    @Optional.Method(modid = "ic2")
    public void ic2Load()
    {
        if (!world.isRemote && TechConfig.DEVICES.acceptIc2EU && !addedToIc2Network)
        {
            MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileLoadEvent(this));
            addedToIc2Network = true;
        }
    }

    @Optional.Method(modid = "ic2")
    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, EnumFacing facing)
    {
        if (!isMainBlock())
        {
            TEFridge te = Helpers.getTE(world, pos.up(), TEFridge.class);
            if (te == null || !te.addedToIc2Network)
            {
                return false;
            }
        }
        return TechConfig.DEVICES.acceptIc2EU && facing == this.getRotation().getOpposite();
    }

    public ItemStack getSlot(int slot)
    {
        return inventory.extractItem(slot, 64, true);
    }

    public float getOpen()
    {
        return open;
    }

    public float getLastOpen()
    {
        return lastOpen;
    }

    public boolean isOpen()
    {
        return open >= MAX_DEGREE;
    }

    public boolean hasStack(int slot)
    {
        return inventory.extractItem(slot, 64, true) != ItemStack.EMPTY;
    }

    public EnumFacing getRotation()
    {
        return world.getBlockState(pos).getValue(BlockHorizontal.FACING);
    }

    public boolean isAnimating()
    {
        return openingState != 0;
    }

    @Nullable
    public Vec3d[] getItems()
    {
        if (!hasWorld())
        {
            return null;
        }
        return BlockFridge.getItems(this.getRotation());
    }

    public boolean setOpening(boolean value)
    {
        if (openingState != 0) return false; //Already opening/closing
        if (!world.isRemote)
        {
            if (value)
            {
                world.playSound(null, pos, TechSounds.FRIDGE_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                open = 0F;
                openingState = 1;
            }
            else
            {
                open = MAX_DEGREE;
                openingState = -1;
            }
            setAndUpdateSlots(0);
        }
        return true;
    }

    @Override
    public void update()
    {
        if (Loader.isModLoaded("ic2"))
        {
            ic2Load();
        }
        if (!isMainBlock()) return;
        lastOpen = open;
        if (openingState == 1)
        {
            //opening
            open += DOOR_SPEED;
            if (open >= MAX_DEGREE)
            {
                open = MAX_DEGREE;
                openingState = 0;
            }
        }
        if (openingState == -1)
        {
            //closing
            open -= DOOR_SPEED;
            if (open <= 0F)
            {
                open = 0F;
                openingState = 0;
                world.playSound(null, pos, TechSounds.FRIDGE_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
        if (!world.isRemote)
        {
            int consumption = (int) Math.max(1.0D, TechConfig.DEVICES.fridgeEnergyConsumption * 10.0D);
            if (efficiency >= 75.0F)
            {
                consumption = (int) Math.max(1.0D, consumption / 4.0D);
            }
            if (this.isOpen() || !energyContainer.consumeEnergy(consumption, false))
            {
                efficiency -= (100.0F / (6000.0F / TechConfig.DEVICES.fridgeLoseEfficiency)); //5 Minutes to 0 default
                if (efficiency <= 0) efficiency = 0;
            }
            else
            {
                efficiency += (100.0F / (36000.0F / TechConfig.DEVICES.fridgeEfficiency)); //30 Minutes to 100 default
                if (efficiency >= 100) efficiency = 100;
            }
            if (++applyTrait >= 100)
            {
                applyTrait = 0;
                if (efficiency >= 80.0F)
                {
                    for (int i = 0; i < inventory.getSlots(); i++)
                    {
                        IFood cap = inventory.getStackInSlot(i).getCapability(CapabilityFood.CAPABILITY, null);
                        if (cap != null)
                        {
                            CapabilityFood.removeTrait(cap, TechFoodTraits.COLD);
                            CapabilityFood.applyTrait(cap, TechFoodTraits.FROZEN);
                        }
                    }
                }
                else if (efficiency >= 30.0F)
                {
                    for (int i = 0; i < inventory.getSlots(); i++)
                    {
                        IFood cap = inventory.getStackInSlot(i).getCapability(CapabilityFood.CAPABILITY, null);
                        if (cap != null)
                        {
                            CapabilityFood.removeTrait(cap, TechFoodTraits.FROZEN);
                            CapabilityFood.applyTrait(cap, TechFoodTraits.COLD);
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < inventory.getSlots(); i++)
                    {
                        IFood cap = inventory.getStackInSlot(i).getCapability(CapabilityFood.CAPABILITY, null);
                        if (cap != null)
                        {
                            CapabilityFood.removeTrait(cap, TechFoodTraits.FROZEN);
                            CapabilityFood.removeTrait(cap, TechFoodTraits.COLD);
                        }
                    }
                }
            }
            if (++serverUpdate % 40 == 0)
            {
                serverUpdate = 0;
                TFCTech.getNetwork().sendToAllTracking(new PacketFridgeUpdate(pos, efficiency), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
            }
        }
    }
}
