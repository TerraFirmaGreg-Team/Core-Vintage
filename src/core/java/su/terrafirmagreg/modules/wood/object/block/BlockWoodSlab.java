package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.feature.woodtype.spi.IWoodBlock;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;

import lombok.Getter;

@Getter
public abstract class BlockWoodSlab extends BaseBlockSlab implements IWoodBlock, IProviderBlockColor {

  protected final WoodType type;

  protected Half halfSlab;
  protected Double doubleSlab;

  private BlockWoodSlab(WoodType type) {
    super(Settings.of(BlocksWood.PLANKS.get(type)));

    this.type = type;

    getSettings()
      .fireInfo(5, 20)
      .sound(SoundType.WOOD);
  }

  @Override
  public IBlockColor getBlockColor() {
    return (s, w, p, i) -> this.getType().getColor();
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }

  public static class Double extends BlockWoodSlab {

    public Double(WoodType type) {
      super(type);

      getSettings()
        .registryKey(type.getRegistryKey("slab_double/planks"))
        .customResource(type.getResource("slab_double/planks"));

    }

    @Override
    public boolean isDouble() {
      return true;
    }
  }

  public static class Half extends BlockWoodSlab {

    public Half(WoodType type) {
      super(type);

      this.doubleSlab = BlocksWood.SLAB_DOUBLE_PLANKS.get(type);
      this.doubleSlab.halfSlab = this;
      this.halfSlab = this;

      getSettings()
        .registryKey(type.getRegistryKey("slab/planks"))
        .customResource(type.getResource("slab/planks"))
        .oreDict("slab", "wood")
        .oreDict("slab", "wood", type);

    }

    @Override
    public boolean isDouble() {
      return false;
    }
  }
}
