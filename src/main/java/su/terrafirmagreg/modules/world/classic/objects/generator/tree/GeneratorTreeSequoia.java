package su.terrafirmagreg.modules.world.classic.objects.generator.tree;

import su.terrafirmagreg.api.util.BlockUtils;
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
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.BoolProp.PLACED;

public class GeneratorTreeSequoia implements ITreeGenerator {

  private static final BlockPos[] OFFSETS = new BlockPos[]{
    new BlockPos(0, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(-1, 0, -1)
  };
  private final PlacementSettings settings = StructureUtils.getDefaultSettings();
  private IBlockState trunk;

  @Override
  public void generateTree(TemplateManager manager, World world, BlockPos pos, Tree tree,
                           Random rand, boolean isWorldGen) {
    final int baseVariant = 1 + rand.nextInt(3);
    final int topVariant = 1 + rand.nextInt(3);
    final int layers = 4 + rand.nextInt(3);
    final int height = 3 + rand.nextInt(4);

    trunk = BlockLogTFC.get(tree).getDefaultState().withProperty(PLACED, false);

    for (int i = -2; i < height; i++) {
      placeTrunk(world, pos.add(0, i, 0));
    }

    int k = height;
    for (int j = 0; j < layers; j++) {
      if (j == layers - 1 || (j == layers - 2 && rand.nextBoolean())) {
        k += placeLayer(manager, world, pos.up(k), tree.getRegistryName() + "/mid" + baseVariant);
      } else {
        k += placeLayer(manager, world, pos.up(k), tree.getRegistryName() + "/base" + baseVariant);
      }
    }
    placeLayer(manager, world, pos.up(k), tree.getRegistryName() + "/top" + topVariant);

  }

  @Override
  public boolean canGenerateTree(World world, BlockPos pos, Tree treeType) {
    for (BlockPos p1 : OFFSETS) {
      if (!BlockUtils.isSoil(world.getBlockState(pos.add(p1).down()))) {
        if (world.getBlockState(pos.add(p1)).getMaterial().isReplaceable()) {
          if (BlockUtils.isSoil(world.getBlockState(pos.add(p1).down(1)))) {
            continue;
          }
          if (BlockUtils.isSoil(world.getBlockState(pos.add(p1)
                                                       .down(2))) && world.getBlockState(pos.add(p1.down(1)))
                                                                          .getMaterial()
                                                                          .isReplaceable()) {
            continue;
          }
        }
        return false;
      }
    }

    return ITreeGenerator.super.canGenerateTree(world, pos, treeType);
  }

  private void placeTrunk(World world, BlockPos pos) {
    for (BlockPos p1 : OFFSETS) {
      checkAndPlace(world, pos.add(p1));
    }
  }

  private int placeLayer(TemplateManager manager, World world, BlockPos pos, String name) {
    ResourceLocation base = new ResourceLocation(name);
    Template structureBase = manager.get(world.getMinecraftServer(), base);

    if (structureBase == null) {
      ModuleWorld.LOGGER.warn("Unable to find a template for " + base);
      return -1;
    }
    BlockPos size = structureBase.getSize();
    pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);

    StructureUtils.addStructureToWorld(world, pos, structureBase, settings);
    return size.getY();
  }

  private void checkAndPlace(World world, BlockPos pos) {
    if (world.getBlockState(pos).getMaterial().isReplaceable() || world.getBlockState(pos)
                                                                       .getBlock() instanceof BlockSaplingTFC || world.getBlockState(pos)
                                                                                                                      .getBlock() instanceof BlockLeavesTFC) {
      world.setBlockState(pos, trunk);
    }
  }

}
