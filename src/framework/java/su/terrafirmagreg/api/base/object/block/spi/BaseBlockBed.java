package su.terrafirmagreg.api.base.object.block.spi;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.block.BlockBed;

import lombok.Getter;

@Getter
public abstract class BaseBlockBed extends BlockBed implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockBed(Settings settings) {
    this.settings = settings;
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize("block", this.getRegistryName());
  }
}
