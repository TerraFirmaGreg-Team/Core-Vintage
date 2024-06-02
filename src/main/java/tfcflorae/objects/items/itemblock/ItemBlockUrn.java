package tfcflorae.objects.items.itemblock;

import su.terrafirmagreg.api.capabilities.heat.ProviderHeat;
import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import tfcflorae.objects.blocks.BlockUrn;

import org.jetbrains.annotations.Nullable;

public class ItemBlockUrn extends ItemBlockTFC implements ICapabilitySize {

    public ItemBlockUrn(BlockUrn block) {
        super(block);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        // Since this is technically still a pottery item, despite being a block
        return new ProviderHeat(nbt, 1.0f, 1599f);
    }
}
