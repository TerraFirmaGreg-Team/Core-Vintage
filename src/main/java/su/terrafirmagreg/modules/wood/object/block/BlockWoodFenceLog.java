package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodFenceLog extends BlockFence implements IWoodBlock {

  protected final Settings settings;
  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodFenceLog(WoodBlockVariant variant, WoodType type) {
    super(Material.WOOD, Material.WOOD.getMaterialMapColor());

    this.variant = variant;
    this.type = type;

    this.settings = Settings.of(Material.WOOD);

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .sound(SoundType.WOOD)
      .hardness(2.0F)
      .resistance(15.0F)
      .harvestLevel(ToolClasses.AXE, 0)
      .oreDict("fence", "wood")
      .oreDict("fence", "wood", type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());

  }
}
