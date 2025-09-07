package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeMountainAsh extends BaseGeneratorTree {


  public GeneratorTreeMountainAsh() {
    super(Builder.builder()
      .name("mountain_ash")
      .rainInfo(80f, 270f)
      .tempInfo(9f, 33f)
      .minGrowthTime(10)
      .bushes()
      .density(0.4f, 2f)
      //.generator(GEN_MOUNTAIN_ASH)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
