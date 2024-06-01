package net.dries007.tfc.api.capability;

import su.terrafirmagreg.api.capabilities.size.CapabilitySize;
import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.api.capabilities.size.spi.Size;
import su.terrafirmagreg.api.capabilities.size.spi.Weight;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.capability.heat.ItemHeatHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static su.terrafirmagreg.api.data.Constants.MODID_TFC;

/**
 * Custom heat + size capability for stick items.
 */
public class ItemStickCapability extends ItemHeatHandler implements ICapabilitySize {

    public static final ResourceLocation KEY = new ResourceLocation(MODID_TFC, "stick");
    private static final float MELTING_POINT = 40f;
    private static final float HEAT_CAPACITY = 1f;

    public ItemStickCapability() {
        this(null);
    }

    public ItemStickCapability(@Nullable NBTTagCompound nbt) {
        super(nbt, HEAT_CAPACITY, MELTING_POINT);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addHeatInfo(@NotNull ItemStack stack, @NotNull List<String> text) {
        float temperature = getTemperature();
        if (temperature > getMeltTemp() * 0.9f) {
            text.add(I18n.format("tfc.enum.heat.torch.lit"));
        } else if (temperature > 1f) {
            text.add(I18n.format("tfc.enum.heat.torch.catching_fire"));
        }
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack stack) {
        return Size.SMALL; // Store anywhere
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack stack) {
        return Weight.VERY_LIGHT; // Stacksize = 64
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilitySize.CAPABILITY || super.hasCapability(capability, facing);
    }
}
