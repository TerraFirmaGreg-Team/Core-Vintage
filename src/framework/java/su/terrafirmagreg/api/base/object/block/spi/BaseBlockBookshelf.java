package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.util.ModUtils;

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

  @Override
  public String getTranslationKey() {
    return ModUtils.localize("block", this.getRegistryName());
  }
}
