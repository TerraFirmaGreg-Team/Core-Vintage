package com.buuz135.hotornot.object.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;


import com.buuz135.hotornot.HotGuiHandler;
import com.buuz135.hotornot.object.recipe.UnMoldJawPiece;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.container.CapabilityContainerListener;
import net.dries007.tfc.objects.container.ContainerEmpty;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import net.dries007.tfc.util.Helpers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemMetalTongsJawMold extends ItemPottery {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(final ItemStack itemStack, @Nullable final NBTTagCompound nbt) {
        return new FilledMoldCapability(nbt);
    }

    @Override
    public int getItemStackLimit(final ItemStack itemStack) {
        final IMoldHandler moldHandler = (IMoldHandler) itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (moldHandler != null && moldHandler.getMetal() != null) {
            return 1;
        }
        return super.getItemStackLimit(itemStack);
    }

    @NotNull
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        final ItemStack heldStack = player.getHeldItem(hand);
        if (world.isRemote) return new ActionResult<>(EnumActionResult.SUCCESS, heldStack);

        final IItemHeat cap = heldStack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
        if (!player.isSneaking() && cap != null && cap.isMolten()) {
            HotGuiHandler.openMoldGui(world, player);
        }

        // Must be sneaking
        if (!player.isSneaking()) return new ActionResult<>(EnumActionResult.SUCCESS, heldStack);

        // Unmold on right click, if possible
        final InventoryCrafting craftMatrix = new InventoryCrafting(new ContainerEmpty(), 3, 3);
        craftMatrix.setInventorySlotContents(0, heldStack);
        for (final IRecipe recipe : ForgeRegistries.RECIPES.getValuesCollection()) {
            if (recipe instanceof UnMoldJawPiece && recipe.matches(craftMatrix, world)) {
                final ItemStack craftingResult = recipe.getCraftingResult(craftMatrix);
                if (!craftingResult.isEmpty()) {
                    final ItemStack moldResult = ((UnMoldJawPiece) recipe).getMoldResult(heldStack);
                    player.setHeldItem(hand, craftingResult);
                    if (!moldResult.isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(player, moldResult);
                    } else {
                        world.playSound(null, player.getPosition(), TFCSounds.CERAMIC_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }
                }
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, heldStack);
    }

    @NotNull
    @Override
    public String getTranslationKey(ItemStack stack) {
        final IFluidHandler capFluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (!(capFluidHandler instanceof IMoldHandler)) return super.getTranslationKey(stack);

        final Metal metal = ((IMoldHandler) capFluidHandler).getMetal();
        //noinspection DataFlowIssue
        return metal != null ? super.getTranslationKey(stack) + "." + metal.getRegistryName()
                .getPath() : super.getTranslationKey(stack);
    }

    @Nullable
    @Override
    public NBTTagCompound getNBTShareTag(final ItemStack itemStack) {
        return CapabilityContainerListener.readShareTag(itemStack);
    }

    @Override
    public void readNBTShareTag(final ItemStack itemStack, final @Nullable NBTTagCompound nbt) {
        CapabilityContainerListener.applyShareTag(itemStack, nbt);
    }

    /**
     * Copy of the TFC mold capability which we don't have access to
     */
    private static class FilledMoldCapability extends ItemHeatHandler implements ICapabilityProvider, IMoldHandler {

        private final FluidTank tank = new FluidTank(100);
        private final IFluidTankProperties[] fluidTankProperties = { new FluidTankPropertiesWrapper(tank) };

        FilledMoldCapability(final @Nullable NBTTagCompound nbt) {
            if (nbt != null) {
                deserializeNBT(nbt);
            }
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
            return fluidTankProperties;
        }

        @Override
        public int fill(final FluidStack resource, final boolean doFill) {
            final int fillAmount = tank.fill(resource, doFill);
            if (fillAmount == tank.getFluidAmount()) {
                updateFluidData();
            }

            return fillAmount;
        }

        @Nullable
        @Override
        public FluidStack drain(final FluidStack resource, final boolean doDrain) {
            return getTemperature() >= meltTemp ? tank.drain(resource, doDrain) : null;
        }

        @Nullable
        @Override
        public FluidStack drain(final int maxDrain, final boolean doDrain) {
            if (getTemperature() > meltTemp) {
                final FluidStack drained = tank.drain(maxDrain, doDrain);
                if (tank.getFluidAmount() == 0) {
                    updateFluidData();
                }

                return drained;
            }

            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void addHeatInfo(final ItemStack itemStack, final List<String> text) {
            final Metal metal = getMetal();
            if (metal != null) {
                String desc = TextFormatting.DARK_GREEN + I18n.format(Helpers.getTypeName(metal)) + ": " + I18n.format("tfc.tooltip.units",
                        getAmount());
                if (isMolten()) {
                    desc += I18n.format("tfc.tooltip.liquid");
                } else {
                    desc += I18n.format("tfc.tooltip.solid");
                }

                text.add(desc);
            }

            super.addHeatInfo(itemStack, text);
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
        public boolean hasCapability(final Capability<?> capability, final @Nullable EnumFacing facing) {
            return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
        }

        @Nullable
        @Override
        @SuppressWarnings("unchecked")
        public <T> T getCapability(final Capability<T> capability, final @Nullable EnumFacing facing) {
            return hasCapability(capability, facing) ? (T) this : null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            final NBTTagCompound compound = new NBTTagCompound();
            if (getTemperature() <= 0.0F) {
                compound.setLong("ticks", -1L);
                compound.setFloat("heat", 0.0F);
            } else {
                compound.setLong("ticks", lastUpdateTick);
                compound.setFloat("heat", temperature);
            }

            return tank.writeToNBT(compound);
        }

        @Override
        public void deserializeNBT(final @Nullable NBTTagCompound compound) {
            if (compound != null) {
                temperature = compound.getFloat("heat");
                lastUpdateTick = compound.getLong("ticks");
                tank.readFromNBT(compound);
            }

            updateFluidData();
        }

        private void updateFluidData() {
            updateFluidData(tank.getFluid());
        }

        private void updateFluidData(final @Nullable FluidStack fluidStack) {
            meltTemp = Heat.maxVisibleTemperature();
            heatCapacity = 1.0F;
            if (fluidStack != null) {
                final Metal metal = FluidsTFC.getMetalFromFluid(fluidStack.getFluid());
                meltTemp = metal.getMeltTemp();
                heatCapacity = metal.getSpecificHeat();
            }

        }
    }
}
