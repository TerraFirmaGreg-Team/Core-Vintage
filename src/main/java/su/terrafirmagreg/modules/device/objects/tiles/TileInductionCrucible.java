package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierBase;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierTile;
import su.terrafirmagreg.modules.core.features.ambiental.provider.ITemperatureTileProvider;
import su.terrafirmagreg.modules.device.client.audio.IMachineSoundEffect;
import su.terrafirmagreg.modules.device.client.gui.GuiCrucible;
import su.terrafirmagreg.modules.device.client.gui.GuiInductionCrucible;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.device.objects.blocks.BlockInductionCrucible;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.capability.GregtechCapabilities;
import tfctech.TFCTech;
import tfctech.TechConfig;
import tfctech.objects.storage.MachineEnergyContainer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static su.terrafirmagreg.api.data.Blockstates.HORIZONTAL;
import static su.terrafirmagreg.api.data.Blockstates.LIT;

//@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2")
public class TileInductionCrucible extends TileCrucible implements IMachineSoundEffect, ITemperatureTileProvider {

    private final MachineEnergyContainer energyContainer;
    private int litTime = 0; //Client "effects" only

    private final boolean addedToIc2Network = false;
    private boolean soundPlay = false;

    public TileInductionCrucible() {
        super();
        energyContainer = new MachineEnergyContainer(TechConfig.DEVICES.inductionCrucibleEnergyCapacity, TechConfig.DEVICES.inductionCrucibleEnergyCapacity, 0);
    }

    //    @Optional.Method(modid = "ic2")
    //    @Override
    //    public void invalidate() {
    //        super.invalidate();
    //        if (!world.isRemote && addedToIc2Network) {
    //            MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileUnloadEvent(this));
    //            addedToIc2Network = false;
    //        }
    //    }
    //
    //    @Optional.Method(modid = "ic2")
    //    @Override
    //    public void validate() {
    //        super.validate();
    //        if (!world.isRemote && TechConfig.DEVICES.acceptIc2EU && !addedToIc2Network) {
    //            MinecraftForge.EVENT_BUS.post(new ic2.api.energy.event.EnergyTileLoadEvent(this));
    //            addedToIc2Network = true;
    //        }
    //    }

    //    @Override
    //    public double getDemandedEnergy() {
    //        return Math.ceil(energyContainer.receiveEnergy(Integer.MAX_VALUE, true) / (double) TechConfig.DEVICES.ratioIc2);
    //    }
    //
    //    @Override
    //    public int getSinkTier() {
    //        return TechConfig.DEVICES.ic2Voltage;
    //    }
    //
    //    @Override
    //    public double injectEnergy(EnumFacing facing, double amount, double voltage) {
    //        energyContainer.receiveEnergy((int) Math.ceil(amount) * TechConfig.DEVICES.ratioIc2, false);
    //        return 0;
    //    }
    //
    //    @Optional.Method(modid = "ic2")
    //    @Override
    //    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, EnumFacing facing) {
    //        return TechConfig.DEVICES.acceptIc2EU && facing == world.getBlockState(pos).getValue(HORIZONTAL);
    //    }

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
                var cap = CapabilityHeat.get(stack);
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
        return SoundsDevice.INDUCTION_WORK;
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

    @Override
    public GuiCrucible getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new GuiInductionCrucible(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
    }

    @Override
    public Optional<ModifierBase> getModifier(EntityPlayer player, TileEntity tile) {
        float temp = FIELD_TEMPERATURE;
        float change = temp / 100f;
        float potency = temp / 350f;
        if (ModifierTile.hasProtection(player)) {
            change = change * 0.3F;
        }
        return ModifierBase.defined(this.getBlockType().getRegistryName().getPath(), change, potency);
    }
}
