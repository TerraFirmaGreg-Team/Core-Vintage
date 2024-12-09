package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;

import net.minecraft.block.BlockBed;

import lombok.Getter;

@Getter
public abstract class BaseBlockBed extends BlockBed implements IBlockSettings {

  protected final Settings settings;

  public BaseBlockBed(Settings settings) {
    this.settings = settings;
  }
}
