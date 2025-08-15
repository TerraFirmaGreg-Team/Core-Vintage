package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockBookshelf;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

import lombok.Getter;

@Getter
public class BlockWoodBookshelf extends BaseBlockBookshelf implements IProviderBlockColor, IWoodEntry {

  protected final WoodType type;

  public BlockWoodBookshelf(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .registryKey(type.getRegistryKey("bookshelf"))
      .customResource(type.getResource("bookshelf"))
      .hardness(2.0F)
      .resistance(5.0F)
      .sound(SoundType.WOOD)
      .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
      .harvestLevel(ToolClasses.AXE, 0)
      .fireInfo(30, 20)
      .addOreDict("bookshelf")
    );
    
    this.type = type;
  }


}
