package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeMarblewood extends BaseGeneratorTree {


  public GeneratorTreeMarblewood() {
    super(Builder.builder()
      .name("marblewood")
      .rainInfo(180f, 500f)
      .tempInfo(16f, 35f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.2f, 2f)
      //.generator(GEN_MARBLEWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
