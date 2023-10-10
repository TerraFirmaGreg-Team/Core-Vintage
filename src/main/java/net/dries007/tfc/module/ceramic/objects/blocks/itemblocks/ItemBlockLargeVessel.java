package net.dries007.tfc.module.ceramic.objects.blocks.itemblocks;

import net.dries007.tfc.module.core.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.module.ceramic.objects.blocks.BlockLargeVessel;
import net.dries007.tfc.module.core.api.capability.size.IItemSizeAndWeight;
import net.dries007.tfc.module.core.api.objects.block.itemblocks.ItemBlockBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemBlockLargeVessel extends ItemBlockBase implements IItemSizeAndWeight {
    public ItemBlockLargeVessel(BlockLargeVessel block) {
        super(block);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        // Since this is technically still a pottery item, despite being a block
        return new ItemHeatHandler(nbt, 1.0f, 1599f);
    }
}
