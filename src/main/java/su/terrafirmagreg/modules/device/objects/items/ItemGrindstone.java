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

@Getter
public class ItemGrindstone extends BaseItem {

    private final int tier;

    public ItemGrindstone(int tier, int durability, String name) {
        this.tier = tier;

        setNoRepair();
        getSettings()
                .registryKey("device/grindstone/" + name)
                .weight(Weight.HEAVY)
                .size(Size.LARGE)
                .notCanStack()
                .maxDamage(durability)
                .maxCount(1);

    }

    public int getMaxCharges() {
        return switch (tier) {
            case 2 -> 256;
            case 3 -> 384;
            default -> 64;
        };
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (tier == 1) {
            return new ForgeableHandler(nbt);
        } else {
            return null;
        }
    }
}
