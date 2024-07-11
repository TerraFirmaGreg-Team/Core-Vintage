package lyeoj.tfcthings.items;

import su.terrafirmagreg.api.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.api.capabilities.heat.ProviderHeat;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.capability.IMoldHandler;


import su.terrafirmagreg.api.capabilities.heat.spi.Heat;


import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public class ItemTFCThingsMold extends ItemPottery {

    private static final Map<String, ItemTFCThingsMold> MAP = new HashMap<>();
    private final String toolName;

    public ItemTFCThingsMold(String toolName) {
        this.toolName = toolName;
        if (MAP.put(toolName, this) != null) throw new IllegalStateException("There can only be one.");
        setCreativeTab(CreativeTabsTFC.CT_POTTERY);
        setRegistryName("mold/" + toolName);
        setTranslationKey("mold_" + toolName);
    }

    public static ItemTFCThingsMold get(String toolName) {
        return MAP.get(toolName);
    }

    public String getToolName() {
        return toolName;
    }

    @Override
    @NotNull
    public String getItemStackDisplayName(@NotNull ItemStack stack) {
        IFluidHandler capFluidHandler = stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
        if (capFluidHandler instanceof IMoldHandler) {
            Metal metal = ((IMoldHandler) capFluidHandler).getMetal();
            if (metal != null) {
                //noinspection ConstantConditions
                String metalName = (new TextComponentTranslation("tfc.types.metal." + metal.getRegistryName()
                        .getPath()
                        .toLowerCase())).getFormattedText();
                return (new TextComponentTranslation("item.tfcthings.mold." + toolName.toLowerCase() + "." + metal.getRegistryName()
                        .getPath(), metalName)).getFormattedText();
            }
        }
        return super.getItemStackDisplayName(stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FilledMoldCapability(nbt);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        IMoldHandler moldHandler = (IMoldHandler) stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
        if (moldHandler != null && moldHandler.getMetal() != null) {
            return 1;
        }
        return super.getItemStackLimit(stack);
    }

    // Extends ItemHeatHandler for ease of use

    public class FilledMoldCapability extends ProviderHeat implements ICapabilityProvider, IMoldHandler {

        private final FluidTank tank;
        private IFluidTankProperties[] fluidTankProperties;

        public FilledMoldCapability(@Nullable NBTTagCompound nbt) {
            tank = new FluidTank(100);

            if (nbt != null)
                deserializeNBT(nbt);
        }

        @Nullable
        @Override
        public Metal getMetal() {
            return tank.getFluid() != null ? FluidsTFC.getMetalFromFluid(tank.getFluid().getFluid()) : null;
        }

        @Override
        public int getAmount() {
            return tank.getFluidAmount();
        }

        @Override
        public IFluidTankProperties[] getTankProperties() {
            if (fluidTankProperties == null) {
                fluidTankProperties = new IFluidTankProperties[] { new FluidTankPropertiesWrapper(tank) };
            }
            return fluidTankProperties;
        }

        public int fill(FluidStack resource, boolean doFill) {
            if (resource != null) {
                Metal metal = FluidsTFC.getMetalFromFluid(resource.getFluid());
                if (metal != null && Metal.ItemType.PROPICK_HEAD.hasMold(metal)) {
                    int fillAmount = this.tank.fill(resource, doFill);
                    if (fillAmount == this.tank.getFluidAmount()) {
                        this.updateFluidData();
                    }

                    return fillAmount;
                }
            }

            return 0;
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            return getTemperature() >= meltTemp ? tank.drain(resource, doDrain) : null;
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (getTemperature() > meltTemp) {
                FluidStack stack = tank.drain(maxDrain, doDrain);
                if (tank.getFluidAmount() == 0) {
                    updateFluidData();
                }
                return stack;
            }
            return null;
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void addHeatInfo(@NotNull ItemStack stack, @NotNull List<String> text) {
            Metal metal = getMetal();
            if (metal != null) {
                String desc =
                        TextFormatting.DARK_GREEN + I18n.format(Helpers.getTypeName(metal)) + ": " + I18n.format("tfc.tooltip.units", getAmount());
                if (isMolten()) {
                    desc += I18n.format("tfc.tooltip.liquid");
                }
                text.add(desc);
            }
            IMoldHandler.super.addHeatInfo(stack, text);
        }

        @Override
        public float getHeatCapacity() {
            return heatCapacity;
        }

        @Override
        public float getMeltTemp() {
            return meltTemp;
        }

        @Override
        public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == FLUID_HANDLER_CAPABILITY
                    || capability == CapabilityHeat.CAPABILITY;
        }

        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
            return hasCapability(capability, facing) ? (T) this : null;
        }

        @Override
        @NotNull
        public NBTTagCompound serializeNBT() {
            NBTTagCompound nbt = new NBTTagCompound();
            float temp = getTemperature();
            nbt.setFloat("heat", temp);
            if (temp <= 0) {
                nbt.setLong("ticks", -1);
            } else {
                nbt.setLong("ticks", Calendar.PLAYER_TIME.getTicks());
            }
            return tank.writeToNBT(nbt);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            if (nbt != null) {
                temperature = nbt.getFloat("heat");
                lastUpdateTick = nbt.getLong("ticks");
                tank.readFromNBT(nbt);
            }
            updateFluidData();
        }

        private void updateFluidData() {
            updateFluidData(tank.getFluid());
        }

        @SuppressWarnings("ConstantConditions")
        private void updateFluidData(FluidStack fluid) {
            meltTemp = Heat.maxVisibleTemperature();
            heatCapacity = 1;
            if (fluid != null) {
                Metal metal = FluidsTFC.getMetalFromFluid(fluid.getFluid());
                if (metal != null) {
                    meltTemp = metal.getMeltTemp();
                    heatCapacity = metal.getSpecificHeat();
                }
            }
        }
    }

}
