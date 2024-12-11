package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;

import net.minecraft.block.BlockBookshelf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public abstract class BaseBlockBookshelf extends BlockBookshelf implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockBookshelf(Settings settings) {

    this.settings = settings;

  }

  @Override
  public float getEnchantPowerBonus(World world, BlockPos pos) {
    return 1.0F;
  }
}
