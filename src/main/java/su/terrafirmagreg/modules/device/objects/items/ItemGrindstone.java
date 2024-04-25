package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.spi.item.ItemBase;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.capability.forge.ForgeableHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemGrindstone extends ItemBase {

    private final int tier;
    private final String name;

    public ItemGrindstone(int tier, int durability, String name) {

        this.tier = tier;
        this.name = name;

        setMaxDamage(durability);
        setNoRepair();
        setMaxStackSize(1);
    }

    public int getTier() {
        return tier;
    }

    public int getMaxCharges() {
        return switch (tier) {
            case 2 -> 256;
            case 3 -> 384;
            default -> 64;
        };
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack itemStack) {
        return Size.LARGE;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
        return Weight.HEAVY;
    }

    @Override
    public boolean canStack(@NotNull ItemStack stack) {
        return false;
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (tier == 1) {
            return new ForgeableHandler(nbt);
        } else {
            return null;
        }
    }

    @Override
    public @NotNull String getName() {
        return "device/grindstone/" + name;
    }
}
