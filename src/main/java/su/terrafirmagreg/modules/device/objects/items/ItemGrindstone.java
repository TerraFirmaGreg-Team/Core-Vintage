package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.capability.forge.ForgeableHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public class ItemGrindstone extends BaseItem {

    @Getter
    private final int tier;
    private final String name;

    public ItemGrindstone(int tier, int durability, String name) {

        this.tier = tier;
        this.name = name;

        setMaxDamage(durability);
        setNoRepair();
        setMaxStackSize(1);
    }

    public int getMaxCharges() {
        return switch (tier) {
            case 2 -> 256;
            case 3 -> 384;
            default -> 64;
        };
    }

    @Override
    public Size getSize(ItemStack itemStack) {
        return Size.LARGE;
    }

    @Override
    public Weight getWeight(ItemStack itemStack) {
        return Weight.HEAVY;
    }

    @Override
    public boolean canStack(ItemStack stack) {
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
    public String getName() {
        return "device/grindstone/" + name;
    }
}
