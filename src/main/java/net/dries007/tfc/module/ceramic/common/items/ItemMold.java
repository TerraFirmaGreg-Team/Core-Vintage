package net.dries007.tfc.module.ceramic.common.items;

import gregtech.api.fluids.MetaFluids;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.objects.container.CapabilityContainerListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

@ParametersAreNonnullByDefault
public class ItemMold extends ItemPottery {

    public final OrePrefix orePrefix;

    public ItemMold(OrePrefix type) {
        this.orePrefix = type;
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        return
                new TextComponentTranslation(
                        "item.tfc.ceramics.fired.mold.name",
                        new TextComponentTranslation("item.material.oreprefix." + orePrefix.name).getFormattedText().replaceFirst(" ", "")
                ).getFormattedText();
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        return CapabilityContainerListener.readShareTag(stack);
    }

    @Override
    public void readNBTShareTag(ItemStack stack, @Nullable NBTTagCompound nbt) {
        CapabilityContainerListener.applyShareTag(stack, nbt);
    }

    public OrePrefix getOrePrefix() {
        return orePrefix;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FilledMoldCapability(nbt);
    }

    @Override
    public boolean canStack(ItemStack stack) {
        IMoldHandler moldHandler = (IMoldHandler) stack.getCapability(FLUID_HANDLER_CAPABILITY, null);
        return moldHandler == null || moldHandler.getMaterial() == null;
    }

    // Extends ItemHeatHandler for ease of use
    private class FilledMoldCapability extends ItemHeatHandler implements ICapabilityProvider, IMoldHandler {
        private final FluidTank tank;
        private IFluidTankProperties[] fluidTankProperties;

        FilledMoldCapability(@Nullable NBTTagCompound nbt) {
            tank = new FluidTank(Helpers.getOrePrefixMaterialAmount(orePrefix));

            if (nbt != null) {
                deserializeNBT(nbt);
            }
        }


        @Nullable
        @Override
        public Material getMaterial() {
            return tank.getFluid() != null ? MetaFluids.getMaterialFromFluid(tank.getFluid().getFluid()) : null;
        }

        @Override
        public int getAmount() {
            return tank.getFluidAmount();
        }

        @Override
        public IFluidTankProperties[] getTankProperties() {
            if (fluidTankProperties == null) {
                fluidTankProperties = new IFluidTankProperties[]{new FluidTankPropertiesWrapper(tank)};
            }
            return fluidTankProperties;
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            var material = MetaFluids.getMaterialFromFluid(resource.getFluid());
            //noinspection ConstantConditions
            if (material != null) //  && orePrefix.hasMold(metal)
            {
                int fillAmount = tank.fill(resource, doFill);
                if (fillAmount == tank.getFluidAmount()) {
                    updateFluidData();
                }
                return fillAmount;
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
        public void addHeatInfo(@Nonnull ItemStack stack, @Nonnull List<String> text) {
            var material = getMaterial();

            IMoldHandler.super.addHeatInfo(stack, text);

            if (material != null) {
                text.add("");
                text.add(I18n.format("tfc.tooltip.containsmetal"));

                if (isMolten()) {
                    text.add(I18n.format("tfc.tooltip.state", I18n.format("tfc.tooltip.liquid")));
                } else {
                    text.add(I18n.format("tfc.tooltip.state", I18n.format("tfc.tooltip.solid")));
                }

                text.add(I18n.format("tfc.tooltip.metalname", material.getLocalizedName()));
                text.add(I18n.format("tfc.tooltip.units", getAmount()));
            }
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                    || capability == CapabilityItemHeat.ITEM_HEAT_CAPABILITY;
        }

        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return hasCapability(capability, facing) ? (T) this : null;
        }

        @Override
        @Nonnull
        public NBTTagCompound serializeNBT() {
            NBTTagCompound nbt = new NBTTagCompound();

            // Duplicated from ItemHeatHandler
            if (getTemperature() <= 0) {
                nbt.setLong("ticks", -1);
                nbt.setFloat("heat", 0);
            } else {
                nbt.setLong("ticks", lastUpdateTick);
                nbt.setFloat("heat", temperature);
            }
            return tank.writeToNBT(nbt);
        }

        @Override
        public void deserializeNBT(@Nullable NBTTagCompound nbt) {
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

        private void updateFluidData(@Nullable FluidStack fluid) {
            meltTemp = Heat.maxVisibleTemperature();
            heatCapacity = 1;
            if (fluid != null) {
                var material = MetaFluids.getMaterialFromFluid(fluid.getFluid());

                if (material != null) {
                    var heatKey = material.getProperty(TFGPropertyKey.HEAT);

                    meltTemp = heatKey.getMeltTemp();
                    heatCapacity = heatKey.getSpecificHeat();
                }
            }
        }
    }
}
