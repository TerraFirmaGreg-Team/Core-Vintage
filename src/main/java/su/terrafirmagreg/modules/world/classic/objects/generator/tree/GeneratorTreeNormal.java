package su.terrafirmagreg.modules.world.classic.objects.generator.tree;

import su.terrafirmagreg.api.util.StructureUtils;
import su.terrafirmagreg.modules.world.ModuleWorld;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.BoolProp.PLACED;

public class GeneratorTreeNormal implements ITreeGenerator {

  private static final PlacementSettings settingsFull = StructureUtils.getDefaultSettings();
  private static final PlacementSettings settingsWeak = StructureUtils.getDefaultSettings().setIntegrity(0.5f);
  private final int heightMin;
  private final int heightRange;

  /**
   * A basic tree generator. It will generate a structure found in /assets/tfc/[TREE NAME]/base.nbt Additionally, it will try and apply an overlay with the name
   * /overlay.nbt at 50% integrity.
   *
   * @param heightMin   The minimum amount of logs to add to the bottom of the trunk Set to 0 for no extra height
   * @param heightRange The maximum amount of logs to add to the bottom of the trunk. Set to 0 for no extra height
   */
  public GeneratorTreeNormal(int heightMin, int heightRange) {
    this.heightMin = heightMin;
    this.heightRange = heightRange;
  }

  @Override
  public void generateTree(TemplateManager manager, World world, BlockPos pos, Tree tree, Random rand, boolean isWorldGen) {
    ResourceLocation base = new ResourceLocation(tree.getRegistryName() + "/base");
    ResourceLocation overlay = new ResourceLocation(tree.getRegistryName() + "/overlay");

    Template structureBase = manager.get(world.getMinecraftServer(), base);
    Template structureOverlay = manager.get(world.getMinecraftServer(), overlay);

    if (structureBase == null) {
      ModuleWorld.LOGGER.warn("Unable to find a template for " + base);
      return;
    }

    int height = heightMin + (heightRange > 0 ? rand.nextInt(heightRange) : 0);

    BlockPos size = structureBase.getSize();
    pos = pos.add(-size.getX() / 2, height, -size.getZ() / 2);

    StructureUtils.addStructureToWorld(world, pos, structureBase, settingsFull);
    if (structureOverlay != null) {
      StructureUtils.addStructureToWorld(world, pos, structureOverlay, settingsWeak);
    }

    final IBlockState log = BlockLogTFC.get(tree).getDefaultState().withProperty(PLACED, false);
    for (int i = 0; i < height; i++) {
      world.setBlockState(pos.add(size.getX() / 2, i - height, size.getZ() / 2), log);
    }
  }

}
