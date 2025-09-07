package su.terrafirmagreg.framework.manager.content.base.block.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;

import net.minecraft.block.BlockTrapDoor;

import lombok.Getter;

@Getter
public abstract class BaseBlockTrapDoor extends BlockTrapDoor implements IBlockEntry {

  protected final BlockSettings settings;

  public BaseBlockTrapDoor(BlockSettings settings) {
    super(settings.getMaterial());

    this.settings = settings;
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, this.getRegistryName());
  }
}
