package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.minecraft.block.SoundType;

import lombok.Getter;

@Getter
public class BlockWoodSlab extends BaseBlockSlab implements IWoodEntry, IProviderBlockColor {

  protected final WoodType type;

  protected BlockWoodSlab halfSlab;
  protected BlockWoodSlab doubleSlab;

  public BlockWoodSlab(WoodType type) {
    super(BlockSettings.of(BlocksWood.PLANKS.get(type)));

    this.type = type;

    getSettings()
      .fireInfo(5, 20)
      .sound(SoundType.WOOD)
      .registryKey(type.getRegistryKey("planks/slab_double"))
      .customResource(type.getResource("planks/slab_double"));
  }

  @Override
  public boolean isDouble() {
    return true;
  }


  public static class Half extends BlockWoodSlab {

    public Half(WoodType type) {
      super(type);

      this.doubleSlab = BlocksWood.SLAB_DOUBLE_PLANKS.get(type);
      this.doubleSlab.halfSlab = this;
      this.halfSlab = this;

      getSettings()
        .registryKey(type.getRegistryKey("planks/slab"))
        .customResource(type.getResource("planks/slab"))
        .addOreDict("slab", "wood")
        .addOreDict("slab", "wood", type);

    }

    @Override
    public boolean isDouble() {
      return false;
    }
  }
}
