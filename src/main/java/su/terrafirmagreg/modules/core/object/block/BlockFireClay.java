package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import net.dries007.tfc.objects.items.ItemsTFC;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockFireClay extends BaseBlock {

  public BlockFireClay() {
    super(Settings.of(Material.CLAY));

    getSettings()
      .registryKey("core/fire_clay")
      .sound(SoundType.GROUND)
      .size(Size.VERY_SMALL)
      .weight(Weight.HEAVY);
  }

  @Override
  public int quantityDropped(Random random) {
    return 4;
  }

  @NotNull
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsTFC.FIRE_CLAY;
  }

}
