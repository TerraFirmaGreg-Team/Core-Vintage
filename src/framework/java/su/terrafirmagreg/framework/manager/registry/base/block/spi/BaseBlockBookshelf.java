package su.terrafirmagreg.framework.manager.registry.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.BlockBookshelf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public abstract class BaseBlockBookshelf extends BlockBookshelf implements IBlockEntry {

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
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }
}
