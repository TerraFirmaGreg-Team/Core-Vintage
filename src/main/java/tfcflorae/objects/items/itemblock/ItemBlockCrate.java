package tfcflorae.objects.items.itemblock;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import tfcflorae.objects.blocks.BlockCrate;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemBlockCrate extends ItemBlockTFC implements IItemSize {

  public ItemBlockCrate(BlockCrate block) {
    super(block);
  }
}
