package su.terrafirmagreg.modules.world.classic.objects.generator.tree;

import su.terrafirmagreg.api.util.StructureUtils;
import su.terrafirmagreg.modules.world.ModuleWorld;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.util.IFruitTreeGenerator;

import java.util.Random;

public class GeneratorTreeFruit implements IFruitTreeGenerator {

  private static final PlacementSettings SETTINGS = StructureUtils.getDefaultSettings();

  @Override
  public void generateTree(TemplateManager manager, World world, BlockPos pos, IFruitTree tree,
                           Random rand) {
    ResourceLocation base = new ResourceLocation("tfc:fruit_trees/" + tree.getName());
    Template structureBase = manager.get(world.getMinecraftServer(), base);

    if (structureBase == null) {
      ModuleWorld.LOGGER.warn("Unable to find a template for " + base);
      return;
    }

    BlockPos size = structureBase.getSize();
    pos = pos.add(-size.getX() / 2, 0, -size.getZ() / 2);

    StructureUtils.addStructureToWorld(world, pos, structureBase, SETTINGS);
  }
}
