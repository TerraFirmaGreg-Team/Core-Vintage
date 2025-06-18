package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;

import net.dries007.tfc.api.types.Tree;

import java.util.Random;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeKapok extends BaseGeneratorTree {

  public GeneratorTreeKapok() {
    super(Builder.builder()
      .name("kapok")
      .rainInfo(210f, 500f)
      .tempInfo(15f, 35f)
      .dominance(8.5f)
      .radius(3)
      .height(24)
      .decayDist(6)
      .minGrowthTime(18)
      .density(0.6f, 2f)
      //.generator(GEN_KAPOK_COMPOSITE)
      .paramMap(0.10f, 30f, 7, 4, 0.85f)
      .growthLogicKit("jungle")
      .cellKit("deciduous")
      .bushes()
      .isThick()
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


  @Override
  public void generateTree(TemplateManager manager, World world, BlockPos pos, Tree tree, Random rand, boolean isWorldGen) {

  }
}
