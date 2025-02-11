package net.dries007.tfc.objects.items.itemblock;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.device.object.block.BlockCrate;

import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemBlockCrate extends ItemBlockTFC implements ICapabilitySize {

  public ItemBlockCrate(BlockCrate block) {
    super(block);
  }
}
