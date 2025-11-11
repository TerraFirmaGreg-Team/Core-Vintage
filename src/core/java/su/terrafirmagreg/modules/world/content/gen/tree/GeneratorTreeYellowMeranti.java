package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeYellowMeranti extends BaseGeneratorTree {


  public GeneratorTreeYellowMeranti() {
    super(Builder.builder()
      .name("yellow_meranti")
      .rainInfo(260f, 500f)
      .tempInfo(21f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.5f, 2f)
      //.generator(GEN_YELLOW_MERANTI)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
