package net.dries007.tfcflorae.objects.items.itemblock;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;

import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfcflorae.objects.blocks.BlockCrate;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemBlockCrate extends ItemBlockTFC implements ICapabilitySize {

  public ItemBlockCrate(BlockCrate block) {
    super(block);
  }
}
