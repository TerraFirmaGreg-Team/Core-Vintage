package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockWoodFenceGateLog extends BlockFenceGate implements IWoodBlock {

  protected final Settings settings;
  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodFenceGateLog(WoodBlockVariant variant, WoodType type) {
    super(BlockPlanks.EnumType.OAK);

    this.variant = variant;
    this.type = type;

    this.settings = Settings.of(Material.WOOD);

    getSettings()
      .registryKey(variant.getRegistryKey(type))
      .ignoresProperties(IN_WALL, POWERED)
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .hardness(2.0F)
      .resistance(15.0F)
      .oreDict("fence", "gate", "wood")
      .oreDict("fence", "gate", "wood", type);

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());

  }
}
