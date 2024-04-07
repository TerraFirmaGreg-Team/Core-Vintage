package tfctech.objects.tileentities;

import su.terrafirmagreg.modules.device.objects.tiles.TECrucible;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import gregtech.api.capability.GregtechCapabilities;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import tfctech.TFCTech;
import tfctech.TechConfig;
import tfctech.client.TechSounds;
import tfctech.client.audio.IMachineSoundEffect;
import tfctech.objects.blocks.devices.BlockInductionCrucible;
import tfctech.objects.storage.MachineEnergyContainer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.util.PropertyUtils.HORIZONTAL;
import static su.terrafirmagreg.api.util.PropertyUtils.LIT;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2")
public class TEInductionCrucible extends TECrucible implements IMachineSoundEffect, IEnergySink {

    private final MachineEnergyContainer energyContainer;
    private int litTime = 0; //Client "effects" only

    private boolean addedToIc2Network = false;
    private boolean soundPlay = false;

    public TEInductionCrucible() {
        super();
        energyContainer = new MachineEnergyContainer(TechConfig.DEVICES.inductionCrucibleEnergyCapacity,
                TechConfig.DEVICES.inductionCrucibleEnergyCapacity, 0);
    }

    @Optional.Method(modid = "ic2")
    @Override
    public void invalidate() {
        super.invalidate();
        if (!world.isRemote && addedToIc2Network) {
            MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileUnloadEvent(this));
            addedToIc2Network = false;
        }
    }

    @Optional.Method(modid = "ic2")
    @Override
    public void validate() {
        super.validate();
        if (!world.isRemote && TechConfig.DEVICES.acceptIc2EU && !addedToIc2Network) {
            MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileLoadEvent(this));
            addedToIc2Network = true;
        }
    }

    @Override
    public double getDemandedEnergy() {
        return Math.ceil(energyContainer.receiveEnergy(Integer.MAX_VALUE, true) / (double) TechConfig.DEVICES.ratioIc2);
    }

    @Override
    public int getSinkTier() {
        return TechConfig.DEVICES.ic2Voltage;
    }

    @Override
    public double injectEnergy(EnumFacing facing, double amount, double voltage) {
        energyContainer.receiveEnergy((int) Math.ceil(amount) * TechConfig.DEVICES.ratioIc2, false);
        return 0;
    }

    @Optional.Method(modid = "ic2")
    @Override
    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, EnumFacing facing) {
        return TechConfig.DEVICES.acceptIc2EU && facing == world.getBlockState(pos).getValue(HORIZONTAL);
    }

    public int getEnergyCapacity() {
        return energyContainer.getMaxEnergyStored();
    }

    @Override
    public void update() {
        super.update();
        if (world.isRemote) {
            IMachineSoundEffect.super.update();
            return;
        }
        IBlockState state = world.getBlockState(pos);
        boolean isLit = state.getValue(LIT);
        int energyUsage = TechConfig.DEVICES.inductionCrucibleEnergyConsumption;
        boolean acceptHeat = this.getAlloy().removeAlloy(1, true) > 0;
        if (!acceptHeat) {
            for (int i = SLOT_INPUT_START; i <= SLOT_INPUT_END; i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                IItemHeat cap = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
                if (cap != null) {
                    acceptHeat = true;
                    break;
                }
            }
        }
        if (acceptHeat && energyContainer.consumeEnergy(energyUsage, false)) {
            this.acceptHeat(TechConfig.DEVICES.inductionCrucibleTargetTemperature);
            litTime = 15;
            if (!isLit) {
                state = state.withProperty(LIT, true);
                world.setBlockState(pos, state, 2);
                isLit = true;
            }
        }
        if (litTime > 0 && --litTime <= 0 && isLit) {
            state = state.withProperty(LIT, false);
            world.setBlockState(pos, state, 2);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        energyContainer.deserializeNBT(nbt.getCompoundTag("energyContainer"));
        super.readFromNBT(nbt);
    }

    @Override
    @NotNull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("energyContainer", energyContainer.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing == null || facing == world.getBlockState(pos).getValue(HORIZONTAL)) {
            if (TechConfig.DEVICES.acceptFE && capability == CapabilityEnergy.ENERGY) {
                return true;
            } else if (TechConfig.DEVICES.acceptGTCEEU && Loader.isModLoaded("gregtech") &&
                    capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) {
                return true;
            }
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing == null || facing == world.getBlockState(pos).getValue(HORIZONTAL)) {
            if (TechConfig.DEVICES.acceptFE && capability == CapabilityEnergy.ENERGY) {
                return (T) this.energyContainer;
            } else if (TechConfig.DEVICES.acceptGTCEEU && Loader.isModLoaded("gregtech") &&
                    capability == GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER) {
                return (T) this.energyContainer.getGTCEHandler();
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public void setField(int index, int value) {
        if (index == 0) {
            super.setField(0, value);
        } else if (index == 1) {
            this.energyContainer.setEnergy(value);
        } else {
            TFCTech.getLog().warn("Invalid field ID {} in TEElectricForge#setField", index);
        }
    }

    @Override
    public int getField(int index) {
        if (index == 0) {
            return super.getField(0);
        } else if (index == 1) {
            return this.energyContainer.getEnergyStored();
        } else {
            TFCTech.getLog().warn("Invalid field ID {} in TEElectricForge#setField", index);
            return 0;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public SoundEvent getSoundEvent() {
        return TechSounds.INDUCTION_WORK;
    }

    @Override
    public boolean shouldPlay() {
        if (!this.isInvalid()) {
            return false;
        }
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof BlockInductionCrucible && world.getBlockState(pos)
                .getValue(LIT);
    }

    @Override
    public boolean isPlaying() {
        return soundPlay;
    }

    @Override
    public void setPlaying(boolean value) {
        soundPlay = value;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockPos getSoundPos() {
        return this.getPos();
    }

    public int getEnergyStored() {
        return energyContainer.getEnergyStored();
    }
}
