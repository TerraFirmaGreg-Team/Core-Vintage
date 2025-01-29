package tfcflorae.objects.items.itemblock;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityProviderHeat;

import tfcflorae.objects.blocks.BlockUrnLoot;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemBlockUrnLoot extends ItemBlockTFC implements ICapabilitySize {

  public ItemBlockUrnLoot(BlockUrnLoot block) {
    super(block);
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    // Since this is technically still a pottery item, despite being a block
    return new CapabilityProviderHeat(nbt, 1.0f, 1599f);
  }
}
