package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.features.ambiental.modifiers.ModifierBase;
import su.terrafirmagreg.api.features.ambiental.modifiers.ModifierTile;
import su.terrafirmagreg.api.features.ambiental.provider.ITemperatureTileProvider;
import su.terrafirmagreg.api.spi.gui.provider.IContainerProvider;
import su.terrafirmagreg.modules.device.client.audio.IMachineSoundEffect;
import su.terrafirmagreg.modules.device.client.gui.GuiElectricForge;
import su.terrafirmagreg.modules.device.init.SoundDevice;
import su.terrafirmagreg.modules.device.objects.blocks.BlockElectricForge;
import su.terrafirmagreg.modules.device.objects.containers.ContainerElectricForge;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.capability.GregtechCapabilities;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.objects.te.ITileFields;
import net.dries007.tfc.objects.te.TEInventory;
import tfctech.TFCTech;
import tfctech.TechConfig;
import tfctech.objects.storage.MachineEnergyContainer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

import static su.terrafirmagreg.api.data.Blockstates.LIT;

@SuppressWarnings("WeakerAccess")
//@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2")
public class TileElectricForge extends TEInventory
        implements ITickable, ITileFields, IMachineSoundEffect, ITemperatureTileProvider, IContainerProvider<ContainerElectricForge, GuiElectricForge> {

    public static final int SLOT_INPUT_MIN = 0;
    public static final int SLOT_INPUT_MAX = 8;
    public static final int SLOT_EXTRA_MIN = 9;
    public static final int SLOT_EXTRA_MAX = 11;
    private final HeatRecipe[] cachedRecipes = new HeatRecipe[9];
    private final MachineEnergyContainer energyContainer;
    private float targetTemperature = 0.0F;
    private int litTime = 0;
    private boolean soundPlay = false;

    private boolean addedToIc2Network = false;

    public TileElectricForge() {
        super(12);

        this.energyContainer = new MachineEnergyContainer(TechConfig.DEVICES.electricForgeEnergyCapacity, TechConfig.DEVICES.electricForgeEnergyCapacity, 0);
        Arrays.fill(cachedRecipes, null);
    }

    public void addTargetTemperature(int value) {
        targetTemperature += value;
        if (targetTemperature > (float) TechConfig.DEVICES.electricForgeMaxTemperature)
            targetTemperature = (float) TechConfig.DEVICES.electricForgeMaxTemperature;
        if (targetTemperature < 0) targetTemperature = 0;
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

    @Override
    public void update() {
        if (world.isRemote) {
            IMachineSoundEffect.super.update();
            return;
        }
        IBlockState state = world.getBlockState(pos);
        int energyUsage = (int) ((float) TechConfig.DEVICES.electricForgeEnergyConsumption * targetTemperature / 100);
        if (energyUsage < 1) energyUsage = 1;
        for (int i = SLOT_INPUT_MIN; i <= SLOT_INPUT_MAX; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            IItemHeat cap = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
            float modifier = stack.getItem() instanceof IMetalItem ? ((IMetalItem) stack.getItem()).getSmeltAmount(stack) / 100.0F : 1.0F;
            if (cap != null) {
                // Update temperature of item
                float itemTemp = cap.getTemperature();
                int energy = (int) (energyUsage * modifier);
                if (targetTemperature > itemTemp && energyContainer.consumeEnergy(energy, false)) {
                    float heatSpeed = (float) TechConfig.DEVICES.electricForgeSpeed * 15.0F;
                    float temp = cap.getTemperature() + heatSpeed * cap.getHeatCapacity() * (float) ConfigTFC.Devices.TEMPERATURE.globalModifier;
                    cap.setTemperature(Math.min(temp, targetTemperature));
                    litTime = 15;
                }
                handleInputMelting(stack, i);
            }
        }
        if (--litTime <= 0) {
            litTime = 0;
            state = state.withProperty(LIT, false);
            world.setBlockState(pos, state, 2);
        } else {
            state = state.withProperty(LIT, true);
            world.setBlockState(pos, state, 2);
        }
    }

    @Override
    public void setAndUpdateSlots(int slot) {
        this.markDirty();
        updateCachedRecipes();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        targetTemperature = nbt.getFloat("targetTemperature");
        energyContainer.deserializeNBT(nbt.getCompoundTag("energyContainer"));
        super.readFromNBT(nbt);

        updateCachedRecipes();
    }

    @Override
    @NotNull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setFloat("targetTemperature", targetTemperature);
        nbt.setTag("energyContainer", energyContainer.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing == null || facing == EnumFacing.UP || facing == EnumFacing.DOWN || facing == world.getBlockState(pos)
                .getValue(BlockElectricForge.FACING)
                .getOpposite()) {
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
        if (facing == null || facing == EnumFacing.UP || facing == EnumFacing.DOWN || facing == world.getBlockState(pos)
                .getValue(BlockElectricForge.FACING)
                .getOpposite()) {
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
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        if (slot <= SLOT_INPUT_MAX) {
            return stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
        } else {
            return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null) &&
                    stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
        }
    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public void setField(int index, int value) {
        if (index == 0) {
            this.targetTemperature = (float) value;
        } else if (index == 1) {
            this.energyContainer.setEnergy(value);
        } else {
            TFCTech.getLog().warn("Invalid field ID {} in TEElectricForge#setField", index);
        }
    }

    @Override
    public int getField(int index) {
        if (index == 0) {
            return (int) this.targetTemperature;
        } else if (index == 1) {
            return this.energyContainer.getEnergyStored();
        } else {
            TFCTech.getLog().warn("Invalid field ID {} in TEElectricForge#setField", index);
            return 0;
        }
    }

    public int getEnergyCapacity() {
        return energyContainer.getMaxEnergyStored();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public SoundEvent getSoundEvent() {
        return SoundDevice.INDUCTION_WORK;
    }

    @Override
    public boolean shouldPlay() {
        if (!this.isInvalid()) {
            return false;
        }
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof BlockElectricForge && world.getBlockState(pos).getValue(LIT);
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

    //    @Optional.Method(modid = "ic2")
    //    @Override
    //    public boolean acceptsEnergyFrom(IEnergyEmitter iEnergyEmitter, EnumFacing facing) {
    //        return TechConfig.DEVICES.acceptIc2EU && (facing == EnumFacing.UP || facing == EnumFacing.DOWN || facing == world.getBlockState(pos)
    //                .getValue(BlockElectricForge.FACING)
    //                .getOpposite());
    //    }

    private void handleInputMelting(ItemStack stack, int index) {
        HeatRecipe recipe = cachedRecipes[index];
        IItemHeat cap = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);

        if (recipe != null && cap != null && recipe.isValidTemperature(cap.getTemperature())) {
            // Handle possible metal output
            FluidStack fluidStack = recipe.getOutputFluid(stack);
            float itemTemperature = cap.getTemperature();
            if (fluidStack != null) {
                // Loop through all input slots
                for (int i = SLOT_EXTRA_MIN; i <= SLOT_EXTRA_MAX; i++) {
                    // While the fluid is still waiting
                    if (fluidStack.amount <= 0) {
                        break;
                    }
                    // Try an output slot
                    ItemStack output = inventory.getStackInSlot(i);
                    // Fill the fluid
                    IFluidHandler fluidHandler = output.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                    if (fluidHandler != null) {
                        int amountFilled = fluidHandler.fill(fluidStack.copy(), true);
                        if (amountFilled > 0) {
                            fluidStack.amount -= amountFilled;

                            // If the fluid was filled, make sure to make it the same temperature
                            IItemHeat heatHandler = output.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
                            if (heatHandler != null) {
                                heatHandler.setTemperature(itemTemperature);
                            }
                        }
                    }
                }
            }

            // Handle possible item output
            inventory.setStackInSlot(index, recipe.getOutputStack(stack));
        }
    }

    private void updateCachedRecipes() {
        for (int i = SLOT_INPUT_MIN; i <= SLOT_INPUT_MAX; ++i) {
            this.cachedRecipes[i] = null;
            ItemStack inputStack = this.inventory.getStackInSlot(i);
            if (!inputStack.isEmpty()) {
                this.cachedRecipes[i] = HeatRecipe.get(inputStack);
            }
        }
    }

    @Override
    public ContainerElectricForge getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new ContainerElectricForge(inventoryPlayer, this);
    }

    @Override
    public GuiElectricForge getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
        return new GuiElectricForge(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
    }

    @Override
    public Optional<ModifierBase> getModifier(EntityPlayer player, TileEntity tile) {
        float temp = TileCrucible.FIELD_TEMPERATURE;
        float change = temp / 100f;
        float potency = temp / 350f;
        if (ModifierTile.hasProtection(player)) {
            change = change * 0.3F;
        }
        return ModifierBase.defined(this.blockType.getTranslationKey(), change, potency);
    }
}
