package su.terrafirmagreg.modules.world.classic.objects.generator.tree;

import su.terrafirmagreg.api.data.Reference;
import su.terrafirmagreg.api.util.StructureUtils;
import su.terrafirmagreg.modules.world.ModuleWorld;

import net.minecraft.block.BlockLog;
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

import static net.minecraft.block.BlockLog.LOG_AXIS;
import static su.terrafirmagreg.api.data.Properties.BoolProp.PLACED;

/**
 * This is a tree generator only used for the willow tree shapes. Requires two structure blocks: both found in /assets/tfc/[TREE NAME]/, named base.nbt and
 * overlay.nbt respectively. See the examples for TFC willow tree for what the structure blocks should look like.
 */
public class GeneratorTreeWillow implements ITreeGenerator {

  private static final PlacementSettings settingsFull = StructureUtils.getDefaultSettings();
  private static final PlacementSettings settingsWeak = StructureUtils.getDefaultSettings().setIntegrity(0.5F);
  private Template structureBase;
  private Template structureOverlay;

  @Override
  public void generateTree(TemplateManager manager, World world, BlockPos pos, Tree tree, Random rand, boolean isWorldGen) {
    //noinspection ConstantConditions
    ResourceLocation base = new ResourceLocation(Reference.MODID_TFC, tree.getRegistryName().getPath() + "/base");
    ResourceLocation overlay = new ResourceLocation(Reference.MODID_TFC, tree.getRegistryName().getPath() + "/overlay");

    structureBase = manager.get(world.getMinecraftServer(), base);
    structureOverlay = manager.get(world.getMinecraftServer(), overlay);

    if (structureBase == null || structureOverlay == null) {
      ModuleWorld.LOGGER.warn("Unable to find a template for " + base + " or " + overlay);
      return;
    }

    int height = 5 + rand.nextInt(3),
      branches = 2 + rand.nextInt(3), x1, z1, y1;
    for (int n = 0; n <= height; n++) {
      if (n > 3) {
        createLeafGroup(world, pos.up(n));
      }
      tryPlaceLog(world, pos.up(n), tree, BlockLog.EnumAxis.Y);
    }

    for (int n = 0; n < branches; n++) {
      x1 = (rand.nextBoolean() ? 1 : -1) * (1 + rand.nextInt(3));
      z1 = (rand.nextBoolean() ? 1 : -1) * (1 + rand.nextInt(3));
      y1 = 3 + rand.nextInt(2);
      createLeafGroup(world, pos.add(x1, y1, z1));
      createBranch(world, pos, x1, y1, z1, rand, tree);
    }
  }

  private void createLeafGroup(World world, BlockPos pos) {
    BlockPos size = structureBase.getSize();
    pos = pos.add(-size.getX() / 2, -size.getY() / 2, -size.getZ() / 2);

    StructureUtils.addStructureToWorld(world, pos, structureBase, settingsFull);
    StructureUtils.addStructureToWorld(world, pos, structureOverlay, settingsWeak);
  }

  private void tryPlaceLog(World world, BlockPos pos, Tree tree, BlockLog.EnumAxis axis) {
    if (world.getBlockState(pos).getMaterial().isReplaceable() || world.getBlockState(pos)
                                                                       .getBlock() instanceof BlockSaplingTFC || world.getBlockState(pos)
                                                                                                                      .getBlock() instanceof BlockLeavesTFC) {
      world.setBlockState(pos, BlockLogTFC.get(tree)
                                          .getDefaultState()
                                          .withProperty(LOG_AXIS, axis)
                                          .withProperty(PLACED, false));
    }
  }

  private void createBranch(World world, BlockPos pos1, int x, int y, int z, Random rand,
                            Tree tree) {
    int x1 = x < 0 ? 1 : -1,
      z1 = z < 0 ? 1 : -1;
    do {
      if (x != 0 && rand.nextBoolean()) {
        x += x1;
      }
      if (z != 0 && rand.nextBoolean()) {
        z += z1;
      }
      tryPlaceLog(world, pos1.add(x, y, z), tree, BlockLog.EnumAxis.NONE);
      if (rand.nextBoolean()) {
        createLeafGroup(world, pos1.add(x, y, z));
      }
    }
    while (Math.abs(x) + Math.abs(z) > 0);
  }
}
