package su.terrafirmagreg.modules.wood.object.generator;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.StructureUtils;
import su.terrafirmagreg.modules.wood.ModuleWood;
import su.terrafirmagreg.modules.wood.api.generator.ITreeGenerator;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodLeaves;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSapling;

import net.minecraft.block.BlockLog;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

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
  public void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType type, Random rand, boolean isWorldGen) {
    //noinspection ConstantConditions
    ResourceLocation base = ModUtils.resource("tree/" + type + "/base");
    ResourceLocation overlay = ModUtils.resource("tree/" + type + "/overlay");

    structureBase = manager.get(world.getMinecraftServer(), base);
    structureOverlay = manager.get(world.getMinecraftServer(), overlay);

    if (structureBase == null || structureOverlay == null) {
      ModuleWood.LOGGER.warn("Unable to find a template for {} or {}", base, overlay);
      return;
    }

    int height = 5 + rand.nextInt(3);
    int branches = 2 + rand.nextInt(3), x1, z1, y1;
    for (int n = 0; n <= height; n++) {
      if (n > 3) {
        createLeafGroup(world, pos.up(n));
      }
      tryPlaceLog(world, pos.up(n), type, BlockLog.EnumAxis.Y);
    }

    for (int n = 0; n < branches; n++) {
      x1 = (rand.nextBoolean() ? 1 : -1) * (1 + rand.nextInt(3));
      z1 = (rand.nextBoolean() ? 1 : -1) * (1 + rand.nextInt(3));
      y1 = 3 + rand.nextInt(2);
      createLeafGroup(world, pos.add(x1, y1, z1));
      createBranch(world, pos, x1, y1, z1, rand, type);
    }
  }

  private void createLeafGroup(World world, BlockPos pos) {
    BlockPos size = structureBase.getSize();
    pos = pos.add(-size.getX() / 2, -size.getY() / 2, -size.getZ() / 2);

    StructureUtils.addStructureToWorld(world, pos, structureBase, settingsFull);
    StructureUtils.addStructureToWorld(world, pos, structureOverlay, settingsWeak);
  }

  private void tryPlaceLog(World world, BlockPos pos, WoodType type, BlockLog.EnumAxis axis) {
    if (world.getBlockState(pos).getMaterial().isReplaceable() ||
        world.getBlockState(pos).getBlock() instanceof BlockWoodSapling ||
        world.getBlockState(pos).getBlock() instanceof BlockWoodLeaves) {

      world.setBlockState(pos, BlocksWood.LOG.get(type)
                                             .getDefaultState()
                                             .withProperty(LOG_AXIS, axis)
                                             .withProperty(PLACED, false));
    }
  }

  private void createBranch(World world, BlockPos pos1, int x, int y, int z, Random rand, WoodType type) {
    int x1 = x < 0 ? 1 : -1;
    int z1 = z < 0 ? 1 : -1;
    do {
      if (x != 0 && rand.nextBoolean()) {
        x += x1;
      }
      if (z != 0 && rand.nextBoolean()) {
        z += z1;
      }
      tryPlaceLog(world, pos1.add(x, y, z), type, BlockLog.EnumAxis.NONE);
      if (rand.nextBoolean()) {
        createLeafGroup(world, pos1.add(x, y, z));
      }
    }
    while (Math.abs(x) + Math.abs(z) > 0);
  }
}
