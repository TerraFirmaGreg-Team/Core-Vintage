package lyeoj.tfcthings.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.capability.forge.ForgeableHandler;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemDiamondGrit extends ItemTFC {

    public ItemDiamondGrit() {
        this.setRegistryName("diamond_grit");
        this.setTranslationKey("diamond_grit");
        this.setCreativeTab(CreativeTabsTFC.CT_MISC);
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack itemStack) {
        return Size.VERY_SMALL;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
        return Weight.VERY_LIGHT;
    }

    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ForgeableHandler(nbt);
    }
}
